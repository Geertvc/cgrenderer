package abstractModel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * jUnit test case to test the Tuple2f, Tuple3f and Tuple4f and their subclasses.
 * 
 * @author Geert Van Campenhout
 */
public class Test_Tuple {

	@Before
	public void setUp() throws Exception {
	}

	/** Test of the Add method of Tuple2f. */
	@Test
	public void test_Tuple2fAdd() {
		Point2f p = new Point2f(1.0f, 1.0f);
		p.add(p);
		Point2f p2 = new Point2f(2.0f,2.0f);
		Assert.assertEquals(p2, p);
	}
	
	/** Test of the negate method of Tuple3f. */
	@Test
	public void test_Tuple3fNegate() {
		Point3f p = new Point3f(0.0f, 1.0f, 1.0f);
		p.negate(p);
		Point3f p2 = new Point3f(-0.0f,-1.0f, -1.0f);
		Assert.assertEquals(p2, p);
	}
	
	/** Test of the Add method of Tuple3f. */
	@Test
	public void test_Tuple3fAdd() {
		Point3f p = new Point3f(1.0f, 1.0f, 1.0f);
		p.add(p);
		Point3f p2 = new Point3f(2.0f,2.0f, 2.0f);
		Assert.assertEquals(p2, p);
	}
	
	/** Test of the AddSet method of Tuple3f. */
	@Test
	public void test_Tuple3fAddSet() {
		Point3f p = new Point3f();
		Point3f p1 = new Point3f(1.0f, 1.0f, 1.0f);
		Point3f p2 = new Point3f(2.0f,-2.0f, 20.0f);
		p.addSet(p1,p2);
		Assert.assertEquals(new Point3f(3.0f,-1.0f,21.0f), p);
		p1.addSet(p1, p2);
		Assert.assertEquals(new Point3f(3, -1, 21), p1);
	}
	
	/** Test of the Scale method of Tuple3f. */
	@Test
	public void test_Tuple3fScale() {
		Point3f p = new Point3f(1.0f, 1.0f, 1.0f);
		p.scale(5.0f);
		Point3f p2 = new Point3f(5.0f,5.0f, 5.0f);
		Assert.assertEquals(p2, p);
	}
	
	/** Test of the ScaleAdd method of Tuple3f. */
	@Test
	public void test_Tuple3fScaleAdd() {
		Point3f p = new Point3f(1.0f, 1.0f, 1.0f);
		p.scaleAdd(5.0f, new Point3f(0.1f,2.5f,0.2f),p);
		Point3f p2 = new Point3f(1.5f,13.5f, 2.0f);
		Assert.assertEquals(p2, p);
	}
	
	/** Test of the interpolate method of Tuple3f. */
	@Test
	public void test_Tuple3fInterpolate() {
		Point3f p = new Point3f();
		p.interpolate(new Point3f(1,2,3),new Point3f(0.5f,0.5f,0.5f),0.8f);
		Point3f p2 = new Point3f(0.9f,1.7f, 2.5f);
		Assert.assertEquals(p2, p);
	}

	/** Test of the Add method of Tuple4f. */
	@Test
	public void test_Tuple4fAdd() {
		Vector4f v = new Vector4f(1.0f, 0.5f, 2.0f, 0.0f);
		v.add(v);
		Vector4f v2 = new Vector4f(2.0f, 1.0f, 4.0f,0.0f);
		Assert.assertEquals(v2, v);
	}
}
