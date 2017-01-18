package de.jhs.oldenburg.gg.facility;

import de.jhs.oldenburg.gg.owl.compound.CompoundNode;
import de.jhs.oldenburg.gg.owl.compound.SimpleConditionLiteral;
import de.jhs.oldenburg.gg.owl.parser.ComparisonObject;

/**
 * 
 * @author Et. Al. From the master project of the Jade University of applied
 *         science: TripleStoreDB
 *
 *         Creation date: 18.01.2017
 *
 */
public class Facility extends ComparisonObject {

	// --Instance-Test--
	// Hausalter
	// Grundfl�chenzahl
	// Geschosszahl
	// Dachtyp
	// Dachalter
	// Bauweise
	// Wohneinheitszahl
	// Anzahl Zimmer

	/**
	 * 
	 * @param rootNode
	 */
	public Facility(CompoundNode rootNode) {
		super(rootNode);
		this.getProperties().put("ImmobilieAlter", getFacilityAge());
		this.getProperties().put("GFZ", getGFZ());
		this.getProperties().put("GeschossAnzahl", countFloors());
		this.getProperties().put("WohneinheitenAnzahl", countLivingAreas());
		this.getProperties().put("GeschaeftseinheitenAnzahl", countBusinessAreas());

		System.out.println("XX>--FACILITY PROPERTIES--<XX\nx>");
		for (String s : this.getProperties().keySet())
			System.out.println("| " + s + " ||| " + this.getProperties().get(s));
		System.out.println("<x");
		System.out.println("XX>-----------------------<XX\n\n\n");
	}

	/**
	 * 
	 * @return
	 */
	private String getFacilityAge() {
		SimpleConditionLiteral cs = null;
		CompoundNode cn = this.getRootNode();
		for (CompoundNode c1 : cn.getChildNodes()) {
			if (c1.getResourceUri().contains("BaulicheKennzahlen")) {
				for (CompoundNode c2 : c1.getChildNodes()) {
					if (c2.getResourceUri().contains("Alter")) {
						cs = (SimpleConditionLiteral) c2;
						return cs.getLiteral();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	private String getGFZ() {
		SimpleConditionLiteral cs = null;
		CompoundNode cn = this.getRootNode();
		for (CompoundNode c1 : cn.getChildNodes()) {
			if (c1.getResourceUri().contains("BaulicheKennzahlen")) {
				for (CompoundNode c2 : c1.getChildNodes()) {
					if (c2.getResourceUri().contains("Grundflaechenzahl")) {
						cs = (SimpleConditionLiteral) c2;
						return cs.getLiteral();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	private String countFloors() {
		String result = null;
		int anz = 0;
		CompoundNode cn = this.getRootNode();
		for (CompoundNode c : cn.getChildNodes()) {
			if (c.getResourceUri().contains("Etage"))
				anz++;
		}
		if (anz > 0)
			return String.valueOf(anz);
		else
			return result;
	}

	/**
	 * 
	 * @return
	 */
	private String countLivingAreas() {
		String result = null;
		int anz = 0;
		CompoundNode cn = this.getRootNode();
		for (CompoundNode c1 : cn.getChildNodes()) {
			if (c1.getResourceUri().contains("Etage"))
				for (CompoundNode c2 : c1.getChildNodes()) {
					System.out.println("--" + c2.getResourceUri());
					if (c2.getResourceUri().contains("Wohneinheit"))
						anz++;
				}
		}
		System.out.println(anz);
		if (anz > 0)
			return String.valueOf(anz);
		else
			return result;
	}

	/**
	 * 
	 */
	private String countBusinessAreas() {
		String result = null;
		int anz = 0;
		CompoundNode cn = this.getRootNode();
		for (CompoundNode c1 : cn.getChildNodes()) {
			if (c1.getResourceUri().contains("Etage"))
				for (CompoundNode c2 : c1.getChildNodes()) {
					if (c2.getResourceUri().contains("Geschaeftseinheit"))
						anz++;
				}
		}
		if (anz > 0)
			return String.valueOf(anz);
		else
			return result;
	}
}
