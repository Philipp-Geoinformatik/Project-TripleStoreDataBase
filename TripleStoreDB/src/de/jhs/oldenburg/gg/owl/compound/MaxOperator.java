package de.jhs.oldenburg.gg.owl.compound;

import de.jhs.oldenburg.gg.owl.parser.ComparisonObject;

public class MaxOperator extends ComparisonCondition {

	/**
	 * 
	 * @param resourceUri
	 */
	public MaxOperator(String resourceUri) {
		super(resourceUri);
		// TODO Auto-generated constructor stubs
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(CompoundNode cn) {
		String lit = ((SimpleConditionLiteral) getChildNodes().get(0).getChildNodes().get(0)).getLiteral();
		
		return false;
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(ComparisonObject cpv) {
		String propName = getChildNodes().get(0).getResourceUri();
		propName = propName.substring(propName.lastIndexOf("#"));
		Double lit = Double
				.parseDouble(((SimpleConditionLiteral) getChildNodes().get(0).getChildNodes().get(0)).getLiteral());
		Double v = Double.parseDouble(cpv.getProperties().get(propName));

		if (lit <= v)
			return true;
		else
			return false;
	}
}
