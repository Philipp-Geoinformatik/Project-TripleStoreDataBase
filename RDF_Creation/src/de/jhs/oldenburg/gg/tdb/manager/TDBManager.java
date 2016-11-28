package de.jhs.oldenburg.gg.tdb.manager;

import java.io.File;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
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
		// File f = new File("RDF_Files/owl.ttl");
		//
		// a.insertDataFromFile(f, "TTL");
		// a.printRDFContent();
		a.makeSPARQLReq("SELECT * {?s ?p ?o} ");
		// a.testReadingData();
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
		return dataset;
	}

	@Override
	public boolean insertDataFromFile(File file, String fileType) {
		boolean transactionSucess = true;
		Model model = RDFDataMgr.loadModel("RDF_Files/owl.ttl");
		dataset.getDefaultModel().add(model);
		return transactionSucess;
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
}
