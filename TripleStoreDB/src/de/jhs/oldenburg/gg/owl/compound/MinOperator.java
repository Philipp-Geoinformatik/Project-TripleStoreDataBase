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
public class MinOperator extends ComparisonCondition {

	public MinOperator(String resourceUri) {
		super(resourceUri);
	}

	@Override
	public boolean resolve(CompoundNode cn) {
		String propName = getChildNodes().get(0).getResourceUri();
		propName = propName.substring(propName.lastIndexOf("#"));
		Double lit = Double.parseDouble(((SimpleConditionLiteral) getChildNodes().get(0).getChildNodes().get(0)).getLiteral());
		Double v = null;
		if (cn instanceof ExistanceCondition)
			v = Double.parseDouble(((SimpleConditionLiteral) cn.getChildNodes().get(0)).getLiteral());

		if (lit >= v)
			return true;
		else
			return false;
	}

	
	@Override
	public boolean resolve(ComparisonObject cpv) {
		String propName = getChildNodes().get(0).getResourceUri();
		propName = propName.substring(propName.lastIndexOf("#"));
		Double lit = Double
				.parseDouble(((SimpleConditionLiteral) getChildNodes().get(0).getChildNodes().get(0)).getLiteral());
		Double v = Double.parseDouble(cpv.getProperties().get(propName));
		if (lit >= v)
			return true;
		else
			return false;
	}
}
