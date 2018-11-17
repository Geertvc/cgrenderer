package sceneModel.sceneGraph;

import sceneModel.Surface;
import abstractModel.Matrix4f;
import abstractModel.Vector3f;

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
	
//	@Override
//	public Matrix4f getTotalTransformMatrix() {
//		Matrix4f totalTransformMatrix = new Matrix4f();
//		if(root != null){
//			Matrix4f rootMatrix = root.getTotalTransformMatrix();
//			totalTransformMatrix.multiplyRight(super.getLocalTransformMatrix(), rootMatrix);
//		}
//		Matrix4f newLocal = new Matrix4f();
//		newLocal.multiplyRight(totalTransformMatrix, rotationMatrix);
//		return newLocal;
//	}
	
//	private Matrix4f rotationMatrix = new Matrix4f(1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1);

//	public void setRotationMatrix(float angle){
//    	Vector3f axis = new Vector3f(0,1,0);
//    	System.err.println("axis: " + axis);
//    	System.err.println("angle: " + angle);
//    	//Make orthonormal base to make rotationMatrix with.
//    	Vector3f w = new Vector3f(axis);
//    	w.normalize();
//    	Vector3f t = getNonCollinearVector(w);
//    	Vector3f u = new Vector3f();
//    	u.cross(t, w);
//    	u.normalize();
//    	Vector3f v = new Vector3f();
//    	v.cross(w, u);
//    	v.normalize();
//    	System.err.println("v: " + v);
//    	Matrix4f uvw = new Matrix4f(u.x, v.x, w.x, 0, 
//    								u.y, v.y, w.y, 0, 
//    								u.z, v.z, w.z, 0, 
//    								0, 0, 0, 1);
//    	System.err.println("uvw: \n" + uvw);
//    	double angleInRadians = Math.toRadians(angle);
//    	System.err.println("angleInRadians: " + angleInRadians);
//    	Matrix4f rotateAngleAroundZ = new Matrix4f(	(float) Math.cos(angleInRadians), (float)-Math.sin(angleInRadians), 0, 0,
//    												(float) Math.sin(angleInRadians), (float) Math.cos(angleInRadians), 0, 0,
//    												0, 0, 1, 0,
//    												0, 0, 0, 1);
//    	Matrix4f uvwTranspose = uvw.transpose();
//    	//Make rotationMatrix
//    	Matrix4f rotationMatrix = new Matrix4f();
//    	rotationMatrix.multiplyRight(uvw, rotateAngleAroundZ);
//    	rotationMatrix = rotationMatrix.multiplyRight(uvwTranspose);
//    	this.rotationMatrix = rotationMatrix;
//    }
	
	/**
	 * Returns a vector that is not collinear with the given vector.
	 * 
	 * @param w	The given vector.
	 * @return	Vector3f
	 * 		Non collinear vector.
	 */
	protected Vector3f getNonCollinearVector(Vector3f w) {
		Vector3f t = new Vector3f(w);
		float tempX = Math.abs(w.x);
		float tempY = Math.abs(w.y);
		float tempZ = Math.abs(w.z);
    	if(tempY < tempX){
    		if(tempZ < tempY){
    			t.set(w.x, w.y, 1);
    		} else{
    			t.set(w.x, 1, w.z);
    		}
    	} else if(tempZ < tempX){
    		t.set(w.x, w.y, 1);
    	} else{
    		t.set(1, w.y, w.z);
    	}
    	return t;
	}

	
	
	
}
