package de.jhs.oldenburg.gg.owl.tdb.manager;

import java.io.File;
import java.util.ArrayList;
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

import de.jhs.oldenburg.gg.facility.Facility;
import de.jhs.oldenburg.gg.facility.FacilityEvaluator;
import de.jhs.oldenburg.gg.owl.compound.CompoundNode;
import de.jhs.oldenburg.gg.owl.compound.CompoundResolver;
import de.jhs.oldenburg.gg.owl.parser.ComparisonObject;
import de.jhs.oldenburg.gg.requirement.Requirement;

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
		deleteModel();
		// loadGraph(GRAPH_NAME, "I_AM_THE_CREATOR");
		// Voraussetzungen
		createOntology("RDF_Files/jade-hs_VORAUSSETZUNGEN.owl");
		// createOntology("RDF_Files/jade-hs_VORAUSSETZUNGEN_instance.owl");
		createOntology("RDF_Files/jade-hs_VORAUSSETZUNGEN_instance-TEST.owl");

		// Immobilien
		createOntology("RDF_Files/jade-hs_IMMOBILIE.owl");
		// createOntology("RDF_Files/jade-hs_IMMOBILIE_instance.owl");
		createOntology("RDF_Files/jade-hs_IMMOBILIE_instance-TEST.owl");

		// Resolving
		CompoundResolver solver = new CompoundResolver(dataset);
		String nameSpace = "http://www.jade-hs.de/RDF/Ontology";
		// Printing all CompoundNodes
		CompoundNode vorraussetzung = solver.resolveCompound("http://www.jade-hs.de/RDF/Ontology/Voraussetzung#Voraussetzung_1", nameSpace);// .forEach(System.out::println)
		CompoundNode immobilie = solver.resolveCompound("http://www.jade-hs.de/RDF/Ontology/Immobilie#Immobilie_1", nameSpace);// .forEach(System.out::println)
		//String query = "";
		//query += " SELECT DISTINCT ?o ?p  WHERE {<http://www.jade-hs.de/RDF/Ontology/Fenster#Fenster_1> <http://www.jade-hs.de/RDF/Ontology#Alter> ?o .}";
		Facility facility = new Facility("http://www.jade-hs.de/RDF/Ontology/Immobilie#Immobilie_1",dataset);
		Requirement requirement = new Requirement(vorraussetzung);
		FacilityEvaluator.evaluate(requirement.getRootNode(), facility);
		//FacilityEvaluator.evaluate(vorraussetzung.get(0), immobilie.get(0));
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
