package de.jhs.oldenburg.gg.owl.compound;

public abstract class Condition extends CompoundNode{

	public Condition(String resourceUri) {
		super(resourceUri);
		// TODO Auto-generated constructor stub
	}

	public abstract boolean resolve();
	public abstract boolean resolve(CompoundNode cn);
	
}
