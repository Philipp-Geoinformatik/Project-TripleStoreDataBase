package de.jhs.oldenburg.gg.owl.compound;

import java.util.ArrayList;

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
public class OrOperator extends ConditionCompound {

	/**
	 * 
	 * @param resourceUri
	 */
	public OrOperator(String resourceUri) {
		super(resourceUri);
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(CompoundNode cn) {
		System.out.println("<<<TRYING TO RESOLVE>>> ");
		System.out.println(this);
		// Get the child nodes of this compound node
		ArrayList<CompoundNode> childs = getChildNodes();
		// wenn blatt element dann frage nach den Values
		if (childs.isEmpty())
			System.err.println("OR need at least two child nodes");
		// sonst lasse die antwort von den child nodes geben!
		System.out.println("=================OR===================");
		for (int j = 0; j < childs.size(); j++) {
			System.out.println("OR LOOP <" + j + ">");
			if (childs.get(j).resolve(cn)) {
				System.out.println("=================OR END===================");
				return true;
			}
		}
		System.out.println("=================OR END===================");
		return false;
	}

	@Override
	public boolean resolve(ComparisonObject cpv) {
		System.out.println("<<<TRYING TO RESOLVE>>> ");
		System.out.println(this);
		// Get the child nodes of this compound node
		ArrayList<CompoundNode> childs = getChildNodes();
		// wenn blatt element dann frage nach den Values
		if (childs.isEmpty())
			System.err.println("OR need at least two child nodes");
		// sonst lasse die antwort von den child nodes geben!
		System.out.println("=================OR===================");
		for (int j = 0; j < childs.size(); j++) {
			System.out.println("OR LOOP <" + j + ">");
			if (childs.get(j).resolve(cpv)) {
				System.out.println("=================OR END===================");
				return true;
			}
		}
		System.out.println("=================OR END===================");
		return false;
	}

}
