package de.jhs.oldenburg.gg.facility;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import de.jhs.oldenburg.gg.owl.compound.CompoundNode;
import de.jhs.oldenburg.gg.owl.compound.SimpleConditionLiteral;
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
public class Facility extends ComparisonObject {

	/**
	 * Facility with the rootNode, that represent the facility itself.
	 * This class implements methods for requesting its properties by SPARQL from a given database or by analyzing its given rootNode.
	 * @param rootNode
	 */
	public Facility(CompoundNode rootNode) {
		super(rootNode);
		this.getProperties().put("ImmobilieAlter", getFacilityAgeByNode());
		this.getProperties().put("GFZ", getGFZByNode());
		this.getProperties().put("GeschossAnzahl", countFloorsByNode());
		this.getProperties().put("WohneinheitenAnzahl", countLivingAreasByNode());
		this.getProperties().put("GeschaeftseinheitenAnzahl", countBusinessAreasByNode());

		System.out.println("XX>--FACILITY PROPERTIES--<XX\nx>");
		for (String s : this.getProperties().keySet())
			System.out.println("| " + s + " ||| " + this.getProperties().get(s));
		System.out.println("<x");
		System.out.println("XX>-----------------------<XX\n\n\n");
	}

	/**
	 * Facility with an given triple store database reference.
	 * @param dataset
	 */
	public Facility(String resourceUri, Dataset dataset) {
		super(dataset);
		this.setResourceUri(resourceUri);
		this.getDataset().begin(ReadWrite.READ);
		try {
			this.getProperties().put("ImmobilieAlter", getFacilityAgeByDataset());
			this.getProperties().put("GFZ", getGFZByDataset());
			this.getProperties().put("GeschossAnzahl", countFloorsByDataset());
			this.getProperties().put("WohneinheitenAnzahl", countLivingAreasByDataset());
			this.getProperties().put("GeschaeftseinheitenAnzahl", countBusinessAreasByDataset());
			this.getProperties().put("Dach",getRoofByDataset());
			this.getProperties().put("Typ",getFacilityTypeByDataset());
			this.getProperties().put("Unterstützung",getFacilityCurrentProgramByDataset());
			this.getProperties().put("Bauantragsdatum",getDateOfBuildingApplicationByDataset());
		} finally {
			this.getDataset().end();
		}

		System.out.println("XX>--FACILITY PROPERTIES--<XX\nx>");
		for (String s : this.getProperties().keySet())
			System.out.println("| " + s + " ||| " + this.getProperties().get(s));
		System.out.println("<x");
		System.out.println("XX>-----------------------<XX\n\n\n");

	}

