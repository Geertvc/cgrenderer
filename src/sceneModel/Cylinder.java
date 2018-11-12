package sceneModel;

/**
 * A SceneElement that represents a cylinder.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class Cylinder implements SceneElement{
	
	String name;
	public float radius;
	public float height;

	/**
	 * Creates a new cylinder with the given radius, height and name.
	 * 
	 * @param radius	The radius of the cylinder.
	 * @param height	The height of the cylinder.
	 * @param name	The name of the cylinder.
	 */
	public Cylinder(float radius, float height, String name) {
		this.radius = radius;
		this.height = height;
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
