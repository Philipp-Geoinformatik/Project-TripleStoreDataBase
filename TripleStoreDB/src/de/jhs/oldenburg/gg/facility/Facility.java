package de.jhs.oldenburg.gg.facility;

import java.util.ArrayList;
import java.util.HashMap;

import de.jhs.oldenburg.gg.owl.compound.CompoundNode;
import de.jhs.oldenburg.gg.owl.compound.SimpleConditionLiteral;
import de.jhs.oldenburg.gg.owl.parser.ComparisonObject;

public class Facility extends ComparisonObject {
	
	//--Instance-Test--
	//Hausalter
	//Grundflächenzahl
	//Geschosszahl
	//Dachtyp
	//Dachalter
	//Bauweise
	//Wohneinheitszahl
	//Anzahl Zimmer
	
	public Facility(CompoundNode rootNode) {
		super(rootNode);
		this.getProperties().put("ImmobilieAlter", getFacilityAge());
		this.getProperties().put("GFZ", getGFZ());
		this.getProperties().put("GeschossAnzahl", countFloors());
		this.getProperties().put("WohneinheitenAnzahl", countFacilityPartsForLiving());
		this.getProperties().put("GeschaeftseinheitenAnzahl", countFacilityPartsForSelling());
		
		System.out.println("--HashMap(Facility)--");
		for (String s :this.getProperties().keySet())
			System.out.println(s+" ||| "+this.getProperties().get(s));
	}
	
	private String getFacilityAge() {
		SimpleConditionLiteral cs=null;
		CompoundNode cn = this.getRootNode();
		for(CompoundNode c1 :cn.getChildNodes()) {
			if ( c1.getResourceUri().contains("BaulicheKennzahlen")) {
				for(CompoundNode c2 : c1.getChildNodes()) {
					if ( c2.getResourceUri().contains("Alter")){
						cs = (SimpleConditionLiteral) c2;
						return cs.getLiteral();
					}
				}
			}				
		}
		return null;
	}
	
	private String getGFZ() {
		SimpleConditionLiteral cs=null;
		CompoundNode cn = this.getRootNode();
		for(CompoundNode c1 :cn.getChildNodes()) {
			if ( c1.getResourceUri().contains("BaulicheKennzahlen")) {
				for(CompoundNode c2 : c1.getChildNodes()) {
					if ( c2.getResourceUri().contains("Grundflaechenzahl")){
						cs = (SimpleConditionLiteral) c2;
						return cs.getLiteral();
					}
				}
			}				
		}
		return null;
	}

	private String countFloors() {
		String result =null;
		int anz=0;
		CompoundNode cn = this.getRootNode();
		for(CompoundNode c :cn.getChildNodes()) {
			if ( c.getResourceUri().contains("Etage"))
				anz++;
		}		
		if (anz>0)
			return String.valueOf(anz);
		else
			return result;
	}
	
	private String countFacilityPartsForLiving() {
		String result =null;
		int anz=0;
		CompoundNode cn = this.getRootNode();
		for(CompoundNode c1 :cn.getChildNodes()) {
			if ( c1.getResourceUri().contains("Etage"))
				for(CompoundNode c2 :c1.getChildNodes()) {
					System.out.println("--"+c2.getResourceUri());
					if ( c2.getResourceUri().contains("Wohneinheit"))
						anz++;
				}
		}
		System.out.println(anz);
		if (anz>0)
			return String.valueOf(anz);
		else
			return result;
	}
		private String countFacilityPartsForSelling() {
			String result =null;
			int anz=0;
			CompoundNode cn = this.getRootNode();
			for(CompoundNode c1 :cn.getChildNodes()) {
				if ( c1.getResourceUri().contains("Etage"))
					for(CompoundNode c2 :c1.getChildNodes()) {
						if ( c2.getResourceUri().contains("Geschaefseinheit"))
							anz++;
					}
			}		
			if (anz>0)
				return String.valueOf(anz);
			else
				return result;
		}
}
