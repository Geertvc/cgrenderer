package abstractModel;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Vertex;

/**
 * Class that represents an Intersection record between a ray and an object.
 * Containing an Intersection, the name of the geometry that is intersected and
 * the three vertices of the triangle that is intersected.
 * 
 * @author Geert Van Campenhout
 * @version 1.1
 */
public class IntersectionRecord extends Intersection {

	public String geometryName;
	public Vertex a;
	public Vertex b;
	public Vertex c;
	
	/**
	 * Creates a new IntersectionRecord for a hit triangle.
	 * 
	 * @param geometryName	The name of the geometry that is intersected.
	 * @param intersectionPoint	The point of intersection.
	 * @param currentT	The t value of the intersectionPoint.
	 * @param a		The first vertex of the triangle that is intersected.
	 * @param b		The second vertex of the triangle that is intersected.
	 * @param c		The third vertex of the triangle that is intersected.
	 */
	public IntersectionRecord(String geometryName, Point3f intersectionPoint, float currentT, Vertex a, Vertex b, Vertex c){
		super(intersectionPoint, currentT);
		this.geometryName = geometryName;
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	/**
	 * Creates a new IntersectionRecord for a hit triangle.
	 * 
	 * @param geometryName	The name of the geometry that is intersected.
	 * @param intersection	The intersection containing basic intersection information.
	 * @param a		The first vertex of the triangle that is intersected.
	 * @param b		The second vertex of the triangle that is intersected.
	 * @param c		The third vertex of the triangle that is intersected.
	 */
	public IntersectionRecord(String geometryName, Intersection intersection, Vertex a, Vertex b, Vertex c){
		super(intersection.intersectionPoint, intersection.currentT);
		this.geometryName = geometryName;
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
}
