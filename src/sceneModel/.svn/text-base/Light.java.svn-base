package sceneModel;

import abstractModel.Color3f;

/**
 * An abstract class representing a light in the scene.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public abstract class Light implements SceneElement {
	
	String name;
	public float intensity;
	public Color3f color;
	
	/**
	 * Creates a new light with the given intensity, color and name.
	 * 
	 * @param intensity	The intensity of the light.
	 * @param color	The color of the light.
	 * @param name	The name of the light.
	 */
	public Light(float intensity, Color3f color, String name){
		this.name = name;
		this.intensity = intensity;
		this.color = new Color3f(color);
	}
	
	/* (non-Javadoc)
	 * @see sceneModel.SceneElement#getName()
	 */
	@Override
	public String getName(){
		return this.name;
	}
}
