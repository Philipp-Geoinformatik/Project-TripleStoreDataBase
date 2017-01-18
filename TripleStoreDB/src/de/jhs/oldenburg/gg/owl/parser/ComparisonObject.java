package de.jhs.oldenburg.gg.owl.parser;


import java.util.ArrayList;
import java.util.HashMap;

import de.jhs.oldenburg.gg.owl.compound.CompoundNode;
import de.jhs.oldenburg.gg.owl.compound.SimpleConditionLiteral;

public abstract class ComparisonObject {

	private HashMap<String, String> properties;
	private CompoundNode rootNode;

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

}
