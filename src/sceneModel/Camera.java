package sceneModel;

import abstractModel.Point3f;
import abstractModel.Vector3f;

/**
 * A camera that is a SceneElement
 *  
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class Camera implements SceneElement {
	
	String name;
	public Point3f position;
	public Vector3f direction;
	public Vector3f up;
	//field of view in degrees
	public float fovy;
	
	/**
	 * Creates a new camera with a position, viewing direction, up direction, a field of view and a name.
	 * @param position	The position the camera.
	 * @param direction	The direction the camera looks in.
	 * @param up	The up direction of the camera.
	 * @param fovy	The field of view.
	 * @param name	The name of the camera.
	 */
	public Camera(Point3f position, 
			Vector3f direction, Vector3f up, float fovy, String name){
		this.name = name;
		this.position = position;
		this.direction = direction;
		this.up = up;
		this.fovy = fovy;
	}

	/* (non-Javadoc)
	 * @see sceneModel.SceneElement#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
}
