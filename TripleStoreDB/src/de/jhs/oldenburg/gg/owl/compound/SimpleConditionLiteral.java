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
public class SimpleConditionLiteral extends CompoundNode {

	/**
	 * 
	 */
	private String literal;

	/**
	 * 
	 */
	@Override
	public boolean resolve(CompoundNode cn) {
		System.out.println("<<<TRYING TO RESOLVE>>> ");
		//System.out.println(this);
		// search literal in the given tree cn
		return compoundContains(literal, cn);
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(ComparisonObject cpv) {
		System.out.println("<<<TRYING TO RESOLVE>>> ");
		System.out.print(this);
		String resUri = this.getParentNode().getResourceUri();
		System.out.println(resUri.substring(resUri.lastIndexOf("#") + 1));
		System.out.println(cpv.getProperties().get(resUri.substring(resUri.lastIndexOf("#"))));
		System.out.println("Comparison: " + literal + "==" + cpv.getProperties().get(resUri.substring(resUri.lastIndexOf("#") + 1)) + "\n\n");
		if (literal.equals(cpv.getProperties().get(resUri.substring(resUri.lastIndexOf("#") + 1))))
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
		str += "<x\n";
		System.out.println();
		return str;
	}
}
