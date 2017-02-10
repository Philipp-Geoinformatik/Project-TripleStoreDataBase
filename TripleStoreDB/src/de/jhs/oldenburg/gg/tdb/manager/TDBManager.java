package de.jhs.oldenburg.gg.tdb.manager;

import java.io.File;

import javax.transaction.TransactionRequiredException;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDBFactory;

import de.jhs.oldenburg.gg.tdb.utils.TDBPrinter;

/**
 * 
 * 
 * 
 * @author Fred Bohlmann
 * @author Stefan Buescher
 * @author Philipp Grashorn
 *
 */
public class TDBManager implements ITDBManager {

	private Dataset dataset;

	/**
	 * 
	 */
	@Override
	public Dataset createOrConnectTDB(String datasetLocation, String datasetName) {
		String dir = datasetLocation + "/" + datasetName;
		File theDir = new File(dir);
		if (!theDir.exists()) {
			theDir.mkdirs();
			System.out.println("New directory created");
		}
		// erstelle ein dataset
		Dataset dataset = TDBFactory.createDataset(datasetLocation + "/" + datasetName);
		this.dataset = dataset;
		System.out.println(dataset.getContext());
		return dataset;
	}

	/**
	 * 
	 */
	@Override
	public boolean insertDataFromFile(File file, Lang lang) {
		try {
			Model m = RDFDataMgr.loadModel(file.getAbsolutePath(), lang);
			dataset.getDefaultModel().add(m);
			System.out.println("LoadModel");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean resolveCompoundTEST1() {
		boolean result = false;
		// building the query
		Query query = QueryFactory.create("select distinct ?s ?x ?o where { ?s ?x ?o.}");
		// Query query2 =
		// QueryFactory.create("prefix jhs: <http://www.jade-hs.de> CONSTRUCT { <hhttp://www.jade-hs.de/Existenzbedingung/Existenzbedingung_4> jhs:Name ?name } WHERE"
		// + "{ ?x jhs:name ?name }");
		// requesting the compound instance
		String baseURI = query.getBaseURI();
		System.out.println(baseURI);
		if (dataset != null) {
			dataset.begin(ReadWrite.READ);
			// get the existing model
			Model model = dataset.getDefaultModel();
			// Execute query
			try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
				ResultSet results = qexec.execSelect();

				for (; results.hasNext();) {
					QuerySolution soln = results.nextSolution();
					RDFNode s = soln.get("?s");
					RDFNode o = soln.get("?o");
					if (s.isLiteral())
						((Literal) s).getLexicalForm();
					else if (s.isResource()) {
						Resource r = (Resource) s;
						// System.out.println(r);
						if (!r.isAnon()) {
							r.getURI();
						}
					}
					if (o.isResource()) {
						Resource r2 = (Resource) o;
						// System.out.println(r2);
					}
				}
			} finally {
				dataset.end();
			}
		} else
			System.out.println("Dataset = null");
		// resolve answer
		return result;
	}

	public void resolveCompoundTEST2() {

		// ///Query to the Graph model//////

		// ///Get result//////
		// ///Recursive processing//////

	}

	

	/**
 	 * 
	 */
	public void deleteModel() {
		if (dataset != null) {
			Model m = dataset.getDefaultModel();
			m.removeAll();
		} else {
			System.out.println("Cannot find any RDF-model!");
		}
	}

	// ====Getters and Setters
	/**
	 * 
	 * @return
	 */
	public Dataset getDataset() {
		return dataset;
	}

	/**
	 * 
	 * @param dataset
	 */
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TDBManager a = new TDBManager();
		a.createOrConnectTDB("TestFolder", "myTDB");
		// Insert SCHEMA into database
		File f = new File("RDF_Files/jade-hs_VORAUSSETZUNGEN_instance.rdf");
		//
		// System.out.println(f.getAbsoluteFile().getAbsolutePath());
		a.insertDataFromFile(f, Lang.RDFXML);
		// a.makeSPARQLReq("SELECT  *  {?s ?p ?o} ");
		TDBPrinter.printModelContent(a.getDataset());
		a.resolveCompoundTEST1();
	}
}
