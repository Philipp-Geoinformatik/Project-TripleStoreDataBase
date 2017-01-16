package de.jhs.oldenburg.gg.owl.compound;

import java.util.ArrayList;

public class CompoundEvaluator {

	public static void evaluate(CompoundNode c1, CompoundNode c2) {

		boolean result = false;
		result = c1.resolve(c2);
		System.out.println("Evaluation result : <" + result + ">");
	}

	/**
	 * 
	 * @param resourceName
	 * @param node
	 * @return
	 */
	public String getValueOf(String resourceName, CompoundNode node) {
		System.out.println("BEGINNING search with: " + node.getResourceUri());
		System.out.println(resourceName);
		SimpleConditionLiteral cs = null;
		//
		ArrayList<CompoundNode> nodes = new ArrayList<CompoundNode>();
		nodes.add(node);
		//
		int i = 0;
		//
		while (true) {
			if (i == nodes.size())
				return null;
			CompoundNode n = nodes.get(i++);
			nodes.addAll(n.getChildNodes());
			if (n instanceof SimpleConditionLiteral) {
				cs = (SimpleConditionLiteral) n;
				if (cs.getParentNode().getResourceUri().contains(resourceName))
					return "" + cs.getLiteral();
			}
			System.out.println(i);
		}
	}

}
