package indexed_Neighbour_Triangle_Mesh_With_Edges;

import static org.junit.Assert.assertFalse;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import abstractModel.Point3f;
import abstractModel.Scene;
import abstractModel.Vector3f;

/**
 * Test case to test the calculation of barycentric coordinates.
 * 
 * @author Geert Van Campenhout
 */
public class Test_Barycentric {
	
	Vertex a;
	Vertex b;
	Vertex c;
	Point3f p;
	Scene scene;
	Vector3f bary;
	float diff = .000001f;
	float[][] intersections;
	float[][] baryCoordsToCheck;
	
	@Before
	public void setUp() throws Exception {
		scene = new Scene();
	}

	/**
	 * Testing the method getBarycentricCoordinates3DCalculatedWithAreas in a simple way.
	 */
	@Test
	public void test_barycentricCoordinates3DCalculatedWithAreasSimple() {
		a = new Vertex(0, 0, 0, 0);
		b = new Vertex(1, 1, 0, 0);
		c = new Vertex(2, 0, 1, 0);
		p = new Point3f();
		//The intersectionPoints to check.
		intersections = new float[][] {{0, 0, 0}, {.5f, 0, 0}, {0, .5f, 0}, {.5f, .5f, 0}, {.25f, .25f, 0}, {.25f, .5f, 0}, {.5f, .25f, 0}};
		//The respectively expected barycentric coordinates for the intersectionPoint.
		baryCoordsToCheck = new float[][] {{1,0,0},{.5f, .5f, 0},{.5f, 0, .5f},{0, .5f, .5f},{.5f, .25f, .25f}, {.25f, .25f, .5f}, {.25f, .5f, .25f}};
		calculateAndCheckCoordinates(a, b, c, intersections, baryCoordsToCheck);
	}

	/**
	 * Testing the method getBarycentricCoordinates3DCalculatedWithAreas with a 3D example.
	 */
	@Test
	public void test_getBarycentricCoordinates3DCalculatedWithAreas3D() {
		a = new Vertex(0, -1.5f, 4, -1);
		b = new Vertex(1, 6, 2, 0.5f);
		c = new Vertex(2, 3, -2, 5);
		p = new Point3f();
		intersections = new float[][] {{.75f, 1, 2}, {2.25f, 3, -.25f}, {1.5f, 2, 0.875f}};
		baryCoordsToCheck = new float[][] {{.5f, 0, .5f}, {.5f, .5f, 0}, {.5f, .25f, .25f}};
		calculateAndCheckCoordinates(a, b, c, intersections, baryCoordsToCheck);
	}
	
	/**
	 * Testing the method getBarycentricCoordinates3DCalculatedWithAreas with an example that should fail.
	 */
	@Test
	public void test_getBarycentricCoordinates3DCalculatedWithAreasFail(){
		a = new Vertex(0, -1.5f, 4, -1);
		b = new Vertex(1, 6, 2, 0.5f);
		c = new Vertex(2, 3, -2, 5);
		p = new Point3f();
		intersections = new float[][] {{-1, -1, -1}, {0,0,0}, {1,1,1}, {7.5f,-8,11}};
		for (int i = 0; i < intersections.length; i++) {
			p.set(new Point3f(intersections[i]));
			bary = Triangle.getBarycentricCoordinates3DCalculatedWithAreas(a, b, c, p);
			assertFalse(Math.abs(((1-bary.y-bary.z) - bary.x ))< diff);
		}
	}
	
	/**
	 * Calculates and checks the calculated coordinates.
	 * 
	 * @param a	The first vertex of the triangle.
	 * @param b The second vertex of the triangle.
	 * @param c The third vertex of the triangle.
	 * @param intersections	The test cases with the intersection coordinates.
	 * @param baryCoordsToCheck	The test cases with the barycentricCoordinates.
	 */
	protected void calculateAndCheckCoordinates(Vertex a, Vertex b, Vertex c, float[][] intersections, float[][] baryCoordsToCheck){
		for (int i = 0; i < intersections.length; i++) {
			p.set(new Point3f(intersections[i]));
			bary = Triangle.getBarycentricCoordinates3DCalculatedWithAreas(a, b, c, p);
			Assert.assertEquals(bary.x, 1-bary.y-bary.z, diff);
			Assert.assertEquals(new Vector3f(baryCoordsToCheck[i]), bary);
		}
		
	}
	
	/**
	 * Test of the method getInterpolatedNormalizedNormal method.
	 */
	@Test
	public void test_getInterpolatedNormalizedNormal(){
		a = new Vertex(0, new Point3f(0, 0, 0), new Vector3f(0,0,1), null);
		b = new Vertex(1, new Point3f(1, 0, 0), new Vector3f(0,0,1), null);
		c = new Vertex(2, new Point3f(0, 1, 0), new Vector3f(0,0,1), null);
		p = new Point3f();
		Vector3f normalizedNormal = Triangle.getInterpolatedNormalizedNormal(a, b, c, p);
		Assert.assertEquals(new Vector3f(0,0,1), normalizedNormal);
	}

}
