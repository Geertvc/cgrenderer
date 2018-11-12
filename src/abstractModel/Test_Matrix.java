package abstractModel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * jUnit test case to test the Matrix4f new implemented methods.
 * 
 * @author Geert Van Campenhout
 */
public class Test_Matrix {

	Matrix4f matrix1;
	Matrix4f matrix2;
	Matrix4f matrix3;
	Matrix4f identity;
	
	@Before
	public void setUp() throws Exception {
		matrix1 = new Matrix4f(	1,2,3,4,
								5,6,7,8,
								9,10,11,12,
								13,14,15,16);
		matrix2 = new Matrix4f();
		matrix3 = new Matrix4f();
		identity = new Matrix4f();
		identity.setIdentity();
	}

	/** Test of the MultiplyRight method. */
	@Test
	public void test_Matrix4fMultiplyRight() {
		Assert.assertEquals(matrix1, identity.multiplyRight(matrix1));
		matrix2 = matrix1.scaleReturn(-1);
		Assert.assertEquals(new Matrix4f(	
				-90,-100,-110,-120,
				-202,-228,-254,-280,
				-314,-356,-398,-440,
				-426,-484,-542,-600), 
				matrix1.multiplyRight(matrix2));
	}
	
	/** Test of the MultiplyRight method with two arguments. */
	@Test
	public void test_Matrix4fMultiplyRightTwoArguments() {
		matrix2 = new Matrix4f(
				6, 5, 0, 0,
				0, 0, 1, 0,
				5, -6, 0, 0,
				0, 0, 0, 1);
		matrix3 = new Matrix4f(	
				0,0,1,0,
				1,0,0,0,
				0,1,0,0,
				0,0,0,1);
		
		Matrix4f matrix4 = new Matrix4f();
		matrix4.multiplyRight(identity, matrix1);
		Assert.assertEquals(matrix1, matrix4);
		matrix4.multiplyRight(matrix2, matrix3);
		Assert.assertEquals(new Matrix4f(	
				5, 0, 6, 0,
				0, 1, 0, 0,
				-6, 0, 5, 0,
				0, 0, 0, 1), 
				matrix4);
	}
	
	/** Test of the Transpose method. */
	@Test
	public void test_Matrix4fTranspose(){
		matrix2 = matrix1.transpose();
		Assert.assertEquals(new Matrix4f(	1,5,9,13,
										2,6,10,14,
										3,7,11,15,
										4,8,12,16), matrix2);
	}
	
	/** Test of the Invert method with a Singular matrix. */
	@Test
	public void test_Matrix4fSingularInverse(){
		matrix2 = new Matrix4f(	1,0,1,0,
								0,1,0,0,
								1,0,1,0,
								0,0,0,1);
		try{
			matrix3.invert(matrix2);
			Assert.fail();
		} catch(SingularMatrixException exc){
		}
	}

	/** Test of the Invert method. */
	@Test
	public void test_Matrix4fInverse(){
		matrix2 = new Matrix4f(	1,3,1,2,
								1,1,2,4,
								2,3,4,0,
								1,5,2,3);
		matrix3.invert(matrix2);
		Matrix4f matrix4 = matrix2.multiplyRight(matrix3);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Assert.assertEquals(identity.getElement(i, j), matrix4.getElement(i, j), 0.0001f);
			}
		}
	}
	
	/** Test of the locationMult method. */
	@Test
	public void test_locationMult(){
		Point3f location = new Point3f(1, 2, 3);
		matrix2 = new Matrix4f(	1,3,1,2,
								1,1,2,4,
								2,3,4,0,
								0,0,0,1);
		Assert.assertEquals(new Point3f(12,13,20), matrix2.locationMult(location));
	}
	
	/** Test of the directionMult method. */
	@Test
	public void test_directionMult(){
		Vector3f direction = new Vector3f(1, 2, 3);
		matrix2 = new Matrix4f(	1,3,1,2,
								1,1,2,4,
								2,3,4,0,
								0,0,0,1);
		Assert.assertEquals(new Vector3f(10,9,20), matrix2.directionMult(direction));
	}
	
	/** Test of the vectorMult method. */
	@Test
	public void test_vectormult(){
		Point4f direction = new Point4f(1, 2, 3, 4);
		matrix2 = new Matrix4f(	1,3,1,2,
								1,1,2,4,
								2,3,4,0,
								5,2,0,1);
		Assert.assertEquals(new Point4f(18,25,20,13), matrix2.vectorMult(direction));
	}

}
