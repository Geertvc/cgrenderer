package sceneModel;

import abstractModel.Color3f;
import abstractModel.Vector3f;

/**
 * A light that shines in a direction (like the sun shines on earth).
 * So it is like a very distant light.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class DirectionalLight extends Light {

	public Vector3f direction;
	
	/**
	 * Creates a new DirectionalLight with the given direction, intensity, color and name.
	 * 
	 * @param direction	The direction the light shines in.
	 * @param intensity	The intensity of the light.
	 * @param color	The color of the light.
	 * @param name	The name of the new Light.
	 */
	public DirectionalLight(Vector3f direction, float intensity, Color3f color, String name){
		super(intensity, color, name);
		this.direction = direction;
	}
}
