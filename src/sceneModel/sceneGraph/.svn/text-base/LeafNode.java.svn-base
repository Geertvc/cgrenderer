package sceneModel.sceneGraph;

import sceneModel.Surface;
import abstractModel.Matrix4f;

/**
 * A SceneGraphElement containing one object of the scene. 
 * 
 * @author Geert Van Campenhout
 * @version 1.1
 */
public class LeafNode extends SceneGraphElement{
	
	private Surface surface;
	private String materialName;
	private String textureName;

	/**
	 * Makes a new LeafNode with the given name, localTransformMatrix and root of the leaf.
	 * 
	 * @param name	The name of the LeafNode.
	 * @param materialName	The name of the material this LeafNode is made of.
	 * @param localTransformMatrix	The localTransformMatrix of the LeafNode.
	 * @param root	The root SceneGraphElement of the LeafNode.
	 * @param surface	The surface stored in the LeafNode.
	 */
	public LeafNode(String name, String materialName, Matrix4f localTransformMatrix, SceneGraphElement root, Surface surface){
		super(name, localTransformMatrix, root);
		this.materialName = materialName;
		this.textureName = null;
		this.surface = surface;
	}
	
	/**
	 * Makes a new LeafNode with the given name, material name, texture name, localTransformMatrix, root of the leaf and surface of the leafNode.
	 * 
	 * @param name	The name of the LeafNode.
	 * @param materialName	The name of the material this LeafNode is made of.
	 * @param textureName The name of the texture of the surface in this leafNode.
	 * @param localTransformMatrix	The localTransformMatrix of the LeafNode.
	 * @param root	The root SceneGraphElement of the LeafNode.
	 * @param surface	The surface stored in the LeafNode.
	 */
	public LeafNode(String name, String materialName, String textureName, Matrix4f localTransformMatrix, SceneGraphElement root, Surface surface){
		super(name, localTransformMatrix, root);
		this.materialName = materialName;
		this.textureName = textureName;
		this.surface = surface;
	}
	
	/**
	 * @return	The surface stored in this LeafNode.
	 */
	public Surface getSurface(){
		return this.surface;
	}
	
	/**
	 * @return	The material name of this LeafNode.
	 */
	public String getMaterialName(){
		return this.materialName;
	}
	
	/**
	 * @return	The texture name of this LeafNode.
	 */
	public String getTextureName(){
		return this.textureName;
	}
	
	/**
	 * @return	The root SceneGraphElement of this LeafNode.
	 */
	public SceneGraphElement getRoot(){
		return root;
	}

}
