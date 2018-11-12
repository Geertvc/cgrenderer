package abstractModel;

/**
 * Class that represents an Intersection between a ray and an object.
 * 
 * @author Geert Van Campenhout
 */
public class Intersection {
	
	public Point3f intersectionPoint;
	public float currentT;
	
	/**
	 * Creates a new Intersection with the given intersectionPoint and t.
	 * 
	 * @param intersectionPoint	The point of intersection.
	 * @param currentT	The ray's t value at the intersection.
	 */
	public Intersection(Point3f intersectionPoint, float currentT){
		this.intersectionPoint = intersectionPoint;
		this.currentT = currentT;
	}
}
