package de.jhs.oldenburg.gg.owl.tdb.manager;

import java.io.File;
import java.util.Date;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;

import de.jhs.oldenburg.gg.owl.compound.CompoundResolver;

/**
 * 
 * @author Philipp Grashorn <br>
 *         From the master project of the Jade University of applied science:
 *         TripleStoreDB <br>
 *         Creation date: 06.01.2017
 *
 */
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
	 * Manages all needed functions
	 */
	public void run() {
		System.out.println("////////////////////////////////////////////////////////////");
		System.out.println("////////////////" + new Date() + "////////////////");
		System.out.println("////////////////////////////////////////////////////////////");
		initDataset();
		//deleteModel();
		//loadGraph(GRAPH_NAME, "I_AM_THE_CREATOR");
		// Voraussetzungen
		//createOntology("RDF_Files/jade-hs_VORAUSSETZUNGEN.owl");
		//createOntology("RDF_Files/jade-hs_VORAUSSETZUNGEN_instance.owl");
		// test --> friends ontology
		// createOntology("RDF_Files/friends.owl");
		// createOntology("RDF_Files/friends-instance.rdf");

		CompoundResolver solver = new CompoundResolver(dataset);
		String parentNode = "http://www.jade-hs.de/RDF/Ontology/Voraussetzung#Voraussetzung_1";
		String predicate = "http://www.jade-hs.de/RDF/Ontology#umfasst";
		//Printing all CompoundNodes
		solver.resolveCompoundByNodeStructure(parentNode, predicate).forEach(System.out::println);
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

	/**
	 * 
	 * @param graphName
	 * @param creator
	 */
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

}
