package de.jhs.oldenburg.gg.owl.parser;


import java.util.ArrayList;
import java.util.HashMap;

import org.apache.jena.query.Dataset;

import de.jhs.oldenburg.gg.owl.compound.CompoundNode;
import de.jhs.oldenburg.gg.owl.compound.SimpleConditionLiteral;

/**
 * 
 * 
 * 
 * @author Fred Bohlmann
 * @author Stefan Buescher
 * @author Philipp Grashorn
 *
 */
public abstract class ComparisonObject {

	private HashMap<String, String> properties;
	private CompoundNode rootNode;
	private Dataset dataset;
	private String resourceUri;
	
	protected Dataset getDataset() {
		return dataset;
	}

	protected void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public ComparisonObject(Dataset dataset){
		super();
		this.dataset=dataset;
		properties= new HashMap<>();
	}

	public ComparisonObject(CompoundNode rootNode) {
		super();
		this.rootNode = rootNode;
		properties= new HashMap<>();
	}

	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, String> values) {
		this.properties = values;
	}

	public CompoundNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(CompoundNode rootNode) {
		this.rootNode = rootNode;
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

}
