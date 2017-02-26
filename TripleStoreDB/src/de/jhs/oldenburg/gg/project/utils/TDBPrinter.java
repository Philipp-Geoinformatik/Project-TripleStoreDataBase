package de.jhs.oldenburg.gg.project.utils;

import java.util.Iterator;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.core.Quad;

/**
 * 
 * 
 * 
 * @author Fred Bohlmann
 * @author Stefan Buescher
 * @author Philipp Grashorn
 *
 */
public class TDBPrinter {

	/**
	 * 
	 * @return
	 */
	public static String printModelContent(Dataset dataset) {
		System.out.println("==========================================================");
		System.out.println("======================ContentModel========================");
		System.out.println("==========================================================");
		try {
			dataset.begin(ReadWrite.READ);
			Model m = dataset.getDefaultModel();
			StmtIterator iter = m.listStatements();
			// print out the predicate, subject and object of each statement
			while (iter.hasNext()) {
				Statement stmt = iter.nextStatement(); // get next statement
				Resource subject = stmt.getSubject(); // get the subject
				Property predicate = stmt.getPredicate(); // get the predicate
				RDFNode object = stmt.getObject(); // get the object
				//
				System.out.print("Subject( " + subject.toString() + " ) -->");
				System.out.print(" predicate( " + predicate.toString() + " ) -->");
				if (object instanceof Resource) {
					System.out.print(" object( " + object.toString() + " )");
				} else {
					// object is a literal
					System.out.print(" ObjectLiteral( " + object.toString() + " )");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataset.end();

		}
		return null;
	}

	/**
	 * 
	 */
	public static String printSELECT(String query, Dataset dataset) {
		dataset.begin(ReadWrite.READ);

		try (QueryExecution qExec = QueryExecutionFactory.create(query, dataset)) {
			ResultSet rs = qExec.execSelect();
			ResultSetFormatter.out(rs);
		} finally {
			dataset.end();
		}
		return null;
	}

	/**
	 * 
	 */
	public static void printCONSTRUCT(Dataset dataset) {
		String queryString = "CONSTRUCT WHERE { <http://www.jade-hs.de/RDF/Ontology/Wohneinheit#Wohneinheit_1> ?p ?o }";
		String queryString2 = "SELECT * WHERE { ?s ?p ?o}";
		dataset.begin(ReadWrite.READ);
		try {
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, dataset.getDefaultModel());
			Model resultModel = qexec.execConstruct();
			qexec.close();
			try (QueryExecution qexec2 = QueryExecutionFactory.create(queryString2, resultModel)) {
				ResultSet results = qexec2.execSelect();
				ResultSetFormatter.out(results);
			}
		} finally {
			dataset.end();
		}
	}
	
	
	/**
	 * 
	 */
	public static void printDESCRIBE(Dataset dataset) {
		String queryString = "DESCRIBE ?Wohnhaus WHERE {?Wohnhaus <http://www.jade-hs.de/RDF/Ontology#hatGebaeudeeinheit> <http://www.jade-hs.de/RDF/Ontology/Wohneinheit#Wohneinheit_1> }";
		String queryString2 = "SELECT * WHERE { ?s ?p ?o}";
		dataset.begin(ReadWrite.READ);
		try {
			Query query = QueryFactory.create(queryString) ;
			QueryExecution qexec = QueryExecutionFactory.create(query, dataset.getDefaultModel()) ;
			Model resultModel = qexec.execDescribe() ;
			qexec.close() ;
			try (QueryExecution qexec2 = QueryExecutionFactory.create(queryString2, resultModel)) {
				ResultSet results = qexec2.execSelect();
				ResultSetFormatter.out(results);
			}
		} finally {
			dataset.end();
		}
	}

	
	
	
	
	/**
	 * 
	 * @param resultSet
	 */
	public static void printResultset(ResultSet resultSet) {
		if (resultSet != null)
			ResultSetFormatter.out(resultSet);
		else
			System.err.println("Resultset is <NULL>");
	}

}
