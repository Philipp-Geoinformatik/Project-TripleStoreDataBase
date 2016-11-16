package de.jhs.oldenburg.gg.rdf.manager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.VCARD;

/**
 * 
 * @author SSA_HErnst
 *
 */
public class MyRDFBuilder {

	/**
	 * 
	 */
	public Model createRDF() {
		// some definitions
		String personURI = "http://somewhere/JohnSmith";
		String givenName = "John";
		String familyName = "Smith";
		String fullName = givenName + " " + familyName;

		// create an empty Model
		Model model = ModelFactory.createDefaultModel();

		// create the resource
		// and add the properties cascading style
		Resource johnSmith = model.createResource(personURI).addProperty(VCARD.FN, fullName).addProperty(VCARD.N,
				model.createResource().addProperty(VCARD.Given, givenName).addProperty(VCARD.Family, familyName));

		// list the statements in the Model
		StmtIterator iter = model.listStatements();

		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement(); // get next statement
			Resource subject = stmt.getSubject(); // get the subject
			Property predicate = stmt.getPredicate(); // get the predicate
			RDFNode object = stmt.getObject(); // get the object

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

		return model;

	}

	/**
	 * 
	 * @param model
	 * @param path
	 * @param filename
	 * @param encoding
	 */
	public void writeRDFModel(Model model, String path, String filename, String encoding) {
		System.out.println("Writing file to: " + path + "/" + filename);
		//
		try {
			model.write(new OutputStreamWriter(new FileOutputStream(path + "" + filename), encoding));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		MyRDFBuilder my = new MyRDFBuilder();

		Model myRDFModel = my.createRDF();
		// writing file to specific directory
		my.writeRDFModel(myRDFModel, "", "my_rdf.xml", "utf-8");

	}
}
