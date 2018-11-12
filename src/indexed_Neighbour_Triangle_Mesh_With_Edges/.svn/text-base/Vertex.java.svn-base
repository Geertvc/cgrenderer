package indexed_Neighbour_Triangle_Mesh_With_Edges;

import abstractModel.Point3f;
import abstractModel.TexCoord2f;
import abstractModel.Vector3f;

/**
 * A Vertex represents a node in the Triangle Mesh.
 * The Vertex contains its coordinates, his normal, a texture coordinate.
 * 
 * @author Geert Van Campenhout
 */
public class Vertex {
	
	//index of any edge leaving the vertex
	public int NBrEdge;
	public Point3f coord;
	
	public Vector3f normal;
	public TexCoord2f texCoord;
	
	/** Initializes a Vertex with the given index of any edge leaving the vertex.
	 * 
	 * @param Neighbour	index of any edge leaving the vertex.
	 * @param float x-coordinate of the vertex
	 * @param float y-coordinate of the vertex
	 * @param float z-coordinate of the vertex
	 */
	public Vertex(int NBrEdge, float x, float y, float z){
		this.coord = new Point3f(x,y,z);
		this.NBrEdge = NBrEdge;
		this.normal = null;
	}
	
	/** Initializes a Vertex with the given index of any edge leaving the vertex.
	 * 
	 * @param Neighbour	index of any edge leaving the vertex.
	 * @param Vector3f The coordinates of the vertex in Vector3f format.
	 */
	public Vertex(int NBrEdge, Point3f coord){
		this.coord = coord;
		this.NBrEdge = NBrEdge;
		this.normal = null;
	}
	
	/** Initializes a Vertex with the given index of any edge leaving the vertex.
	 * 
	 * @param Neighbour	index of any edge leaving the vertex.
	 * @param Vector3f The coordinates of the vertex in Vector3f format.
	 */
	public Vertex(int NBrEdge, float coord[]){
		this.coord = new Point3f(coord);
		this.NBrEdge = NBrEdge;
		this.normal = null;
	}
	
	/**
	 * Initializes a Vertex with the given index of any edge leaving the vertex,
	 * his coordinates, his normal vector and a texture coordinate.
	 * 
	 * @param NBrEdge	index of any edge leaving the vertex.
	 * @param coord	The coordinate of the vertex.
	 * @param normal	The vertex normal.
	 * @param texCoord	The texture coordinate of the vertex.
	 */
	public Vertex(int NBrEdge, Point3f coord, Vector3f normal, TexCoord2f texCoord){
		this.coord = coord;
		this.NBrEdge = NBrEdge;
		normal.scale(1/normal.length());
		this.normal = normal;
		this.texCoord = texCoord;
	}
}