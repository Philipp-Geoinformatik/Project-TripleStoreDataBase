package de.jhs.oldenburg.gg.project.testscenario;

import java.util.ArrayList;

import de.jhs.oldenburg.gg.project.pattern.comparison.ComparisonObject;
import de.jhs.oldenburg.gg.project.pattern.compound.CompoundNode;
import de.jhs.oldenburg.gg.project.pattern.compound.SimpleConditionLiteral;

/**
 * 
 * 
 * 
 * @author Fred Bohlmann
 * @author Stefan Buescher
 * @author Philipp Grashorn
 *
 */
public class FacilityEvaluator {

	/**
	 * Initializes the resolving-method of the given {@link CompoundNode} and the destinated {@link Facility}. This
	 * function gives the opportunity to implement additional function.
	 * executions.
	 * 
	 * @param c1
	 * @param facility
	 */
	public static void evaluate(CompoundNode c1, Facility facility) {

		boolean result = false;
		System.out.println("Xx============================EVALUATION START============================xX");
		System.out.println("Xx=========================RUN WITH CompareObjects========================xX");
		result = c1.resolve(facility);
		System.out.println("\nEvaluation result : <" + result + ">");
		System.out.println("Xx============================EVALUATION END============================xX");
	}

	/**
	 * Initializes the resolving-method of the given {@link CompoundNode} and a second one which represents a {@link Facility} by a node structure. This
	 * function gives the opportunity to implement additional function.
	 * executions.
	 * @param c1
	 * @param c2
	 */
	public static void evaluate(CompoundNode c1, CompoundNode c2) {

		ComparisonObject comObjectWithNode = new Facility(c2);

		boolean result = false;
		System.out.println("Xx============================EVALUATION START============================xX");
		System.out.println("Xx=============================RUN WITH NODES=============================xX");
		result = c1.resolve(comObjectWithNode);
		System.out.println("\nEvaluation result : <" + result + ">");
		System.out.println("Xx============================EVALUATION END============================xX");
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
