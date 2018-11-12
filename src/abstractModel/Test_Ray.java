package abstractModel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ray_Tracing.Ray;

/**
 * jUnit test case to test the Ray class.
 * 
 * @author Geert Van Campenhout
 */
public class Test_Ray {
	
	Point3f origin;
	Point3f secondPoint;
	Vector3f direction;
	Ray ray;
	Matrix4f transformMatrix;
	Vector3f displacement;

	@Before
	public void setUp() throws Exception {
		origin = new Point3f();
		secondPoint = new Point3f(1,1,1);
		direction = new Vector3f();
		direction.substractSet(secondPoint, origin);
		ray = new Ray(origin, direction);
	}

	/** Test of the inverseTransformRay method. */
	@Test
	public void test_inverseTransformRay() {
		displacement = new Vector3f(-1, 0, 0);
		transformMatrix = new Matrix4f(
				1, 0, 0, displacement.x, 
				0, 1, 0, displacement.y, 
				0, 0, 1, displacement.z, 
				0, 0, 0, 1);
		Ray inverseRay = ray.transformShadowRay(transformMatrix);
		Point3f newOrigin = new Point3f();
		newOrigin.substractSet(origin, displacement);
		System.err.println("newOrigin: " + newOrigin);
		Assert.assertEquals(newOrigin, inverseRay.origin);
		Vector3f newDirection = new Vector3f();
		newDirection.substractSet(secondPoint, newOrigin);
		System.err.println("newDirection: " + newDirection);
		Assert.assertEquals(newDirection, inverseRay.direction);
	}

}
