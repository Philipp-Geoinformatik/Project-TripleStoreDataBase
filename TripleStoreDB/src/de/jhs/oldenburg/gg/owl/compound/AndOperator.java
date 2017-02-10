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
public class AndOperator extends ConditionCompound {

	/**
	 * 
	 * @param resourceUri
	 */
	public AndOperator(String resourceUri) {
		super(resourceUri);
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(CompoundNode cn) {
		System.out.println("<<<TRYING TO RESOLVE>>> ");
		System.out.println(this);
		boolean result = false;
		// Get the child nodes of this compound node
		ArrayList<CompoundNode> childs = getChildNodes();
		// wenn blatt element dann frage nach den Values
		if (childs.isEmpty())
			System.err.println("AND need at least two child nodes");
		// sonst lasse die antwort von den child nodes geben!
		System.out.println("=================AND===================");
		for (int i = 0; i < childs.size(); i++) {
			System.out.println("AND LOOP <" + i + ">");
			result = childs.get(i).resolve(cn);
			if (!result) {// if false
				result = false;
				break;
			}
		}
		System.out.println("=================AND END===================");
		return result;
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(ComparisonObject cpv) {
		System.out.println("<<<TRYING TO RESOLVE>>> ");
		System.out.println(this);
		boolean result = false;
		// Get the child nodes of this compound node
		ArrayList<CompoundNode> childs = getChildNodes();
		// wenn blatt element dann frage nach den Values
		if (childs.isEmpty())
			System.err.println("AND need at least two child nodes");
		// sonst lasse die antwort von den child nodes geben!
		System.out.println("=================AND===================");
		for (int i = 0; i < childs.size(); i++) {
			System.out.println("AND LOOP <" + i + ">");
			result = childs.get(i).resolve(cpv);
			if (!result) {// if false
				result = false;
				break;
			}
		}
		System.out.println("=================AND END===================");
		return result;
	}
}
