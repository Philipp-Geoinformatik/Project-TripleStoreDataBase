package de.jhs.oldenburg.gg.facility;

import java.util.ArrayList;

import org.apache.jena.query.Dataset;

import de.jhs.oldenburg.gg.owl.compound.CompoundNode;
import de.jhs.oldenburg.gg.owl.compound.SimpleConditionLiteral;
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
public class FacilityEvaluator {
	
	public static void evaluate(CompoundNode c1, Facility facility){
				
		boolean result = false;
		System.out.println("Xx============================EVALUATION START============================xX");
		System.out.println("Xx=========================RUN WITH CompareObjects========================xX");
		result = c1.resolve(facility);
		System.out.println("\nEvaluation result : <" + result + ">");
		System.out.println("Xx============================EVALUATION END============================xX");
	}
	

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
