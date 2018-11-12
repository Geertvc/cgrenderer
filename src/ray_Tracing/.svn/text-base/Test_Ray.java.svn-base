package ray_Tracing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import abstractModel.Point3f;
import abstractModel.Vector3f;

/**
 * jUnit test case to test the ray class.
 * 
 * @author Geert Van Campenhout
 */
public class Test_Ray {
	Point3f origin;
	Point3f imagePoint;
	Vector3f direction;
	
	@Before
	public void setUp() throws Exception {
		origin = new Point3f();
		imagePoint = new Point3f(1.0f,1.0f,1.0f);
		direction = new Vector3f(1.0f,1.0f,1.0f);
	}

	/**
	 * Test: constructors of Ray.
	 */
	@Test
	public void test() {
		Ray ray1 = new Ray(origin,direction);
		Ray ray2 = new Ray(origin,imagePoint);
		Assert.assertEquals(ray1, ray2);
	}

}
