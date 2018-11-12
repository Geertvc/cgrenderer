package ray_Tracing;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import abstractModel.Point2f;
import abstractModel.Point3f;
import abstractModel.Vector3f;

public class Test_Ray_Generation {

	Ray_Generation rayGen;
	
	@Before
	public void setUp() throws Exception {
		//Make dummy Ray_Generation
		rayGen = new Ray_Generation(new Point3f(), 1, 1, 1, 1, 1, 1, 1);
	}

	@Test
	public void test_calculateFocusPoint() {
		Vector3f wAxis = new Vector3f(0,0,1);
		Ray ray = new Ray(new Point3f(0,0,0), new Point3f(0,-1,-1));
		float distanceToLens = 1;
//		Vector3f negWAxis = new Vector3f(0,0,1);
//		System.out.println(ray.direction);
//		System.out.println(Math.toDegrees(negWAxis.angle(ray.direction)));
		Assert.assertEquals(new Point3f(0,-1,-1), rayGen.calculateFocusPoint(ray, wAxis, distanceToLens));
		ray = new Ray(new Point3f(0,0,15), new Point3f(0,0,-1));
		distanceToLens = 1;
//		Vector3f negWAxis = new Vector3f(0,0,1);
//		System.out.println(ray.direction);
//		System.out.println(Math.toDegrees(negWAxis.angle(ray.direction)));
		Assert.assertEquals(new Point3f(0,0,-1), rayGen.calculateFocusPoint(ray, wAxis, distanceToLens));
		
		Vector3f rayDirection = new Vector3f();
		float distanceToScreen = 1;
		Vector3f uAxis = new Vector3f(0,1,0);
		Vector3f vAxis = new Vector3f(1,0,0);
		Point2f coord = rayGen.calcPositionInRasterImage((123+0.5f),(345+0.5f));
		rayDirection.scaleSet(-distanceToScreen, wAxis);
		rayDirection.scaleAdd(coord.x, uAxis, rayDirection);
		rayDirection.scaleAdd(coord.y, vAxis, rayDirection);
		ray = new Ray(new Point3f(0,0,15), new Vector3f(rayDirection));
		Assert.assertEquals(new Point3f(0,0,-1), rayGen.calculateFocusPoint(ray, wAxis, distanceToLens));
	}

}
