package sceneModel;

/**
 * A SceneElement that represents a teapot.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class Teapot implements SceneElement {

	String name;
	public float size;
	
	/**
	 * Creates a new teapot with the given size and name.
	 * 
	 * @param size	The size of the teapot.
	 * @param name	The name of the teapot.
	 */
	public Teapot(float size, String name) {
		this.size = size;
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
