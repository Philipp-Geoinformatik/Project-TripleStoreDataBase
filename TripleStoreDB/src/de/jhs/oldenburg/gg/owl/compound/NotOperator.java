package de.jhs.oldenburg.gg.owl.compound;

import de.jhs.oldenburg.gg.owl.parser.ComparisonObject;

/**
 * 
 * 
 * 
 * @author Fred Bohlmann
 * @author Stefan Buescher
 * @author Philipp Grashorn
 *
 */
public class NotOperator extends ConditionCompound {

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
