package de.tubs.variantsync.core.view.resourcechanges;

import java.util.List;

import de.tubs.variantsync.core.patch.interfaces.IDelta;
import de.tubs.variantsync.core.patch.interfaces.IPatch;
import de.tubs.variantsync.core.utilities.Tree;
import de.tubs.variantsync.core.utilities.TreeNode;

/**
 * Wraps the patches to a tree format
 * 
 * @author Christopher Sontag
 */
public class ResourcesTree {
	
	public static Tree construct(List<IPatch<?>> patches) {
		Tree tree = new Tree();
		TreeNode root = new TreeNode();
		tree.setRoot(root);
		
		for (IPatch<?> patch : patches) {
			for (IDelta<?> delta : patch.getDeltas()) {
				TreeNode featureNode = tree.find(delta.getFeature());
				if (featureNode == null) {
					featureNode = new TreeNode(delta.getFeature());
					root.addChild(featureNode);
				}
				TreeNode fileNode = tree.find(featureNode, delta.getResource().getProjectRelativePath());
				if (fileNode == null) {
					fileNode = new TreeNode(delta.getResource().getProjectRelativePath());
					featureNode.addChild(fileNode);
				}
				TreeNode deltaNode = tree.find(fileNode, delta);
				if (deltaNode == null) {
					deltaNode = new TreeNode(delta);
					fileNode.addChild(deltaNode);
				}
			}
		}
		
		return tree;
	}

}
