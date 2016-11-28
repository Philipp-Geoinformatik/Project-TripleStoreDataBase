package de.jhs.oldenburg.gg.tdb.manager;

import java.io.File;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

public interface ITDBManager {

	public Dataset createOrConnectTDB(String datasetName, String datasetLocation);
	public boolean insertDataFromFile(File file, String fileType);
	public Model getModelfromTDB();
	public String printRDFContent();
	public String makeSPARQLReq(String query);
}
