package de.jhs.oldenburg.gg.owl.compound;

import java.util.List;

public class OrOperator extends ConditionCompound {

	public OrOperator(String resourceUri) {
		super(resourceUri);
		// TODO Auto-generated constructor stub
	}

	private List<String> existanceConditions;
	
	@Override
	public boolean resolve() {
		return false;
		// TODO Auto-generated method stub

	}

	public List<String> getExistanceCondition() {
		return existanceConditions;
	}

	public void setExistanceCondition(List<String> existanceCondition) {
		this.existanceConditions = existanceCondition;
	}
}
