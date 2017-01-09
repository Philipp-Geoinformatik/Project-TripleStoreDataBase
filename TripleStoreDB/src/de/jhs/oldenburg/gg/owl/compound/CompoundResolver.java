package de.jhs.oldenburg.gg.owl.compound;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;

import de.jhs.oldenburg.gg.rdf.vocabulary.Friends;

/**
 * 
 * @author Philipp Grashorn <br>
 *         From the master project of the Jade University of applied science:
 *         TripleStoreDB <br>
 *         Creation date: 06.01.2017
 *
 */
public class CompoundResolver {

	private String baseDir = new File(".").getAbsoluteFile().getParentFile().toPath().toUri().toString();

	/**
	 * 
	 */
	private Dataset dataset;

	/**
	 * 
	 * @param dataset
	 */
	public CompoundResolver(Dataset dataset) {
		this.dataset = dataset;
	}

	/**
	 * 
	 * @param parentNode
	 * @param predicate
	 * @return ArrayList<String> with parent node and all child elements.
	 */
	@Deprecated
	public ArrayList<String> getResourcesOfCompound(String parentNode, String predicate) {
		// Boolean for breaking the loop
		int i = 0;
		// List for values
		ArrayList<String> nodes = new ArrayList<String>(Arrays.asList(parentNode));
		// List of predefined name spaces
		while (true) {
			// function for recursion
			if (i == nodes.size())// break condition
				return nodes;
			// do the logical things for getting the child elements
			nodes.addAll(this.getChildNodes(nodes.get(i++), predicate));
		}
	}

