package de.jhs.oldenburg.gg.requirement;

import de.jhs.oldenburg.gg.owl.compound.CompoundNode;

public class Requirement {
	
	private CompoundNode rootNode;
	
	public CompoundNode getRootNode() {
		return rootNode;
	}

	public Requirement(CompoundNode rootNode) {
		this.rootNode=rootNode;
	}

}