	/**
	 * Function that creates the Literal as a String from a SPARQL request
	 * 
	 * @param queryString
	 * @return the Result-marked Object
	 */
	private String executeQuery(String queryString) {
		if (this.getDataset().supportsTransactions()) {
			// Query to the Graph model
			String rdf = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
			String rdfs = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
			String foaf = "prefix foaf: <http://xmlns.com/foaf/0.1/> ";
			String owl = "prefix owl: <http://www.w3.org/2002/07/owl#> ";
			String jhs = "prefix jhs: <http://www.jade-hs.de/RDF/Ontology#> ";
			Query q = QueryFactory.create(rdf + rdfs + foaf + owl + jhs + queryString);
			try (QueryExecution qexec = QueryExecutionFactory.create(q, this.getDataset().getDefaultModel())) {
				// get the result set
				ResultSet results = qexec.execSelect();
				while (results.hasNext()) {
					QuerySolution soln = results.nextSolution();
					RDFNode o = soln.get("?RESULT");
					// get each object of the query result
					if (o != null && o.isLiteral()) {
						Literal literal = (Literal) o;
						return literal.getString();
					} else if (o != null && o.isResource()) {
						Resource res = (Resource) o;
						return res.toString();
					} else {
						System.out.println(o.toString());
						return null;
					}
				}
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else
			return null;
	}

	/**
	 * @return the age of the facility
	 */
	private String getFacilityAgeByNode() {
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
	 * 
	 * @return returns the GFZ of the facility
	 */
	private String getGFZByNode() {
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
	 * 
	 * @return Counts the floors of the facility
	 */
	private String countFloorsByNode() {
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
	 * 
	 * @return Counts the Living-Areas of the facility
	 */
	private String countLivingAreasByNode() {
		String result = null;
		int anz = 0;
		CompoundNode cn = this.getRootNode();
		for (CompoundNode c1 : cn.getChildNodes()) {
			if (c1.getResourceUri().contains("Etage"))
				for (CompoundNode c2 : c1.getChildNodes()) {
					if (c2.getResourceUri().contains("Wohneinheit"))
						anz++;
				}
		}
		if (anz > 0)
			return String.valueOf(anz);
		else
			return result;
	}

	/**
	 * @return Counts the Business-Areas of the facility
	 */
	private String countBusinessAreasByNode() {
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

	/**
	 * @return the Age of the Facility
	 */ 
	private String getFacilityAgeByDataset() {
		return executeQuery("SELECT (?o AS ?RESULT) WHERE {<" + this.getResourceUri()
				+ "> <http://www.jade-hs.de/RDF/Ontology#hatBaulicheKennzahlen> ?z. ?z <http://www.jade-hs.de/RDF/Ontology#Alter> ?o.}");
	}

	/**
	 * 
	 * @return 
	 */
	private String getGFZByDataset() {
		return executeQuery(" SELECT (?o AS ?RESULT) WHERE {<" + this.getResourceUri()
				+ "> <http://www.jade-hs.de/RDF/Ontology#hatBaulicheKennzahlen> ?z . ?z <http://www.jade-hs.de/RDF/Ontology#Grundflaechenzahl> ?o.}");
	}

	/**
	 * 
	 * @return
	 */
	private String countFloorsByDataset() {
		return executeQuery("SELECT (COUNT(?o) AS ?RESULT) WHERE {<" + this.getResourceUri()
				+ "> <http://www.jade-hs.de/RDF/Ontology#hatEtage> ?o .}");
	}

	/**
	 * 
	 * @return
	 */
	private String countLivingAreasByDataset() {
		return executeQuery(" SELECT (COUNT(?ge) AS ?RESULT) WHERE {<" + this.getResourceUri()
				+ "> <http://www.jade-hs.de/RDF/Ontology#hatEtage> ?etage. "
				+ "?etage <http://www.jade-hs.de/RDF/Ontology#hatGebaeudeeinheit> ?ge. "
				+ "?ge a <http://www.jade-hs.de/RDF/Ontology#Wohneinheit> .}");
	}

	/**
	 * 
	 */
	private String countBusinessAreasByDataset() {
		return executeQuery(" SELECT (COUNT(?ge) AS ?RESULT) WHERE {<" + this.getResourceUri()
				+ "> <http://www.jade-hs.de/RDF/Ontology#hatEtage> ?etage. "
				+ "?etage <http://www.jade-hs.de/RDF/Ontology#hatGebaeudeeinheit> ?ge. "
				+ "?ge a <http://www.jade-hs.de/RDF/Ontology#Geschaeftseinheit> .}");
	}
	
	/**
	 * 
	 * @return
	 */
	private String getRoofByDataset() {
		return executeQuery(" SELECT (?o AS ?RESULT) WHERE {<" + this.getResourceUri()
				+ "> <http://www.jade-hs.de/RDF/Ontology#hatDach> ?o.}");
	}
	
	/**
	 * 
	 * @return
	 */
	private String getFacilityTypeByDataset() {
		return executeQuery(" SELECT (?o AS ?RESULT) WHERE {<" + this.getResourceUri()
				+ "> <http://www.jade-hs.de/RDF/Ontology#istImmobiliensart> ?o. }");
	}
	
	/**
	 * 
	 * @return
	 */
	private String getFacilityCurrentProgramByDataset() {
		return executeQuery(" SELECT (?o AS ?RESULT) WHERE {<" + this.getResourceUri()
				+ "> <http://www.jade-hs.de/RDF/Ontology#wirdUnterstuetztVonFoerderprogramm> ?z. "
				+ " ?z <http://www.jade-hs.de/RDF/Ontology#Name> ?o.}");
	}
	
	/**
	 * 
	 * @return
	 */
	private String getDateOfBuildingApplicationByDataset() {
		return executeQuery(" SELECT (?o AS ?RESULT) WHERE {<" + this.getResourceUri()
				+ "> <http://www.jade-hs.de/RDF/Ontology#hatBaulicheKennzahlen> ?z ."
				+ " ?z <http://www.jade-hs.de/RDF/Ontology#BauantragAlter> ?o.}");
	}
 
}

