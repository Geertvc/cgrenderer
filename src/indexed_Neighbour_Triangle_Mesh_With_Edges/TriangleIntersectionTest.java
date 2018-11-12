package indexed_Neighbour_Triangle_Mesh_With_Edges;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ray_Tracing.Ray;
import abstractModel.Matrix4f;
import abstractModel.Point3f;
import abstractModel.Vector3f;

/**
 * JUnit test case for the ray-triangle intersection methods.
 * 
 * @author Geert Van Campenhout
 */
public class TriangleIntersectionTest {
	
	Point3f origin;
	Point3f imagePoint;
	Vector3f direction;
	Matrix4f transMatrix;
	
	@Before
	public void setUp() throws Exception {
		origin = new Point3f();
		imagePoint = new Point3f(1.0f,1.0f,1.0f);
		direction = new Vector3f(1.0f,1.0f,1.0f);
		transMatrix = new Matrix4f();
		transMatrix.setIdentity();
	}

	/**
	 * Sets up the mesh needed to test the ray-triangle intersection methods.
	 * 
	 * @return	Mesh
	 * 		The initialized mesh.
	 */
	protected Mesh setupMesh(){
		float[][] coordinates = {{1,0,0},{0,1,0},{0,0,1}};
		int[] vertexToEdge = {0,1,2};
		int[] edgeToTriangle = {0,0,0};
		int[][] indexVertex = {{0,1,2}};
		int[][] indexNbrEdge = {{0,1,2}};
		return new Mesh(coordinates, vertexToEdge, edgeToTriangle, indexVertex, indexNbrEdge,"test");
	}
	
	/**
	 * Sets up a second mesh needed to test the ray-triangle intersection methods.
	 * 
	 * @return	Mesh
	 * 		The initialized mesh.
	 */
	protected Mesh setupMesh2(){
		float[][] coordinates = {{0,0,0},{0,1,0},{1,0,0}};
		int[] vertexToEdge = {0,1,2};
		int[] edgeToTriangle = {0,0,0};
		int[][] indexVertex = {{0,1,2}};
		int[][] indexNbrEdge = {{0,1,2}};
		return new Mesh(coordinates, vertexToEdge, edgeToTriangle, indexVertex, indexNbrEdge,"test2");
	}
	
	/**
	 * Test: rayTriangleIntersection succeeds
	 */
	@Test
	public void test_RayTriangleIntersectionSucceed(){
		Mesh mesh = setupMesh();
		Triangle[] triangles = mesh.getTriangles();
		Vertex[] vertices = mesh.getVertices();
		float currentT = Float.MAX_VALUE;
		for (int i = 0; i < triangles.length; i++) {
			Vertex a = vertices[triangles[i].vertex[0]];
			Vertex b = vertices[triangles[i].vertex[1]];
			Vertex c = vertices[triangles[i].vertex[2]];
			Ray PerpendicularRay = new Ray(origin,direction);
			Assert.assertNotNull(Triangle.rayTriangleIntersection(PerpendicularRay,a,b,c,currentT));
			currentT = Float.MAX_VALUE;
			Ray HittingVertexRay = new Ray(origin,new Vector3f(0f,0f,1f));
			Assert.assertNotNull(Triangle.rayTriangleIntersection(HittingVertexRay,a,b,c,currentT));
			currentT = Float.MAX_VALUE;
			Ray InTriangleRay = new Ray(new Point3f(1,0,0),new Vector3f(0f,2f,-1f));
			Assert.assertNotNull(Triangle.rayTriangleIntersection(InTriangleRay,a,b,c,currentT));
		}
	}
	
	/**
	 * Test: rayTriangleIntersection succeed with rays crossing the borders of the triangle or lying in the triangle.
	 */
	@Test
	public void test2_RayTriangleIntersectionSucceed(){
		Mesh mesh = setupMesh2();
		Triangle[] triangles = mesh.getTriangles();
		Vertex[] vertices = mesh.getVertices();
		float currentT = Float.MAX_VALUE;
		for (int i = 0; i < triangles.length; i++) {
			Vertex a = vertices[triangles[i].vertex[0]];
			Vertex b = vertices[triangles[i].vertex[1]];
			Vertex c = vertices[triangles[i].vertex[2]];
			Ray InTrianglePlaneRay = new Ray(new Point3f(1,-1,0),new Vector3f(0f,1f,0f));
			Assert.assertNotNull(Triangle.rayTriangleIntersection(InTrianglePlaneRay, a,b,c,currentT));
			currentT = Float.MAX_VALUE;
			Ray InTrianglePlaneOnePointIntersectionRay = new Ray(new Point3f(1,-1,0),new Vector3f(0f,1f,0f));
			Assert.assertNotNull(Triangle.rayTriangleIntersection(InTrianglePlaneOnePointIntersectionRay,a,b,c,currentT));
		}
	}
	
	/**
	 * Test: rayTriangleIntersection with rays that dont intersect the given triangle or 
	 * that intersect the given triangle at a t value greater than the currentT.
	 */
	@Test
	public void test_RayTriangleIntersectionFail(){
		Mesh mesh = setupMesh();
		Triangle[] triangles = mesh.getTriangles();
		Vertex[] vertices = mesh.getVertices();
		float currentT = Float.MAX_VALUE;
		for (int i = 0; i < triangles.length; i++) {
			Vertex a = vertices[triangles[i].vertex[0]];
			Vertex b = vertices[triangles[i].vertex[1]];
			Vertex c = vertices[triangles[i].vertex[2]];
			Ray downRay = new Ray(origin,new Vector3f(0f,0f,-1f));
			Assert.assertNull(Triangle.rayTriangleIntersection(downRay,a,b,c,currentT));
			Ray parallelRay = new Ray(origin,new Vector3f(1f,1f,-1f));
			Assert.assertNull(Triangle.rayTriangleIntersection(parallelRay,a,b,c,currentT));
			Ray otherSideRay = new Ray(origin,new Vector3f(-1f,-1f,-1f));
			Assert.assertNull(Triangle.rayTriangleIntersection(otherSideRay,a,b,c,currentT));
			Ray PerpendicularRay = new Ray(origin,direction);
			Assert.assertNull(Triangle.rayTriangleIntersection(PerpendicularRay,a,b,c,0));
		}
	}
	
	/**
	 * Test: calcTriangleNormalizedNormal
	 */
	@Test
	public void test_calcTriangleNormal(){
		Mesh mesh = setupMesh2();
		Triangle triangle = mesh.getTriangles()[0];
		Vector3f normal = mesh.calcTriangleNormalizedNormal(mesh.getVertices()[triangle.vertex[0]], 
				mesh.getVertices()[triangle.vertex[1]], 
				mesh.getVertices()[triangle.vertex[2]]);
		Assert.assertEquals(new Vector3f(0,0,-1), normal);
	}


}
