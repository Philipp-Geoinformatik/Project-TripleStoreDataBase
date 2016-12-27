package de.jhs.oldenburg.gg.owl.tdb.manager;

import java.io.File;
import java.util.Date;
import java.util.Vector;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryException;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;

public class OWLTDBManager {

	/**
	 * 
	 */
	private Dataset dataset;
	private String GRAPH_NAME = "myGraph";

	/**
	 * 
	 */
	public OWLTDBManager() {

	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new OWLTDBManager().run();
	}

	/**
	 * Manage all needed functions
	 */
	public void run() {
		System.out.println("////////////////////////////////////////////////////////////");
		System.out.println("////////////////" + new Date() + "////////////////");
		System.out.println("////////////////////////////////////////////////////////////");
		initDataset();
		deleteModel();
		loadGraph(GRAPH_NAME, "I_AM_THE_CREATOR");
		createOntology("RDF_Files/friends.owl");
		// TDBPrinter.printModelContent(dataset);
		resolveCompound();
	}

	/**
	 * Initialize the default graph database.
	 */
	public void initDataset() {
		String datasetLocation = "OWLDataBase";
		String datasetName = "myOWLTDB";

		String dir = datasetLocation + "/" + datasetName;
		File theDir = new File(dir);
		if (!theDir.exists()) {
			theDir.mkdirs();
			System.out.println("New directory created");
		}
		// erstelle ein dataset
		this.dataset = TDBFactory.createDataset(datasetLocation + "/" + datasetName);
	}

	public void loadGraph(String graphName, String creator) {
		Model m = ModelFactory.createDefaultModel();
		m.createResource(graphName).addProperty(RDF.type, OWL.Ontology).addProperty(DCTerms.creator, creator);
		dataset.addNamedModel(graphName, m);
		System.out.println(dataset.getDefaultModel().isEmpty());
		TDB.sync(m);
	}

	/**
	 * 
	 * @param source
	 */
	public void createOntology(String source) {
		// create the base model
		// "RDF_Files/friends.owl";
		String namespace = source + "#";
		OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		// create the reasoning model using the base
		OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, base);
		base.read(source, "RDF/XML");
		dataset.getDefaultModel().add(inf);
	}

	/**
 	 * 
	 */
	public void deleteModel() {
		if (dataset != null) {
			dataset.getNamedModel(GRAPH_NAME).removeAll();
			dataset.getDefaultModel().removeAll();
		} else {
			System.out.println("Cannot find any RDF-model!");
		}
	}

	public void resolveCompound() {
		Vector<String> personen = new Vector<>(10);

		dataset.begin(ReadWrite.READ);
		// ///Query to the Graph model//////
		// Query query =
		// QueryFactory.create("select ?o  where{ <file:///C:/Users/Philipp/git/Project-TripleStoreDataBase/RDF_Creation/RDF_Files/Mann> a ?o.}");
		// Query query = QueryFactory.create("SELECT DISTINCT  ?o " +
		// "WHERE {<file:///C:/Users/Philipp/git/Project-TripleStoreDataBase/TripleStoreDB/RDF_Files/Mann> a ?o.}");
		String rdf = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
		String rdfs = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
		String foaf = "prefix foaf: <http://xmlns.com/foaf/0.1/> ";
		String owl = "prefix owl: <http://www.w3.org/2002/07/owl#> ";
		String jhs = "prefix jhs: <file:///C:/Users/Philipp/git/Project-TripleStoreDataBase/TripleStoreDB/RDF_Files/> ";
		Query query = QueryFactory.create(rdf + rdfs + foaf + owl + jhs + "SELECT DISTINCT ?s ?p ?o " + "WHERE {<file:///C:/Users/Philipp/git/Project-TripleStoreDataBase/TripleStoreDB/RDF_Files/Gabi>  ?p ?o .}");
		try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset.getDefaultModel())) {
			// ///Get result//////
			ResultSet results = qexec.execSelect();
			//
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				RDFNode o = soln.get("?o");
				RDFNode p = soln.get("?p");
				RDFNode s = soln.get("?s");
				// Subject
				if (s != null)
					if (s.isLiteral())
						//System.out.println("SUBJECTasLITERAL: " + ((Literal) s).getLexicalForm() + " ");
						System.out.println();
					else if (s.isResource()) {
						Resource r = (Resource) s;
						System.out.print("---> SUBJECT: " + s.toString());
						if (!r.isAnon()) {
							r.getURI();
						}
					}
				// Predicate
				if (p != null)
					if (p.isResource()) {
						Resource r3 = (Resource) p;
						//System.out.print("---> PREDICATE: " + r3.toString());
					} else {
					}
				// Object
				if (o != null)
					if (o.isResource()) {
						Resource r2 = (Resource) o;
						//System.out.print(" | OBJECT: " + r2.toString());
						// System.out.println(r2);
						personen.add(r2.toString());
					}
				//System.out.println();
			}
			personen.forEach(System.out::println);
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataset.end();
		}

		// ///Recursive processing//////

	}

}
