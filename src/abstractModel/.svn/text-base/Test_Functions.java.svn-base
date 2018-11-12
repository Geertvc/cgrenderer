package abstractModel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * jUnit test case to test the Functions class.
 * 
 * @author Geert Van Campenhout
 */
public class Test_Functions {
	
	Point3f[] coordinates;
	Point3f min = new Point3f();
	Point3f max = new Point3f();

	@Before
	public void setUp() throws Exception {
	}

	/** Test of the getExtremeXYZValues method. */
	@Test
	public void test_getExtremeXYZValues() {
		coordinates = new Point3f[] {
				new Point3f(-100,0.01f,10), 
				new Point3f(100,1,-10), 
				new Point3f(-10,10,-9.99f), 
				new Point3f(10,5,1.1f), 
				new Point3f(-20,3,5), 
				new Point3f(20,2.5f,8.5f), 
				new Point3f(30,1,10.1f), 
				new Point3f(-30,1,1), 
				new Point3f(0,3.0001f,5), 
				new Point3f(0,1,2)};
		Functions.getExtremeXYZValues(coordinates, min, max);
		Assert.assertEquals(new Point3f(-100,0.01f,-10), min);
		Assert.assertEquals(new Point3f(100,10,10.1f), max);
	}
	
	/** Test of the getIncenterOfTriangle method. */
	@Test
	public void test_getIncenterOfTriangle() {
		Point3f a = new Point3f(0,0,0);
		Point3f b = new Point3f(0,0,1);
		Point3f c = new Point3f(1,0,0);
		System.out.println(Functions.getIncenterOfTriangle(a, b, c));
	}

}
