package de.jhs.oldenburg.gg.owl.tdb.manager;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
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
		System.out.println(new Date().getTime());
		System.out.println("sdfdsf");
		System.out.println("sdfdsf");
		System.out.println("sdfdsf");
		System.out.println("sdfdsf");
		initDataset();
		// deleteModel();
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
		System.out.println("dsfds");
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
		if (dataset != null)
			dataset.getNamedModel(GRAPH_NAME).removeAll();
		else {
			System.out.println("Cannot find any RDF-model!");
		}
	}

	public void resolveCompound() {
		dataset.begin(ReadWrite.READ);
		// ///Query to the Graph model//////
		// Query query =
		// QueryFactory.create("select ?o  where{ <file:///C:/Users/Philipp/git/Project-TripleStoreDataBase/RDF_Creation/RDF_Files/Mann> a ?o.}");
		Query query = QueryFactory.create("SELECT DISTINCT ?property WHERE {  ?s ?property ?o .}");
		try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset.getNamedModel(GRAPH_NAME))) {

			// ///Get result//////
			ResultSet results = qexec.execSelect();
			ResultSetFormatter.out(System.out, results);
			// List<String> rsm = results.getResultVars();
			// System.out.println(rsm.size());
			// rsm.forEach(System.out::println);
			// StmtIterator it = rsm.listStatements();
			//
			// while (it.hasNext()) {
			// Statement type = it.nextStatement();
			// if (!type.asTriple().getObject().isLiteral())
			// System.out.println(type.asTriple().getObject().getURI());
			// }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataset.end();
		}

		// ///Recursive processing//////

	}

}
