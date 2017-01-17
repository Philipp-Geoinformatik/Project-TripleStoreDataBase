package de.jhs.oldenburg.gg.owl.compound;

import java.util.ArrayList;

import org.apache.jena.rdf.model.Literal;

import de.jhs.oldenburg.gg.owl.parser.ComparisonObject;

/**
 * 
 * @author Philipp Grashorn <br>
 *         From the master project of the Jade University of applied science:
 *         TripleStoreDB <br>
 *         Creation date: 06.01.2017
 *
 */
public class CompoundNode {

	private CompoundNode parentNode;
	private ArrayList<CompoundNode> childNodes;
	private String resourceUri;

	public CompoundNode(CompoundNode parentNode) {
		this.parentNode = parentNode;
	}

	public CompoundNode(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	/**
	 * 
	 * @return
	 */
	public CompoundNode getParentNode() {
		return parentNode;
	}

	/**
	 * 
	 * @param parentNode
	 */
	public void setParentNode(CompoundNode parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<CompoundNode> getChildNodes() {
		return childNodes;
	}

	/**
	 * 
	 * @param childNodes
	 */
	public void setChildNodes(ArrayList<CompoundNode> childNodes) {
		this.childNodes = childNodes;
	}

	/**
	 * 
	 * @return
	 */
	public String getResourceUri() {
		return resourceUri;
	}

	/**
	 * 
	 * @param resourceUri
	 */
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		String str = "x> \n| RESOURCE <" + this.getClass().getSimpleName() + ">: " + resourceUri + "\n";
		if (parentNode != null)
			str += "| PARENT: " + parentNode.getResourceUri() + "\n";
		if (childNodes != null && !childNodes.isEmpty()) {

			for (CompoundNode compoundNode : childNodes) {
				str += "| CHILD: <" + compoundNode.getResourceUri() + "> \n";
			}
		}
		str += "<x";
		System.out.println();
		return str;
	}

	/**
	 * 
	 * @param cn
	 * @return
	 */
	public boolean resolve(CompoundNode cn) {
		System.out.println("<<<TRYING TO RESOLVE>>>");
		System.out.println(this);
		if (childNodes.size() == 1)
			return childNodes.get(0).resolve(cn);
		else {
			System.err.println(this + "\n\n Contains more than one child! Node: <" + resourceUri
					+ "> doesn't follow the restrictions of a compound");
			return false;
		}
	}

	/**
	 * 
	 * @param cpv
	 * @return
	 */
	public boolean resolve(ComparisonObject cpv) {
		System.out.println("<<<TRYING TO RESOLVE>>>");
		System.out.println(this);
		if (childNodes.size() == 1)
			return childNodes.get(0).resolve(cpv);
		else {
			System.err.println(this + "\n\n Contains more than one child! Node: <" + resourceUri
					+ "> doesn't follow the restrictions of a compound");
			return false;
		}
	}

	/**
	 * Searches in the given {@link CompoundNode} for the given literal.
	 * 
	 * @param literal
	 * @param node
	 * @return
	 */
	public boolean compoundContains(String literal, CompoundNode node) {
		System.out.println("BEGINNING search in: " + node.getResourceUri());
		System.out.println("FOR RESOURCE: " + literal);
		SimpleConditionLiteral cs = null;
		//
		ArrayList<CompoundNode> nodes = new ArrayList<CompoundNode>();
		nodes.add(node);
		//
		int i = 0;
		//
		while (true) {
			if (i == nodes.size())
				return false;
			CompoundNode n = nodes.get(i++);
			nodes.addAll(n.getChildNodes());
			if (n instanceof SimpleConditionLiteral) {
				cs = (SimpleConditionLiteral) n;
				System.out.println("Looking at Literal: " + cs.getLiteral());
				if (cs.getLiteral().contains(literal)) {
					return true;
				}
			}
		}
	}
}
