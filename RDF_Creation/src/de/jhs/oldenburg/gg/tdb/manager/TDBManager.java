package de.jhs.oldenburg.gg.tdb.manager;

import java.io.File;

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
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.StoreConnection;
import org.apache.jena.tdb.TDBFactory;

/**
 * 
 * @author Grashorn
 *
 */
public class TDBManager implements ITDBManager {

	private Dataset dataset;

	public static void main(String[] args) {
		TDBManager a = new TDBManager();
		a.createOrConnectTDB("TestFolder", "myTDB");
		// Insert SCHEMA into database
		File f = new File("RDF_Files/friends.owl");
		//
		System.out.println(f.getAbsoluteFile().getAbsolutePath());
		// a.insertDataFromFile(f, "OWL");
		// a.printRDFContent();
		// a.makeSPARQLReq("SELECT  *  {?s ?p ?o} ");
		a.resolveCompound("prefix pers: <file:///C:/Users/Philipp/git/Project-TripleStoreDataBase/RDF_Creation/RDF_Files/>" + "  select ?subject where { ?subject pers:* ?subject.}");
	}

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
		// System.out.println(dataset.getContext());
		return dataset;
	}

	@Override
	public boolean insertDataFromFile(File file, String fileType) {
		boolean transactionSucess = false;
		try {
			Model model = RDFDataMgr.loadModel(file.getAbsolutePath());
			Model m = dataset.getDefaultModel().add(model);
			transactionSucess = true;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return transactionSucess;
	}

	public void deleteModel() {
		if (dataset != null) {
			Model m = dataset.getDefaultModel();
			m.remove(m);
		} else {
			System.out.println("Cannot find any RDF-model!");
		}

	}

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

	@Override
	public Model getModelfromTDB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printRDFContent() {
		Model m = dataset.getDefaultModel();
		StmtIterator iter = m.listStatements();
		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement(); // get next statement
			Resource subject = stmt.getSubject(); // get the subject
			Property predicate = stmt.getPredicate(); // get the predicate
			RDFNode object = stmt.getObject(); // get the object
			//
			System.out.print("Subject( " + subject.toString() + " ) -->");
			System.out.print(" predicate( " + predicate.toString() + " ) -->");
			if (object instanceof Resource) {
				System.out.print(" object( " + object.toString() + " )");
			} else {
				// object is a literal
				System.out.print(" ObjectLiteral( " + object.toString() + " )");
			}
		}
		return null;
	}

	@Override
	public String makeSPARQLReq(String query) {
		dataset.begin(ReadWrite.READ);

		try (QueryExecution qExec = QueryExecutionFactory.create(query, dataset)) {
			ResultSet rs = qExec.execSelect();
			ResultSetFormatter.out(rs);
		} finally {
			dataset.end();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean resolveCompound() {
		boolean result = false;
		// building the query
		String queryString = "";
		Query query = QueryFactory.create(queryString);
		// requesting the compound instance
		if (dataset != null) {
			dataset.begin(ReadWrite.READ);
			// get the existing model
			Model model = dataset.getDefaultModel();
			// Execute query
			try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
				ResultSet results = qexec.execSelect();
				// for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				System.out.println(soln);
				ResultSetFormatter.out(System.out, results, query);
				// }
			} finally {
				dataset.end();
			}

		}
		// resolve answer

		return result;
	}
}
