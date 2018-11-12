package sceneModel.sceneGraph;

import sceneModel.Surface;
import abstractModel.Matrix4f;
import abstractModel.Vector3f;

public class MovingLeafNode extends LeafNode{
	
	float velocity;
	Vector3f translationDirection;

	public MovingLeafNode(String name, String materialName,
			Matrix4f localTransformMatrix, SceneGraphElement root,
			Surface surface, float velocity, Vector3f direction) {
		super(name, materialName, localTransformMatrix, root, surface);
		this.velocity = velocity;
		this.translationDirection = direction;
	}
	
	public Matrix4f getMovementTransformMatrix(float Tvalue){
		return new Matrix4f(1, 0, 0, velocity*Tvalue*translationDirection.x, 
				0, 1, 0, velocity*Tvalue*translationDirection.y, 
				0, 0, 1, velocity*Tvalue*translationDirection.z, 
				0, 0, 0, 1);
	}
	
	public Matrix4f getTotalTransformMatrix(float time) {
		Matrix4f movementMatrix = new Matrix4f();
		movementMatrix.multiplyRight(super.getTotalTransformMatrix(), getMovementTransformMatrix(time));
		return movementMatrix;
	}
	
	public Matrix4f getInvertedTotalTransformMatrix(float time){
		Matrix4f invertedMatrix = new Matrix4f();
		Matrix4f inversMovementMatrix =  new Matrix4f(1, 0, 0, -velocity*time*translationDirection.x, 
				0, 1, 0, -velocity*time*translationDirection.y, 
				0, 0, 1, -velocity*time*translationDirection.z, 
				0, 0, 0, 1);
		Matrix4f inverseTotalMatrix = new Matrix4f();
		inverseTotalMatrix.invert(super.getTotalTransformMatrix());
		invertedMatrix.multiplyRight(inversMovementMatrix, inverseTotalMatrix);
		return invertedMatrix;
	}

}
