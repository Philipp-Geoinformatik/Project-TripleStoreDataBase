package de.jhs.oldenburg.gg.facility;

import de.jhs.oldenburg.gg.owl.compound.CompoundNode;
import de.jhs.oldenburg.gg.owl.parser.ComparisonObject;

public class Facility extends ComparisonObject {

	public Facility(CompoundNode rootNode) {
		super(rootNode);
	}

	private Double countFloors(String literal) {
		double fl = Double.parseDouble(literal);
		//TODO count floors by search in compound tree
		CompoundNode cn = this.getRootNode();
		return fl;
	}
}
