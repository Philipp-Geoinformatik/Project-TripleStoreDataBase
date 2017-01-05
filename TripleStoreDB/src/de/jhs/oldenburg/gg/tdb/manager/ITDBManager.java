package de.jhs.oldenburg.gg.tdb.manager;

import java.io.File;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;

/**
 * 
 * @author Philipp Grashorn <br>
 *         From the master project of the Jade University of applied science:
 *         TripleStoreDB <br>
 *         Creation date: 06.01.2017
 *
 */
public interface ITDBManager {

	public Dataset createOrConnectTDB(String datasetName, String datasetLocation);

	public boolean insertDataFromFile(File file, Lang lang);
}
