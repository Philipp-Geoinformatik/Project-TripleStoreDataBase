package de.jhs.oldenburg.gg.owl.compound;

import java.util.List;

public class OrOperator extends ConditionCompound {

	private List<String> existanceCondition;
	
	@Override
	public boolean resolve() {
		return false;
		// TODO Auto-generated method stub

	}

	public List<String> getExistanceCondition() {
		return existanceCondition;
	}

	public void setExistanceCondition(List<String> existanceCondition) {
		this.existanceCondition = existanceCondition;
	}
}
