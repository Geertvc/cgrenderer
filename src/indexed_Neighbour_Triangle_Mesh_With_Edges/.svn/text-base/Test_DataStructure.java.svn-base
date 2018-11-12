package indexed_Neighbour_Triangle_Mesh_With_Edges;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import abstractModel.Matrix4f;
import abstractModel.Vector3f;

/**
 * Test case to test the main mesh data structure.
 * 
 * @author Geert Van Campenhout
 */
public class Test_DataStructure {
	Mesh mesh;
	
	//Zeer basic gegevens. (1 driehoek)
	//int[] vertexToEdge = {0,1,2};
	//int[] edgeToTriangle = {0,0,0};
	//int[][] indexVertex = {{0,1,2}};
	//int[][] indexNbrEdge = {{0,1,2}};
	
	
	//Uitgebreide gegevens. (een kubus)
	float[][] coordinates = {{0,0,0},{1,0,0},{1,1,0},{0,1,0},{0,0,1},{1,0,1},{1,1,1},{0,1,1}};
	int[] vertexToEdge = {0,2,4,6,16,14,17,15};
	int[] edgeToTriangle = {0,0,1,2,3,9,8,7,0,11,10,7,8,1,3,5,6,9};
	int[][] indexVertex = {{4,0,1},{4,1,5},{5,1,2},{6,5,2},{7,4,5},{6,7,5},{4,7,0},{7,3,0},{7,2,3},{7,6,2},{1,0,3},{1,3,2}};
	int[][] indexNbrEdge = {{0,8,1},{13,1,2},{2,9,3},{14,3,4},{16,13,15},{17,15,14},{16,7,0},{6,7,11},{5,12,6},{17,4,5}
						,{8,11,10},{10,12,9}};

	@Before
	public void setUp() throws Exception {
		Matrix4f transMatrix = new Matrix4f();
		transMatrix.setIdentity();
		mesh = new Mesh(coordinates, vertexToEdge, edgeToTriangle, indexVertex, indexNbrEdge,"test");
	}

	/**
	 * Testing all vertices of the created mesh.
	 */
	@Test
	public void test_vertex() {
		Vertex[] vertex = mesh.getVertices();
		int vertexLength = vertex.length;
		Assert.assertEquals(vertexToEdge.length, vertexLength);
		for (int i = 0; i < vertexLength; i++) {
			Assert.assertEquals(vertexToEdge[i], vertex[i].NBrEdge);
		}
	}
	
	/**
	 * Testing all edges of the created mesh.
	 */
	@Test
	public void test_edge() {
		Edge[] edge = mesh.getEdges();
		int edgeLength = edge.length;
		Assert.assertEquals(edgeToTriangle.length, edgeLength);
		for (int i = 0; i < edgeLength; i++) {
			Assert.assertEquals(edgeToTriangle[i], edge[i].triangle);
			int k = -1;
			for (int j = 0; j < indexNbrEdge[edgeToTriangle[i]].length; j++) {
				if(j == edge[i].i){
					k = j;
					break;
				}
			}
			Assert.assertEquals(k, edge[i].i);
		}
	}
	
	/**
	 * Testing all triangles of the created mesh.
	 */
	@Test
	public void test_triangle() {
		Triangle[] triangle = mesh.getTriangles();
		int triangleLength = triangle.length;
		Assert.assertEquals(indexVertex.length, triangleLength);
		Assert.assertEquals(indexNbrEdge.length, triangleLength);
		for (int i = 0; i < triangleLength; i++) {
			Assert.assertEquals(indexVertex[i], triangle[i].vertex);
			Assert.assertEquals(indexNbrEdge[i], triangle[i].edgeNbr);
		}
	}

	/**
	 * Testing all the coordinates of the vertices from the created mesh.
	 */
	@Test
	public void test_vertex_coordinates() {
		Vertex[] vertex = mesh.getVertices();
		int vertexLength = vertex.length;
		Vector3f coord;
		for (int i = 0; i < vertexLength; i++) {
			coord = new Vector3f(coordinates[i]);
			Assert.assertEquals(coord.x, vertex[i].coord.x);
			Assert.assertEquals(coord.y, vertex[i].coord.y);
			Assert.assertEquals(coord.z, vertex[i].coord.z);
		}
	}
}
