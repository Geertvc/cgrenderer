package sceneModel;

import abstractModel.Color3f;
import abstractModel.Point3f;
import abstractModel.Vector3f;

/**
 * A light that shines like a spotLight, in a direction and the intensity depending on the angle.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class SpotLight extends Light {

	public Point3f position;
	public Vector3f direction;
	public float angle;

	/**
	 * Creates a new Spotlight with the given parameters.
	 * 
	 * @param position	The position of the spotLight.
	 * @param direction	The direction the spotLight shines in.
	 * @param angle	The angle on which the intensity of the spotLight depends.
	 * @param intensity	The intensity of the spotLight.
	 * @param color	The color of the light.
	 * @param name	The name of the spotLight.
	 */
	public SpotLight(Point3f position, Vector3f direction, float angle, float intensity, Color3f color, String name) {
		super(intensity, color, name);
		this.position = position;
		this.direction = direction;
		this.angle = angle;
	}

}
