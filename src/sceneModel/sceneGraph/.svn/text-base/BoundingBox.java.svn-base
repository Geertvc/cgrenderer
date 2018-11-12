package sceneModel.sceneGraph;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Triangle;
import indexed_Neighbour_Triangle_Mesh_With_Edges.Vertex;
import ray_Tracing.Ray;
import abstractModel.Intersection;
import abstractModel.IntersectionRecord;
import abstractModel.Point3f;

/**
 * Class representing a simple BoundingBox with containing triangles.
 * The contained triangles are stored as simples Integers, the corresponding Triangle object
 * need to be given when they are needed in the methods.
 * 
 * @author Geert Van Campenhout
 */
public class BoundingBox extends AbstractBoundingBox{
	
	private Integer[] containedTriangles;

	/**
	 * Creates a new BoundingBox.
	 * 
	 * @param name	IT IS IMPORTANT TO GIVE THE BOUNDINGBOX THE EXACT SAME NAME AS THE NAME OF THE MESH OF THE CONTAINED TRIANGLES.
	 * @param xMin	The minimal x coordinate of the AbstractBoundingBox.
	 * @param xMax	The maximal x coordinate of the AbstractBoundingBox.
	 * @param yMin	The minimal y coordinate of the AbstractBoundingBox.
	 * @param yMax	The maximal y coordinate of the AbstractBoundingBox.
	 * @param zMin	The minimal z coordinate of the AbstractBoundingBox.
	 * @param zMax	The maximal z coordinate of the AbstractBoundingBox.
	 */
	public BoundingBox(String name, float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
		super(name, xMin, xMax, yMin, yMax, zMin, zMax);
	}
	
	/**
	 * Creates a new BoundingBox.
	 * 
	 * @param name	IT IS IMPORTANT TO GIVE THE BOUNDINGBOX THE EXACT SAME NAME AS THE NAME OF THE MESH OF THE CONTAINED TRIANGLES.
	 * @param min	The minimum coordinates of the BoundingBox in Point3f format.
	 * @param max	The maximal coordinates of the BoundingBox in Point3f format.
	 */
	public BoundingBox(String name, Point3f min, Point3f max){
		this(name, min.x, max.x, min.y, max.y, min.z, max.z);
	}
	
	/**
	 * Creates a new BoundingBox.
	 * 
	 * @param name	IT IS IMPORTANT TO GIVE THE BOUNDINGBOX THE EXACT SAME NAME AS THE NAME OF THE MESH OF THE CONTAINED TRIANGLES.
	 * @param min	The minimum coordinates of the BoundingBox in Point3f format.
	 * @param max	The maximal coordinates of the BoundingBox in Point3f format.
	 * @param containedTriangles	The Integer[] representing the containedTriangles.
	 */
	public BoundingBox(String name, Point3f min, Point3f max, Integer[] containedTriangles){
		this(name, min, max);
		this.containedTriangles = containedTriangles;
	}

	/* (non-Javadoc)
	 * @see sceneModel.sceneGraph.AbstractBoundingBox#hitContainedTriangles(ray_Tracing.Ray, float, float, indexed_Neighbour_Triangle_Mesh_With_Edges.Triangle[], indexed_Neighbour_Triangle_Mesh_With_Edges.Vertex[])
	 */
	@Override
	public IntersectionRecord hitContainedTriangles(Ray ray, float t0, float t1, Triangle[] triangles, Vertex[] vertices) {
		IntersectionRecord intersectionRecord = null;
		Intersection temp = null;
		for (int v = 0; v < this.containedTriangles.length; v++) {
			int index = this.containedTriangles[v];
			Vertex a = vertices[triangles[index].vertex[0]];
			Vertex b = vertices[triangles[index].vertex[1]];
			Vertex c = vertices[triangles[index].vertex[2]];
			if(!((temp = Triangle.rayTriangleIntersection(ray, a,b,c,t0,t1)) == null)){
				intersectionRecord = new IntersectionRecord(this.getName(), temp, a, b, c);
				t1 = intersectionRecord.currentT;
			}
		}
		return intersectionRecord;
	}
	
	/**
	 * Array with indices of the contained triangles in this boundingBox, can be null.
	 * 
	 * @return
	 */
	public Integer[] getContainedTriangles() {
		return containedTriangles;
	}

}
