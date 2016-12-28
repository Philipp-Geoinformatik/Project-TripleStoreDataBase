package de.jhs.oldenburg.gg.tdb.utils;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

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
	public static String printSPARQLReq(String query, Dataset dataset) {
		dataset.begin(ReadWrite.READ);

		try (QueryExecution qExec = QueryExecutionFactory.create(query, dataset)) {
			ResultSet rs = qExec.execSelect();
			ResultSetFormatter.out(rs);
		} finally {
			dataset.end();
		}
		return null;
	}

	public void printResultset(ResultSet results) {

	}

}
