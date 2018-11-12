package sceneModel.sceneGraph;

import sceneModel.SceneElement;
import abstractModel.Matrix4f;

/**
 * Class representing an element of the SceneGraph.
 * 
 * @author Geert Van Campenhout
 */
public abstract class SceneGraphElement implements SceneElement {
	
	private String name;
	private Matrix4f localTransformMatrix;
	protected SceneGraphElement root;

	/**
	 * Makes a new SceneGraphElement with the given name, localTransformMatrix and root element.
	 * 
	 * @param name	The name of the SceneGraphElement.
	 * @param localTransformMatrix	The localTransformMatrix of the SceneGraphElement.
	 * @param root	The root element of the SceneGraphElement.
	 */
	public SceneGraphElement(String name, Matrix4f localTransformMatrix, SceneGraphElement root){
		this.name = name;
		this.localTransformMatrix = localTransformMatrix;
		this.root = root;
	}
	
	/* (non-Javadoc)
	 * @see sceneModel.SceneElement#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the local transformMatrix of the SceneGraphElement.
	 * 
	 * @return	Matrix4f
	 * 		The local transformMatrix
	 */
	public Matrix4f getLocalTransformMatrix() {
		return this.localTransformMatrix;
	}
	
	/**
	 * Returns the total transformMatrix of the SceneGraphElement.
	 * Taking into consideration all the SceneGraphElements above this SceneGraphElement.
	 * 
	 * @return	Matrix4f
	 * 		The total transformMatrix
	 */
	public Matrix4f getTotalTransformMatrix() {
		Matrix4f totalTransformMatrix = new Matrix4f(localTransformMatrix);
		if(root != null){
			Matrix4f rootMatrix = root.getTotalTransformMatrix();
			totalTransformMatrix.multiplyRight(localTransformMatrix, rootMatrix);
		}
		return totalTransformMatrix;
	}
}
