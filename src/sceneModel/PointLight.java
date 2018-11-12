package sceneModel;

import abstractModel.Color3f;
import abstractModel.Point3f;

/**
 * A light that shines in all directions with a constant intensity.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class PointLight extends Light {

	public Point3f position;

	/**
	 * Creates a new PointLight with the given parameters.
	 * 
	 * @param intensity	The intensity of the light.
	 * @param color	The color of the light.
	 * @param name	The name of the pointLight.
	 */
	public PointLight(float intensity, Color3f color, String name) {
		super(intensity, color, name);
		this.position = new Point3f();
	}
	
	/**
	 * Creates a new PointLight with the given parameters.
	 * 
	 * @param position	The position of the pointLight.
	 * @param intensity	The intensity of the light.
	 * @param color	The color of the light.
	 * @param name	The name of the pointLight.
	 */
	public PointLight(Point3f position, float intensity, Color3f color, String name) {
		super(intensity, color, name);
		this.position = new Point3f(position);
	}
	
}
