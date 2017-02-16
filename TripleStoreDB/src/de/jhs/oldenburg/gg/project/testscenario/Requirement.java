package de.jhs.oldenburg.gg.project.testscenario;

import de.jhs.oldenburg.gg.project.pattern.compound.CompoundNode;

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
