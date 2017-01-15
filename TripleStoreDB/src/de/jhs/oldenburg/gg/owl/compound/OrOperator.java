package de.jhs.oldenburg.gg.owl.compound;

import java.util.List;

public class OrOperator extends ConditionCompound {

	public OrOperator(String resourceUri) {
		super(resourceUri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean resolve(CompoundNode cn) {
		// TODO Auto-generated method stub
		return super.resolve(cn);
	}

	private List<String> existanceConditions;

	public List<String> getExistanceCondition() {
		return existanceConditions;
	}

	public void setExistanceCondition(List<String> existanceCondition) {
		this.existanceConditions = existanceCondition;
	}
}
