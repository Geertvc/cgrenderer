package sceneModel;

/**
 * A SceneElement that represents a cone.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class Cone implements SceneElement {
	
	String name;
	public float radius;
	public float height;
	public boolean capped;

	/**
	 * Creates a new Cone with the given radius, height and name.
	 * The argument capped is used to specify whether the Cone is closed at the bottom or not.
	 * 
	 * @param radius	The radius at the bottom of the cone.
	 * @param height	The height of the cone.
	 * @param capped	Whether the cone is closed at the bottom or not.
	 * @param name	The name of the Cone.
	 */
	public Cone(float radius, float height, boolean capped, String name) {
		this.radius = radius;
		this.height = height;
		this.capped = capped;
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see sceneModel.SceneElement#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

}
