package de.jhs.oldenburg.gg.owl.compound;

import java.util.ArrayList;

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

	/**
	 * 
	 * @param parentNode
	 */
	public CompoundNode(CompoundNode parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * 
	 * @param resourceUri
	 */
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
		String str = "x> RESOURCE: " + resourceUri + "\n";
		if (parentNode != null)
			str += "PARENT: " + parentNode.getResourceUri()+"\n";
		if (childNodes != null && !childNodes.isEmpty()) {

			for (CompoundNode compoundNode : childNodes) {
				str += "|  CHILD: <" + compoundNode.getResourceUri() + "> \n";
			}
		}
		str += "<x";
		System.out.println();
		return str;
	}
}
