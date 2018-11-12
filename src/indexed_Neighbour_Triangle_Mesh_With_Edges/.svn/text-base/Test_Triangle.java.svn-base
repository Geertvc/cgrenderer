package indexed_Neighbour_Triangle_Mesh_With_Edges;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import abstractModel.Vector3f;

/**
 * jUnit test case to test Triangle
 * 
 * @author Geert Van Campenhout
 */
public class Test_Triangle {
	
	Vector3f a;
	Vector3f b;
	Vector3f c;

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test: getArea with simple input.
	 */
	@Test
	public void test_getAreaSimple() {
		a = new Vector3f(0, 0, 0);
		b = new Vector3f(1, 0, 0);
		c = new Vector3f(0, 1, 0);
		Assert.assertEquals(0.5, Triangle.getArea(a, b, c), 0.00001);
	}
	
	/**
	 * Test: getArea with simple 3D input.
	 */
	@Test
	public void test_getArea3DSimple() {
		a = new Vector3f(0, 0, 0);
		b = new Vector3f(0, 1, 0);
		c = new Vector3f(1, 0, 1);
		Assert.assertEquals(Math.sqrt(2)/2, Triangle.getArea(a, b, c), 0.00001);
	}
	
	/**
	 * Test: getArea with harder 3D input.
	 */
	@Test
	public void test_getArea3DFull() {
		a = new Vector3f(-1.5f, 4, -1);
		b = new Vector3f(6, 2, 0.5f);
		c = new Vector3f(3, -2, 5);
		Assert.assertEquals(26.306189, Triangle.getArea(a, b, c), 0.00001);
	}
	
	/**
	 * Test: getArea with vertices that are not given in counter clockwise order.
	 */
	@Test
	public void test_getAreaVerticesNotCounterClockwise() {
		a = new Vector3f(-1.5f, 4, -1);
		b = new Vector3f(6, 2, 0.5f);
		c = new Vector3f(3, -2, 5);
		//The area should stay unchanged if the order of the vertices is changed.
		Assert.assertEquals(26.306189, Triangle.getArea(a, b, c), 0.00001);
		Assert.assertEquals(26.306189, Triangle.getArea(a, c, b), 0.00001);
		Assert.assertEquals(26.306189, Triangle.getArea(b, a, c), 0.00001);
		Assert.assertEquals(26.306189, Triangle.getArea(b, c, a), 0.00001);
		Assert.assertEquals(26.306189, Triangle.getArea(c, a, b), 0.00001);
		Assert.assertEquals(26.306189, Triangle.getArea(c, b, a), 0.00001);
	}
	
	/**
	 * Test: getArea with all vertices Aligned.
	 */
	@Test
	public void test_getAreaAllVerticesAligned(){
		a = new Vector3f(-1.5f, 4, -1);
		b = new Vector3f(0.75f, 1, 2);
		c = new Vector3f(3, -2, 5);
		Assert.assertEquals(0, Triangle.getArea(a, b, c), 0.00001);
	}

}
