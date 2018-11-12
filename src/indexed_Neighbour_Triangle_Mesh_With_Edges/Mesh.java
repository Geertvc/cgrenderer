package indexed_Neighbour_Triangle_Mesh_With_Edges;

import main.Constants;
import ray_Tracing.Ray;
import sceneModel.SceneElement;
import sceneModel.Surface;
import sceneModel.sceneGraph.AbstractBoundingBox;
import sceneModel.sceneGraph.BoundingBox;
import sceneModel.sceneGraph.BoundingBoxTreeNode;
import abstractModel.Intersection;
import abstractModel.IntersectionRecord;
import abstractModel.Point3f;
import abstractModel.TexCoord2f;
import abstractModel.Vector3f;

/**
 * Represents a triangle mesh with edges.
 * 
 * @author Geert Van Campenhout
 * @version 3.1
 */
public class Mesh extends Surface implements SceneElement {
	
	Vertex[] vertex;
	Edge[] edge;
	Triangle[] triangle;
	
	AbstractBoundingBox bbox = null;

	/** Initializes a Mesh with the given arguments.
	 * 
	 * @param coordVertex
	 * 		Array containing the coordinates of the corresponding vertex.
	 * @param vertexToEdge	
	 * 		Array containing an index of an edge corresponding with the vertex.
	 * @param edgeToTriangle
	 * 		Array containing an index of a triangle corresponding with the edge.
	 * @param indexVertex
	 * 		Array containing the indices of the vertices corresponding to the triangle.
	 * @param indexNbrEdge
	 * 		Array containing the indices of the edges corresponding to the triangle.
	 * @param name
	 * 		The name of the mesh.
	 */
	public Mesh(Point3f[] coordVertex, int[] vertexToEdge, int[] edgeToTriangle, int[][] indexVertex, int[][] indexNbrEdge, String name){
		super(name);
		initializeMesh(coordVertex, vertexToEdge, edgeToTriangle, indexVertex, indexNbrEdge);
	}
	
	/** Initializes a Mesh with the given arguments.
	 * 
	 * @param coordVertex
	 * 		Array containing the coordinates of the corresponding vertex in array form.
	 * @param vertexToEdge	
	 * 		Array containing an index of an edge corresponding with the vertex.
	 * @param edgeToTriangle
	 * 		Array containing an index of a triangle corresponding with the edge.
	 * @param indexVertex
	 * 		Array containing the indices of the vertices corresponding to the triangle.
	 * @param indexNbrEdge
	 * 		Array containing the indices of the edges corresponding to the triangle.
	 * @param name
	 * 		The name of the mesh.
	 */
	public Mesh(float[][] coordVertex, int[] vertexToEdge, int[] edgeToTriangle, int[][] indexVertex, int[][] indexNbrEdge, String name){
		super(name);
		initializeMesh(getPointCoords(coordVertex), vertexToEdge, edgeToTriangle, indexVertex, indexNbrEdge);
	}
	
	/** Initializes a Mesh with the given arguments.
	 * 
	 * @param coordVertex
	 * 		Array containing the coordinates of the corresponding vertex in Point3f form.
	 * @param vertexToEdge	
	 * 		Array containing an index of an edge corresponding with the vertex.
	 * @param edgeToTriangle
	 * 		Array containing an index of a triangle corresponding with the edge.
	 * @param indexVertex
	 * 		Array containing the indices of the vertices corresponding to the triangle.
	 * @param indexNbrEdge
	 * 		Array containing the indices of the edges corresponding to the triangle.
	 */
	private void initializeMesh(Point3f[] coordVertex, int[] vertexToEdge, int[] edgeToTriangle, int[][] indexVertex, int[][] indexNbrEdge){
		
		//Create all vertices
		System.out.println("Creating all vertices...");
		this.vertex = new Vertex[vertexToEdge.length];
		for (int i = 0; i < vertexToEdge.length; i++) {
			vertex[i] = new Vertex(vertexToEdge[i], coordVertex[i]);
		}
		System.out.println("Creation of vertices completed.");
		
		//Create all edges
		System.out.println("Creating all edges...");
		this.edge = new Edge[edgeToTriangle.length];
		for (int i = 0; i < edgeToTriangle.length; i++) {
			//We need to search the index of the edge in the triangle to which the edge points.
			boolean found = false;
			int[] edges = indexNbrEdge[edgeToTriangle[i]];
			int j;
			for (j = 0; j < edges.length; j++) {
				if(edges[j] == i){
					found = true;
					break; 
				}
			}
			if(!found){
				throw new IllegalArgumentException("Cannot find the index of the Edge in the corresponding triangle.");
			}
			edge[i] = new Edge(edgeToTriangle[i],j);
		}
		System.out.println("Creation of edges completed.");
		
		//Create all triangles
		System.out.println("Creating all triangles...");
		this.triangle = new Triangle[indexVertex.length];
		int indexVertexLength = indexVertex.length;
		if(indexVertexLength != indexNbrEdge.length)
			throw new IllegalArgumentException("indexVertex and indexNbrEdge contain a different amount of triangles");
		for (int i = 0; i < indexVertexLength; i++) {
			//Create a new triangle with his correspondent vertices (index of vertices) and edges(index of edges)
			Vertex a = vertex[indexVertex[i][0]];
			Vertex b = vertex[indexVertex[i][1]];
			Vertex c = vertex[indexVertex[i][2]];
			triangle[i] = new Triangle(indexVertex[i],indexNbrEdge[i], calcTriangleNormalizedNormal(a,b,c));
		}
		System.out.println("Creation of triangles completed.");
	}
	
