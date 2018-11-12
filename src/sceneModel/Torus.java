package sceneModel;

/**
 * A SceneElement that represents a torus.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class Torus implements SceneElement {
	
	String name;
	public float innerRadius;
	public float outerRadius;
	
	/**
	 * Creates a new Torus with the given innerRadius, outerRadius and name.
	 * 
	 * @param innerRadius	The inner radius of the torus.
	 * @param outerRadius	The outer radius of the torus.
	 * @param name	The name of the torus.
	 */
	public Torus(float innerRadius, float outerRadius, String name) {
		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see sceneModel.SceneElement#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

}
