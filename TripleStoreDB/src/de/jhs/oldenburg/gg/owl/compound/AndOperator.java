package de.jhs.oldenburg.gg.owl.compound;

import java.util.ArrayList;

public class AndOperator extends ConditionCompound {

	public AndOperator(String resourceUri) {
		super(resourceUri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean resolve() {
		boolean result = false;
		// Get the child nodes of this compound node
		ArrayList<CompoundNode> childs = getChildNodes();

		// wenn blatt element dann frage nach den Values
		// sonst lasse die antwort von den childnodes geben!

		for (int i = 0; i < childs.size(); i++) {
			if (!result)
				result = false;
		}
		return result;
	}
}