	/**
	 * Transforms the float[][] into a Point3f[] array.
	 * 
	 * @param coordVertex	The float[][] array to transform.
	 * @return	Point3f[]
	 * 		The transformed coordinates.
	 */
	private Point3f[] getPointCoords(float[][] coordVertex){
		Point3f vectorCoords[] = new Point3f[coordVertex.length]; 
		for (int i = 0; i < coordVertex.length; i++) {
			vectorCoords[i] = new Point3f(coordVertex[i]);
		}
		return vectorCoords;
	}
	
	/** Returns the list with initialized vertices listed in the Mesh.
	 * 
	 * @return	Vertex[]
	 * 		Array containing all the vertices of the Mesh.
	 */
	public Vertex[] getVertices(){
		return this.vertex;
	}
	
	/** Returns the list with initialized edges listed in the Mesh.
	 * 
	 * @return	Edge[]
	 * 		Array containing all the edges of the Mesh.
	 */
	public Edge[] getEdges(){
		return this.edge;
	}
	
	/** Returns the list with initialized triangles listed in the Mesh.
	 * 
	 * @return	Triangle[]
	 * 		Array containing all the triangles of the Mesh.
	 */
	public Triangle[] getTriangles(){
		return this.triangle;
	}
	
	/**
	 * Calculates and returns the normalized normal for the given vertices of a triangle.
	 * Note: the vertices must be given in counter-clockwise order so the normal will point outwards the model.
	 *  
	 * @param a	The first vertex of the triangle.
	 * @param b The second vertex of the triangle.
	 * @param c The third vertex of the triangle.
	 * @return	Vector3f
	 * 		The normalized normal of the triangle.
	 */
	protected Vector3f calcTriangleNormalizedNormal(Vertex a, Vertex b, Vertex c){
		Vector3f bMinA = new Vector3f(b.coord.x-a.coord.x,b.coord.y-a.coord.y,b.coord.z-a.coord.z);
		Vector3f cMinA = new Vector3f(c.coord.x-a.coord.x,c.coord.y-a.coord.y,c.coord.z-a.coord.z);
		Vector3f normal = new Vector3f();
		normal.cross(bMinA, cMinA);
		normal.scale(1/normal.length());
		return normal;
	}
	
	/**
	 * Creates a mesh containing vertices, edges and triangles.
	 * For each triangle 3 new vertices and 3 new edges are made, so no real data saving is used.
	 * 
	 * @param coordinates	The Point3f[] with the corresponding coordinates of all the vertices in the mesh.
	 * @param normals	The Vector3f[] with the corresponding normals of all the vertices in the mesh.
	 * @param textureCoordinates	The TexCoord2f[] with the corresponding texture coordinates of all the vertices in the mesh.
	 * @param coordinateIndices		The indices of all vertices in a triangle, for each three entries in coordinateIndices there is one triangle.
	 * @param normalIndices		The indices of all vertix normals in a triangle.
	 * @param textureCoordinateIndices	The indices of all texture coordinates in a triangle.
	 * @param transMatrix	The transformationMatrix used to transform the mesh.
	 * @param name	The name of the mesh.
	 * @pre		The length of normalIndices need to be 0 or equal to coordinateIndices.
	 * 		normalIndices.length == 0 || normalIndices.length == coordinateIndices.length
	 * @pre		The length of textureCoordinateIndices need to be 0 or equal to coordinateIndices.
	 * 		textureCoordinateIndices.length == 0 || textureCoordinateIndices.length == coordinateIndices.length
	 */
	public Mesh(Point3f [] coordinates, Vector3f [] normals, TexCoord2f [] textureCoordinates, 
			int [] coordinateIndices, int [] normalIndices, int [] textureCoordinateIndices, String name){
		super(name);
		if(coordinateIndices.length%3 != 0)
			throw new IllegalArgumentException("Mesh constructor coordinates is not of a length that is a multiple of 3.");
		this.vertex = new Vertex[coordinateIndices.length];
		this.triangle = new Triangle[coordinateIndices.length/3];
		this.edge = new Edge[coordinateIndices.length];
		Vertex currentVertex = null;
		
		for (int v = 0; v < coordinateIndices.length; v++) {
			int NBrEdge = v;
			TexCoord2f texCoord = null;
			if(textureCoordinateIndices.length != 0){
				texCoord = textureCoordinates[textureCoordinateIndices[v]];
			}
			Vector3f normal = null;
			if(normalIndices.length != 0){
				normal = new Vector3f(normals[normalIndices[v]]);
			}
			currentVertex = new Vertex(NBrEdge, coordinates[coordinateIndices[v]], 
					normal, texCoord);
			this.vertex[v] = currentVertex;
			this.edge[v] = new Edge((v-(v%3))/3, v%3);
		}
		
		for (int i = 0; i < coordinateIndices.length/3; i++) {
			Vertex a = vertex[i*3];
			Vertex b = vertex[(i*3) + 1];
			Vertex c = vertex[(i*3) + 2];
			this.triangle[i] = new Triangle(new int[] {i*3,(i*3)+1,(i*3)+2}, 
					new int[] {i*3,(i*3)+1,(i*3)+2}, 
					calcTriangleNormalizedNormal(a,b,c));
		}
	}

