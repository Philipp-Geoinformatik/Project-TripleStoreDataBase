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
public class ExistanceCondition extends CompoundNode {

	/**
	 * 
	 * @param resourceUri
	 */
	public ExistanceCondition(String resourceUri) {
		super(resourceUri);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns the child nodes' Literal value. Child node is of class
	 * {@link SimpleConditionLiteral}
	 * 
	 * @return
	 */
	public String getConditionLiteral() {
		if (!getChildNodes().isEmpty())
			return getChildNodes().get(0).getResourceUri();
		else
			return null;
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(CompoundNode cn) {
		System.out.println("<<<TRYING TO RESOLVE>>> ");
		System.out.println(this);
		// if (this.getChildNodes().get(0) instanceof SimpleConditionLiteral)
		return getChildNodes().get(0).resolve(cn);
		// else {
		// return false;
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(ComparisonObject cpv) {
		System.out.println("<<<TRYING TO RESOLVE>>> ");
		System.out.println(this);
		return getChildNodes().get(0).resolve(cpv);
	}

	/*
	 * public static void main(String[] args) { ExistanceCondition a = new
	 * ExistanceCondition("http://www.hallo.de/ontology/moin#moin_1");
	 * ArrayList<CompoundNode> l = new ArrayList<CompoundNode>(); l.add(new
	 * CompoundNode("http://www.hallo.de/ontology/moin#moin_1"));
	 * a.setChildNodes(l); System.out.println("FRAGE; " + a.resolve(new
	 * ExistanceCondition("http://www.hallo.de/ontology/moin#moin_1")));
	 * 
	 * }
	 */
}
