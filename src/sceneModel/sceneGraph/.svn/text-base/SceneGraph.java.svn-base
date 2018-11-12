package sceneModel.sceneGraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The SceneGraph containing all the objects in the scene.
 * 
 * @author Geert Van Campenhout
 * @version 1.1
 */
public class SceneGraph {
	
	Map<String, GroupNode> groups;
	Map<String, LeafNode> leafs;
	
	/**
	 * Creates a new SceneGraph.
	 */
	public SceneGraph(){
		this.groups = new HashMap<String, GroupNode>();
		this.leafs = new HashMap<String, LeafNode>();
	}
	
	/**
	 * Adds a GroupNode to the SceneGraph.
	 * 
	 * @param name	The name of the GroupNode.
	 * @param group	The GroupNode to add.
	 */
	public void addGroupNode(String name, GroupNode group){
		this.groups.put(name, group);
	}
	
	/**
	 * Adds a LeafNode to the SceneGraph.
	 * 
	 * @param name	The name of the LeafNode.
	 * @param leaf	The LeafNode to add.
	 */
	public void addLeafNode(String name, LeafNode leaf){
		this.leafs.put(name, leaf);
	}
	
	/**
	 * Removes a LeafNode from the SceneGraph.
	 * 
	 * @param name	The name of the LeafNode.
	 * @param leaf	The LeafNode to remove.
	 */
	public void removeLeafNode(String name){
		this.leafs.remove(name);
	}
	
	/**
	 * @return	All the leafNodes of the SceneGraph.	
	 */
	public Collection<LeafNode> getLeafs(){
		return this.leafs.values();
	}
	
	/**
	 * Returns the leafNode with the given name.
	 * 
	 * @param leafName	The name of the leafNode to retrieve.
	 * @return	LeafNode
	 * 		The LeafNode with the given name.
	 */
	public LeafNode getLeafNode(String leafName){
		return this.leafs.get(leafName);
	}
}
