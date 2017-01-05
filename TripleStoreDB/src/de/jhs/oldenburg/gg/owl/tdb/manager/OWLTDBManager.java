package de.jhs.oldenburg.gg.owl.tdb.manager;

import java.io.File;
import java.util.ArrayList;
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
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;

import de.jhs.oldenburg.gg.rdf.vocabulary.Friends;
import de.jhs.oldenburg.gg.tdb.utils.TDBPrinter;

public class OWLTDBManager {

	/**
	 * 
	 */
	private Dataset dataset;
	private String GRAPH_NAME = "myGraph";
	private String baseDir = new File(".").getAbsoluteFile().getParentFile().toPath().toUri().toString();
	private ArrayList<String> nodeHeap = new ArrayList<>();
	private int counter = 0;

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
		
		createOntology("RDF_Files/jade-hs_VORAUSSETZUNGEN.owl");
		createOntology("RDF_Files/jade-hs_VORAUSSETZUNGEN_instance.owl");
		//createOntology("RDF_Files/friends.owl");
		//createOntology("RDF_Files/friends-instance.rdf");
		// TDBPrinter.printModelContent(dataset);
		resolveCompound();
		// resolveCompoundByVocabularyLib();
		//TDBPrinter.printModelContent(dataset);
		TDBPrinter.printSPARQLReq("SELECT DISTINCT ?s ?p ?o  WHERE {?s  ?p  ?o .}", dataset);
		// TDBPrinter.printSPARQLReq("prefix jhs: <http://www.jade-hs.de/RDF/Ontology#> "
		// +
		// "prefix owl: <http://www.w3.org/2002/07/owl#> SELECT DISTINCT ?s ?p ?o  WHERE { ?s <http://www.jade-hs.de/RDF/Ontology#hatFreund> ?o . ?s ?p ?o }",
		// this.dataset);
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
		// System.out.println(dataset.getDefaultModel().isEmpty());
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
		// ///Recursive processing//////
		// ArrayList<String> rsList = getAllLinkedObjects("Gabi",
		// "hatFreund");//Query for root object
		// //then traversing all child nodes
		// rsList.forEach(nodeName -> {
		// getAllLinkedObjects(nodeName, "hatFreund");
		// });
		getAllLinkedObjects("http://www.jade-hs.de/RDF/Ontology/Voraussetzung#Voraussetzung_1", "http://www.jade-hs.de/RDF/Ontology#umfasst");
		this.nodeHeap.forEach(s -> {
			System.out.println(s);
		});
	}

	/**
	 * This method stores all childnodes of the parent node in the private field
	 * <b>nodeHeap</b>.
	 * 
	 * ATTENTION! This function does check for self referenced nodes and cycles
	 * but does not stop immediately.
	 * 
	 * @param nodeName
	 * @param predicate
	 * @return
	 */
	private void getAllLinkedObjects(String nodeName, String predicate) {
		Vector<String> nodes = new Vector<>(10);

		dataset.begin(ReadWrite.READ);
		// ///Query to the Graph model//////
		String rdf = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
		String rdfs = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
		String foaf = "prefix foaf: <http://xmlns.com/foaf/0.1/> ";
		String owl = "prefix owl: <http://www.w3.org/2002/07/owl#> ";
		String jhs = "prefix jhs: <http://www.jade-hs.de/RDF/Ontology#> ";
		//
		Query query = QueryFactory.create(rdf + rdfs + foaf + owl + jhs + "SELECT DISTINCT ?s ?p ?o  " + "WHERE { <" + nodeName + "> <" + predicate + "> ?o . }");
//		Query query = QueryFactory.create(rdf + rdfs + foaf + owl + jhs + " SELECT ?y WHERE { <http://www.jade-hs.de/RDF/Ontology#Person> rdf:comment+ ?y. }");

		try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset.getDefaultModel())) {
			// ///Get results//////
			ResultSet results = qexec.execSelect();
			//
			//ResultSetFormatter.out(results);
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				RDFNode s = soln.get("?s");
				RDFNode p = soln.get("?p");
				RDFNode o = soln.get("?o");
				// Subject
				if (s != null)
					if (s.isLiteral())
						System.out.println("SUBJECTasLITERAL: " + ((Literal) s).getLexicalForm() + " ");
					else if (s.isResource()) {
						Resource r = (Resource) s;
						// System.out.print("---> SUBJECT: " + s.toString());
						if (!r.isAnon()) {
							r.getURI();
						}
					}
				// Predicate
				if (p != null)
					if (p.isResource()) {
						Resource r3 = (Resource) p;
						// System.out.print("---> PREDICATE: " + r3.toString());
					} else {
					}
				// Object
				if (o != null)
					if (o.isResource()) {
						Resource r2 = (Resource) o;
						// System.out.println(" | OBJECT: " + r2.toString());
						// System.out.println(r2);
						nodes.add(r2.toString());
					}
			}
			counter++;
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataset.end();
		}

		// Adding Nodes to heap
		boolean b = true;
		for (int i = 0; i < nodes.size(); i++) {
			if (nodeHeap.contains(nodes.get(i))) {
				System.err.println("Invalid compound detected! Node: " + nodes.get(i) + " already exists in node heap");
				b = false;
			} else {
				this.nodeHeap.add(nodes.get(i));
			}
		}
		if (b)
			nodes.forEach(childNodeName -> {
				getAllLinkedObjects(childNodeName, predicate);
			});
	}

	/**
	 * 
	 */
	public void resolveCompoundByVocabularyLib() {
		try {
			dataset.begin(ReadWrite.READ);
			Model model = dataset.getDefaultModel();
			ResIterator iter = model.listSubjectsWithProperty(Friends.hatFreund);
			StmtIterator it = model.listStatements(new SimpleSelector(Friends.Mann, null, (Resource) null));
			while (iter.hasNext()) {
				Resource r = iter.nextResource();
				System.out.println(r);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dataset.end();
		}

	}

}
