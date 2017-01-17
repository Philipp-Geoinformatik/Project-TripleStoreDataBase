package de.jhs.oldenburg.gg.owl.compound;

import de.jhs.oldenburg.gg.owl.parser.ComparisonObject;

/**
 * 
 * @author Philipp Grashorn From the master project of the Jade University of
 *         applied science: TripleStoreDB
 *
 *         Creation date: 14.01.2017
 *
 */
public class SimpleConditionLiteral extends CompoundNode {

	/**
	 * 
	 */
	private String literal;

	/**
	 * 
	 */
	@Override
	public boolean resolve(CompoundNode/* ComparisonObject */ cn) {
		System.out.println("<<<TRYING TO RESOLVE>>> ");
		System.out.println(this);
		String lit = null;

		// Vater FÜr existenzbendingungsname
		// das literal selber
		// suche in HashMap von ComparisonObjekt --> literal =
		// Hashmap.get(existenzbendingungsname);

		// search literal in the given tree cn
		return compoundContains(literal, cn);
	}

	@Override
	public boolean resolve(ComparisonObject cpv) {
		System.out.println("<<<TRYING TO RESOLVE>>> ");
		System.out.println(this);
		String resUri = this.getParentNode().getResourceUri();
		if (literal.equals(cpv.getProperties().get(resUri.substring(resUri.lastIndexOf("#")))))
			return true;
		return false;
	}

	/**
	 * 
	 * @param resourceUri
	 * @param literal
	 */
	public SimpleConditionLiteral(String resourceUri, String literal) {
		super(resourceUri);
		this.literal = literal;
	}

	/**
	 * 
	 * @return
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * 
	 * @param literal
	 */
	public void setLiteral(String literal) {
		this.literal = literal;
	}

	@Override
	public String toString() {
		String str = "x> RESOURCE <" + this.getClass().getSimpleName() + ">: " + getResourceUri() + "\n";
		if (getParentNode() != null) {
			str += "PARENT: " + getParentNode().getResourceUri() + "\n";
			str += "LITERAL: " + literal + "\n";
		}
		if (getChildNodes() != null && !getChildNodes().isEmpty()) {

			for (CompoundNode compoundNode : getChildNodes()) {
				str += "|  CHILD: <" + compoundNode.getResourceUri() + "> \n";
			}
		}
		str += "<x";
		System.out.println();
		return str;
	}
}
