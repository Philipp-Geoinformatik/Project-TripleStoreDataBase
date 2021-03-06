package de.jhs.oldenburg.gg.project.playground;

import java.io.File;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;

/**
 * 
 * 
 * 
 * @author Fred Bohlmann
 * @author Stefan Buescher
 * @author Philipp Grashorn
 *
 */
public interface ITDBManager {

	public Dataset createOrConnectTDB(String datasetName, String datasetLocation);

	public boolean insertDataFromFile(File file, Lang lang);
}