	/**
	 * 
	 * @param parentNode
	 * @param predicate
	 * @return
	 */
	private ArrayList<String> getChildNodes(String parentNode, String predicate) {
		ArrayList<String> nodes = new ArrayList<String>();
		dataset.begin(ReadWrite.READ);
		// Query to the Graph model
		String rdf = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
		String rdfs = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
		String foaf = "prefix foaf: <http://xmlns.com/foaf/0.1/> ";
		String owl = "prefix owl: <http://www.w3.org/2002/07/owl#> ";
		String jhs = "prefix jhs: <http://www.jade-hs.de/RDF/Ontology#> ";
		//
		Query query = QueryFactory.create(rdf + rdfs + foaf + owl + jhs + "SELECT DISTINCT  ?o  " + "WHERE { <"
				+ parentNode + "> <" + predicate + "> ?o . }");

		try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset.getDefaultModel())) {
			// get the result set
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				RDFNode o = soln.get("?o");
				// get each object of the query result
				if (o != null)
					if (o.isResource()) {
						Resource r2 = (Resource) o;
						nodes.add(r2.toString());
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataset.end();
		}
		return nodes;
	}

	/**
	 * 
	 * @param parentNode
	 * @param predicate
	 * @return
	 */
	private ArrayList<CompoundNode> getCompoundNodesBySinglePredicate(CompoundNode parentNode, String predicate) {
		ArrayList<CompoundNode> childNodes = new ArrayList<>();
		dataset.begin(ReadWrite.READ);
		// Query to the Graph model
		String rdf = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
		String rdfs = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
		String foaf = "prefix foaf: <http://xmlns.com/foaf/0.1/> ";
		String owl = "prefix owl: <http://www.w3.org/2002/07/owl#> ";
		String jhs = "prefix jhs: <http://www.jade-hs.de/RDF/Ontology#> ";
		//
		Query query = QueryFactory.create(rdf + rdfs + foaf + owl + jhs + "SELECT DISTINCT  ?o  " + "WHERE { <"
				+ parentNode.getResourceUri() + "> <" + predicate + "> ?o . }");

		try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset.getDefaultModel())) {
			// get the result set
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				RDFNode o = soln.get("?o");
				// get each object of the query result
				if (o != null)
					if (o.isResource()) {
						Resource r2 = (Resource) o;
						// System.out.println("SIMPLE OUT :"+r2);
						CompoundNode n = new CompoundNode(r2.toString());
						n.setParentNode(parentNode);
						childNodes.add(n);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataset.end();
		}
		return childNodes;
	}

	/**
	 * 
	 * @param parentNode
	 * @param predicate
	 * @return
	 */
	private ArrayList<CompoundNode> getCompoundNodesByNameSpace(CompoundNode parentNode, String nameSpace) {
		ArrayList<CompoundNode> childNodes = new ArrayList<>();
		ArrayList<String> predicates = getAllPredicatesOfNode(parentNode.getResourceUri(), nameSpace);

		// Query to the Graph model
		String rdf = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
		String rdfs = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
		String foaf = "prefix foaf: <http://xmlns.com/foaf/0.1/> ";
		String owl = "prefix owl: <http://www.w3.org/2002/07/owl#> ";
		String jhs = "prefix jhs: <http://www.jade-hs.de/RDF/Ontology#> ";
		//
		try {
			dataset.begin(ReadWrite.READ);
			for (int j = 0; j < predicates.size(); j++) {
				Query query = QueryFactory.create(rdf + rdfs + foaf + owl + jhs + "SELECT DISTINCT  ?o  " + "WHERE { <"
						+ parentNode.getResourceUri() + "> <" + predicates.get(j) + "> ?o . }");
				QueryExecution qexec = QueryExecutionFactory.create(query, dataset.getDefaultModel());
				// get the result set
				ResultSet results = qexec.execSelect();
				while (results.hasNext()) {
					QuerySolution soln = results.nextSolution();
					RDFNode o = soln.get("?o");
					// get each object of the query result
					if (o != null)
						if (o.isResource()) {
							Resource r2 = (Resource) o;
							// System.out.println("SIMPLE OUT :"+r2);
							CompoundNode n = new CompoundNode(r2.toString());
							n.setParentNode(parentNode);
							childNodes.add(n);
						}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataset.end();
		}
		return childNodes;
	}

	/**
	 * 
	 */
	@Deprecated
	public void resolveCompoundByVocabularyLib() {
		try {
			dataset.begin(ReadWrite.READ);
			Model model = dataset.getDefaultModel();
			ResIterator iter = model.listSubjectsWithProperty(Friends.hatFreund);
			// StmtIterator it = model.listStatements(new
			// SimpleSelector(Friends.Mann, null, (Resource) null));
			while (iter.hasNext()) {
				Resource r = iter.nextResource();
				System.out.println(r);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dataset.end();
		}
	}

	/**
	 * 
	 * @return
	 */
	@Deprecated
	public ArrayList<CompoundNode> resolveCompoundByNodeStructure(String parentNode, String predicate) {
		// Boolean for breaking the loop
		int i = 0;
		// List for values
		ArrayList<CompoundNode> nodes = new ArrayList<CompoundNode>(Arrays.asList(new CompoundNode(parentNode)));
		// List of predefined name spaces
		while (true) {
			// function for recursion
			if (i == nodes.size())// break condition
				return nodes;
			// do the logical things for getting the child elements
			ArrayList<CompoundNode> n = this.getCompoundNodesBySinglePredicate(nodes.get(i), predicate);
			// System.out.println(n);
			nodes.get(i).setChildNodes(n);
			nodes.addAll(n);
			i++;
		}
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<CompoundNode> resolveCompound(String parentNode, String nameSpace) {
		// Boolean for breaking the loop
		int i = 0;
		// List for values
		ArrayList<CompoundNode> nodes = new ArrayList<CompoundNode>(Arrays.asList(new CompoundNode(parentNode)));
		// List of predefined name spaces
		while (true) {
			// function for recursion
			if (i == nodes.size())// break condition
				return nodes;
			// do the logical things for getting the child elements
			ArrayList<CompoundNode> n = this.getCompoundNodesByNameSpace(nodes.get(i), nameSpace);
			// System.out.println(n);
			nodes.get(i).setChildNodes(n);
			nodes.addAll(n);
			i++;
		}
	}

	/**
	 * 
	 * @param name
	 *            of the resource
	 * @return an ArrayList<String> with all associated predicates of the given
	 *         resource.
	 */
	private ArrayList<String> getAllPredicatesOfNode(String name, String nameSpace) {
		ArrayList<String> predicates = new ArrayList<>();
		dataset.begin(ReadWrite.READ);
		// Query to the Graph model
		String query = "SELECT  DISTINCT ?p WHERE {<" + name
				+ "> ?p ?o . filter strstarts(str (?p), 'http://www.jade-hs.de/RDF/Ontology')}";
		//
		try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset.getDefaultModel())) {
			// get the result set
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				RDFNode o = soln.get("?p");
				// get each object of the query result
				if (o != null)
					if (o.isResource()) {
						Resource r2 = (Resource) o;
						// System.out.println("SIMPLE OUT :"+r2);
						predicates.add(r2.getURI());
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataset.end();
		}
		return predicates;
	}
}
