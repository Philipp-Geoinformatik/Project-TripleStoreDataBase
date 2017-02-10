package de.jhs.oldenburg.gg.requirement;

import de.jhs.oldenburg.gg.owl.compound.CompoundNode;

/**
 * 
 * 
 * 
 * @author Fred Bohlmann
 * @author Stefan Buescher
 * @author Philipp Grashorn
 *
 */
public class Requirement {
	
	private CompoundNode rootNode;
	
	public CompoundNode getRootNode() {
		return rootNode;
	}

	public Requirement(CompoundNode rootNode) {
		this.rootNode=rootNode;
	}

}
