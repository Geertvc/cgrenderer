package sceneModel.sceneGraph;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Triangle;
import indexed_Neighbour_Triangle_Mesh_With_Edges.Vertex;
import ray_Tracing.Ray;
import sceneModel.SceneElement;
import abstractModel.IntersectionRecord;
import abstractModel.Point3f;

/**
 * Abstract class representing the general characteristics of a BoundingBox.
 * 
 * @author Geert Van Campenhout
 */
public abstract class AbstractBoundingBox implements SceneElement {

	protected String name;
	protected float xMin;
	protected float xMax;
	protected float yMin;
	protected float yMax;
	protected float zMin;
	protected float zMax;
	
	/**
	 * Creates a new AbstractBoundingBox.
	 * 
	 * @param name	IT IS IMPORTANT TO GIVE THE BOUNDINGBOX THE EXACT SAME NAME AS THE NAME OF THE MESH OF THE CONTAINED TRIANGLES.
	 * @param xMin	The minimal x coordinate of the AbstractBoundingBox.
	 * @param xMax	The maximal x coordinate of the AbstractBoundingBox.
	 * @param yMin	The minimal y coordinate of the AbstractBoundingBox.
	 * @param yMax	The maximal y coordinate of the AbstractBoundingBox.
	 * @param zMin	The minimal z coordinate of the AbstractBoundingBox.
	 * @param zMax	The maximal z coordinate of the AbstractBoundingBox.
	 */
	public AbstractBoundingBox(String name, float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
		this.name = name;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.zMin = zMin;
		this.zMax = zMax;
	}
	
	/**
	 * Calculates if the given ray hits the box within the given t values.
	 * 
	 * @param ray	The ray to intersect with the AbstractBoundingBox
	 * @param t0	The minimum t value of the intersection.
	 * @param t1	The maximum t value of the intersection.
	 * @return	boolean
	 * 		Whether the ray hits the box within the given boundaries or not.
	 */
	public boolean hitBox(Ray ray, float t0, float t1){
		float x_e = ray.origin.x;
		float a_x = 1/ray.direction.x;
		float y_e = ray.origin.y;
		float a_y = 1/ray.direction.y;
		float z_e = ray.origin.z;
		float a_z = 1/ray.direction.z;
		float t_xMin;
		float t_xMax;
		float t_yMin;
		float t_yMax;
		float t_zMin;
		float t_zMax;
		if(a_x >= 0){
			t_xMin = (a_x*(this.xMin - x_e));
			t_xMax = (a_x*(this.xMax - x_e));
		} else{
			t_xMin = (a_x*(this.xMax - x_e));
			t_xMax = (a_x*(this.xMin - x_e));
		}
		if(a_y >= 0){
			t_yMin = (a_y*(this.yMin - y_e));
			t_yMax = (a_y*(this.yMax - y_e));
		} else{
			t_yMin = (a_y*(this.yMax - y_e));
			t_yMax = (a_y*(this.yMin - y_e));
		}
		if(a_z >= 0){
			t_zMin = (a_z*(this.zMin - z_e));
			t_zMax = (a_z*(this.zMax - z_e));
		} else{
			t_zMin = (a_z*(this.zMax - z_e));
			t_zMax = (a_z*(this.zMin - z_e));
		}
		if((t_xMin > t_yMax) || (t_yMin > t_xMax))
			return false;
		else if((t_zMin > t_yMax) || (t_yMin > t_zMax)){
			return false;
		} else if((t_xMin > t_zMax) || (t_zMin > t_xMax)){
			return false;
		} else
			return true;
	}
	
	/**
	 * Calculates if the given ray hits the triangles contained within the AbstractBoundingBox within the given t values.
	 * 
	 * @param ray	The ray to intersect with the AbstractBoundingBox
	 * @param t0	The minimum t value of the intersection.
	 * @param t1	The maximum t value of the intersection.
	 * @param triangles	The triangles to find the corresponding contained Triangles in.
	 * @param vertices	The vertices of the triangles.
	 * @return	IntersectionRecord
	 * 		The intersection of the containing triangles of the AbstractBoundingBox.
	 */
	public abstract IntersectionRecord hitContainedTriangles(Ray ray, float t0, float t1, Triangle[] triangles, Vertex[] vertices);
	
	/* (non-Javadoc)
	 * @see sceneModel.SceneElement#getName()
	 */
	@Override
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns the minimal x value.
	 * @return
	 */
	public float getxMin() {
		return xMin;
	}

	/**
	 * Returns the maximal x value.
	 * @return
	 */
	public float getxMax() {
		return xMax;
	}

	/**
	 * Returns the minimal y value.
	 * @return
	 */
	public float getyMin() {
		return yMin;
	}

	/**
	 * Returns the maximal y value.
	 * @return
	 */
	public float getyMax() {
		return yMax;
	}

	/**
	 * Returns the minimal z value.
	 * @return
	 */
	public float getzMin() {
		return zMin;
	}

	/**
	 * Returns the maximal z value.
	 * @return
	 */
	public float getzMax() {
		return zMax;
	}
	
	/**
	 * The minimal Values of the coordinates of the AbstractBoundingBox.
	 * @return
	 */
	public Point3f getMinValues(){
		return new Point3f(xMin, yMin, zMin);
	}
	
	/**
	 * The maximal Values of the coordinates of the AbstractBoundingBox.
	 * @return
	 */
	public Point3f getMaxValues(){
		return new Point3f(xMax, yMax, zMax);
	}
	
}