	/** Initializes a Mesh with the given arguments.
	 * 
	 * @param coordinates Array containing the coordinates of the corresponding vertex.
	 * @param normals	Array containing the coordinates of the corresponding vertex normal.
	 * @param edgeToTriangle	Array containing an index of a triangle corresponding with the edge.
	 * @param indexVertex	Array containing the indices of the vertices corresponding to the triangle.
	 * @param indexNbrEdge	Array containing the indices of the edges corresponding to the triangle.
	 * @param name	The name of the mesh.
	 * @param minValuesMesh	The minimum values of the coordinates in the mesh.
	 * @param maxValuesMesh	The maximum values of the coordinates in the mesh.
	 */
	public Mesh(Point3f[] coordinates, Vector3f[] normals,
			TexCoord2f[] textureCoordinates, int[] coordinateIndices,
			int[] normalIndices, int[] textureCoordinateIndices,
			String name, Point3f minValuesMesh, Point3f maxValuesMesh) {
		this(coordinates, normals, textureCoordinates, coordinateIndices, normalIndices, textureCoordinateIndices, name);
		if(Constants.HIERARCHICALBOUNDINGBOX){
			Integer[] triangleIndices = new Integer[this.triangle.length];
			for (int i = 0; i < triangleIndices.length; i++) {
				triangleIndices[i] = i;
			}
			System.out.println("Number of triangles: " + triangle.length + " number of triangleIndices: " + triangleIndices.length);
			bbox = new BoundingBoxTreeNode(getName(), minValuesMesh, maxValuesMesh, 0, triangleIndices, this, 0, Constants.LEVELOFHIERARCHICALBOUNDINGBOXTREE);
		} else{
			bbox = new BoundingBox(getName(), minValuesMesh, maxValuesMesh);
		}
	}

	/* (non-Javadoc)
	 * @see sceneModel.Surface#hit(ray_Tracing.Ray, float, float)
	 */
	@Override
	public IntersectionRecord hit(Ray ray, float t0, float t1){
		if(Constants.HIERARCHICALBOUNDINGBOX){
			return bbox.hitContainedTriangles(ray, t0, t1, this.triangle, this.vertex);
		}else if(Constants.BOUNDINGBOX){
			if(bbox == null)
				throw new IllegalStateException("Bounding box is null while using bouding boxes.");
			//Use bounding boxes as acceleration structure.
			if(bbox.hitBox(ray, t0, t1)){
				return hitMesh(ray, t0, t1);
			} else{
				//This is the accelerating step.
				return null;
			}
		} else{
			//Dont use bounding boxes as acceleration structure.
			return hitMesh(ray, t0, t1);
		}
	}
	
	/**
	 * Hits the mesh with the given ray and t values.
	 * 
	 * @param ray	The ray to intersect with the mesh.
	 * @param t0	The minimum t value of the intersection.
	 * @param t1	The maximum t value of the intersection.
	 * @return	IntersectionRecord
	 * 		The calculated Intersection with the mesh.
	 */
	public IntersectionRecord hitMesh(Ray ray, float t0, float t1) {
		IntersectionRecord intersectionRecord = null;
		Intersection temp = null;
		Vertex[] vertices = getVertices();
		Triangle[] triangles = getTriangles();
		for (int v = 0; v < triangles.length; v++) {
			Vertex a = vertices[triangles[v].vertex[0]];
			Vertex b = vertices[triangles[v].vertex[1]];
			Vertex c = vertices[triangles[v].vertex[2]];
			if(!((temp = Triangle.rayTriangleIntersection(ray, a,b,c,t0,t1)) == null)){
				intersectionRecord = new IntersectionRecord(this.getName(), temp, a, b, c);
				t1 = intersectionRecord.currentT;
			}
		}
		return intersectionRecord;
	}

	/**
	 * Returns the AbstractBoundingBox of this mesh.
	 * @return
	 */
	public AbstractBoundingBox getBbox() {
		return bbox;
	}
	
}
