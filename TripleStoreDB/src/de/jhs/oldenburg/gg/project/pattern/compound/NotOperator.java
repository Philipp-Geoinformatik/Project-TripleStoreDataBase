package de.jhs.oldenburg.gg.project.pattern.compound;

import de.jhs.oldenburg.gg.project.pattern.comparison.ComparisonObject;

/**
 * 
 * 
 * 
 * @author Fred Bohlmann
 * @author Stefan Buescher
 * @author Philipp Grashorn
 *
 */
public class NotOperator extends CompoundNode {

	/**
	 * 
	 * @param resourceUri
	 */
	public NotOperator(String resourceUri) {
		super(resourceUri);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(ComparisonObject cpv) {
		return !getChildNodes().get(0).resolve(cpv);
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(CompoundNode cn) {
		return !getChildNodes().get(0).resolve(cn);
	}
}
