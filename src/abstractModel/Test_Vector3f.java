package abstractModel;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * jUnit test case to test the new implemented methods of Vector3f.
 * 
 * @author Geert Van Campenhout
 */
public class Test_Vector3f {
	
	Vector3f v;
	
	@Before
	public void setUp() throws Exception {
	}

	/** Test of the length method. */
	@Test
	public void test_length() {
		v = new Vector3f(3.0f, 2.0f, 1.0f);
		assertEquals(Math.sqrt(14.0f), v.length(),0.000001f);
	}
	
	/** Test of the dotProduct method. */
	@Test
	public void test_dotProduct() {
		v = new Vector3f(3.0f, 2.0f, 1.0f);
		Vector3f v1 = new Vector3f(2.0f, 1.0f, 0.5f);
		assertEquals(8.5f, v.dotProduct(v1), 0.000001f);
	}
	
	/** Test of the cross method. */
	@Test
	public void test_cross() {
		v = new Vector3f();
		Vector3f v1 = new Vector3f(1.0f, 1.0f, 2.0f);
		Vector3f v2 = new Vector3f(0.0f, 1.0f, 1.0f);
		v.cross(v1,v2);
		assertEquals(new Vector3f(-1f,-1f,1f), v);
	}
	
	/** Test of the angle method. */
	@Test
	public void test_angle() {
		v = new Vector3f(0.0f,1.0f,0.0f);
		Vector3f v1 = new Vector3f(1.0f, 1.0f, 0.0f);
		assertEquals(Math.PI/4, v.angle(v1), 0.000001f);
	}
}
