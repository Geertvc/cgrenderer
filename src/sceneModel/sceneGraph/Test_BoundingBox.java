package sceneModel.sceneGraph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ray_Tracing.Ray;
import abstractModel.Point3f;

/**
 * jUnit test case to test the BoundingBox class.
 * 
 * @author Geert Van Campenhout
 */
public class Test_BoundingBox {
	
	BoundingBox unityCube;
	Ray xRay;
	Ray yRay;
	Ray zRay;
	Ray xyzRay;
	Ray onePointHitRay;
	Ray ray;

	@Before
	public void setUp() throws Exception {
		unityCube = new BoundingBox("Test", 0,1,0,1,0,1);
		xRay = new Ray(new Point3f(-1,0,0), new Point3f(2,0,0));
		yRay = new Ray(new Point3f(0,-1,0), new Point3f(0,2,0));
		zRay = new Ray(new Point3f(0,0,-1), new Point3f(0,0,2));
		onePointHitRay = new Ray(new Point3f(-1,-1,0), new Point3f(0,0,1));
		xyzRay = new Ray(new Point3f(), new Point3f(1,1,1));
		
		ray = new Ray(new Point3f(-1,-1,1), new Point3f(1,1,2));
	}

	/** Test of the hitBox method */
	@Test
	public void test_hitBox_unityCube() {
		assertTrue(unityCube.hitBox(xyzRay, 0, Float.MAX_VALUE));
		assertTrue(unityCube.hitBox(xRay, 0, Float.MAX_VALUE));
		assertTrue(unityCube.hitBox(yRay, 0, Float.MAX_VALUE));
		assertTrue(unityCube.hitBox(zRay, 0, Float.MAX_VALUE));
		assertTrue(unityCube.hitBox(onePointHitRay, 0, Float.MAX_VALUE));
		assertFalse(unityCube.hitBox(ray, 0, Float.MAX_VALUE));
		ray = new Ray(new Point3f(0.5f,-1,0), new Point3f(0.5f,0.5f,2));
		assertFalse(unityCube.hitBox(ray, 0, Float.MAX_VALUE));
	}

}
