package de.jhs.oldenburg.gg.project.pattern.compound;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.jhs.oldenburg.gg.project.pattern.comparison.ComparisonObject;

/**
 * 
 * 
 * 
 * @author Fred Bohlmann
 * @author Stefan Buescher
 * @author Philipp Grashorn
 *
 */
public class MaxOperator extends CompoundNode {

	/**
	 * 
	 * @param resourceUri
	 */
	public MaxOperator(String resourceUri) {
		super(resourceUri);
		// TODO Auto-generated constructor stubs
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(CompoundNode cn) {
		String propName = getChildNodes().get(0).getResourceUri();
		propName = propName.substring(propName.lastIndexOf("#"));
		Double lit = Double.parseDouble(((SimpleConditionLiteral) getChildNodes().get(0).getChildNodes().get(0)).getLiteral());
		Double v = null;
		if (cn instanceof ExistanceCondition)
			v = Double.parseDouble(((SimpleConditionLiteral) cn.getChildNodes().get(0)).getLiteral());

		if (lit >= v)
			return true;
		else
			return false;
	}

	/**
	 * 
	 */
	@Override
	public boolean resolve(ComparisonObject cpv) {
		String propName = getChildNodes().get(0).getResourceUri();
		propName = propName.substring(propName.lastIndexOf("#")+1);
		System.out.println("Value: "+ ((SimpleConditionLiteral) getChildNodes().get(0).getChildNodes().get(0)).getLiteral());
		String s = ""+ ((SimpleConditionLiteral) getChildNodes().get(0).getChildNodes().get(0)).getLiteral();
		Double doub;
		Date date;
		try{
			doub=Double.parseDouble(s);
			Double v = Double.parseDouble(cpv.getProperties().get(propName));
			System.out.println("CompareWith: "+v);
			if (doub >= v){
				System.out.println("MAX-Result:true");
				return true;
			}else{
				System.out.println("MAX-Result:false");
				return false;
			}
		}catch(NumberFormatException ex1) {
			try{SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
				date =sdf.parse(s);
				Date v=sdf.parse(cpv.getProperties().get(propName));
				System.out.println("CompareWith: "+cpv.getProperties().get(propName));
				if (date.after(v)){
					System.out.println("MAX-Result:true");
					return true;
				}else{
					System.out.println("MAX-Result:false");
					return false;
				}
			} catch(ParseException ex2) {
				System.err.println(ex2.getLocalizedMessage());
			}
		}
		return false;
//		Double lit = Double.parseDouble(((SimpleConditionLiteral) getChildNodes().get(0).getChildNodes().get(0)).getLiteral());
//		Double v = Double.parseDouble(cpv.getProperties().get(propName));
//
//		if (lit <= v)
//			return true;
//		else
//			return false;
	}
}
