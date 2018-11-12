package sceneModel;

import ray_Tracing.Ray;
import abstractModel.IntersectionRecord;

/**
 * A SceneElement that represents a surface.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public abstract class Surface implements SceneElement{
	
	protected String name;
	
	/**
	 * Creates a new surface with the given name.
	 */
	public Surface(String name){
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see sceneModel.SceneElement#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Hits the given ray with this surface and only returns the nearest intersection between t0 and t1.
	 * 
	 * @param ray	The ray to intersect.
	 * @param t0	The lower bound of the intersection.
	 * @param t1	The upper bound of the intersection.
	 * @return	IntersectionRecord
	 * 		The record of intersection, can be null if there is no intersection.
	 */
	public abstract IntersectionRecord hit(Ray ray, float t0, float t1);
}
