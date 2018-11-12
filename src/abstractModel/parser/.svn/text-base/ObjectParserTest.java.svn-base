package abstractModel.parser;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import abstractModel.Point3f;
import abstractModel.TexCoord2f;
import abstractModel.Vector3f;

/**
 * JUnit test case to test abstractModel.parser.ObjectParser.
 * 
 * @author Geert Van Campenhout
 */
public class ObjectParserTest {

	ObjectParser objparser;
	
	@Before
	public void setUp() throws Exception {
		objparser = new ObjectParser("test");
	}

	/**
	 * Test of the parseLine(String) method.
	 */
	@Test
	public void parseLine_test() {
		String a = "v -1.000000 -0.000000 1.000000";
		objparser.parseLine(a);
		Assert.assertEquals(new Point3f(-1,-0f,1), objparser.coordinates.get(0));
		String b = "vt 1.000000 0.000000";
		objparser.parseLine(b);
		Assert.assertEquals(new TexCoord2f(1,0), objparser.textureCoordinates.get(0));
		String c = "vn 0.000000 1.000000 0.000000";
		objparser.parseLine(c);
		Assert.assertEquals(new Vector3f(0,1,0), objparser.normals.get(0));
	}
	
	/**
	 * Test of the parseLine(String) method for a full plane line (line that starts with f).
	 */
	@Test
	public void parseLinePlane_test(){
		String d = "f 1/1/1 2/2/2 3/3/3";
		objparser.parseLine(d);
		//Not 1,2 and 3 because they are renumbered to numbers that start at 0.
		Assert.assertEquals(new Integer(0), objparser.coordinateIndices.get(0));
		Assert.assertEquals(new Integer(1), objparser.coordinateIndices.get(1));
		Assert.assertEquals(new Integer(2), objparser.coordinateIndices.get(2));
		
		Assert.assertEquals(new Integer(0), objparser.textureCoordinateIndices.get(0));
		Assert.assertEquals(new Integer(1), objparser.textureCoordinateIndices.get(1));
		Assert.assertEquals(new Integer(2), objparser.textureCoordinateIndices.get(2));
		
		Assert.assertEquals(new Integer(0), objparser.normalIndices.get(0));
		Assert.assertEquals(new Integer(1), objparser.normalIndices.get(1));
		Assert.assertEquals(new Integer(2), objparser.normalIndices.get(2));
	}
	
	/**
	 * Test of the parseLine(String) method for a plane line with only coordinateIndices.
	 */
	@Test
	public void parseLinePlaneHalfInput1_test(){
		String d = "f 1 2 3";
		objparser.parseLine(d);
		Assert.assertEquals(new Integer(0), objparser.coordinateIndices.get(0));
		Assert.assertEquals(new Integer(1), objparser.coordinateIndices.get(1));
		Assert.assertEquals(new Integer(2), objparser.coordinateIndices.get(2));
		try{
			Assert.assertEquals(new Integer(0), objparser.textureCoordinateIndices.get(0));
			Assert.fail();
		} catch(IndexOutOfBoundsException e){}
		//Hier niet verder dan get(0) testen omdat als 0 een exception geeft 1 en 2 dat natuurlijk ook gaan doen altijd.
		try{
			Assert.assertEquals(new Integer(0), objparser.normalIndices.get(0));
			Assert.fail();
		} catch(IndexOutOfBoundsException e){}
	}

	/**
	 * Test of the parseLine(String) method for a plane line with only coordinateIndices and textureCoordinateIndices.
	 */
	@Test
	public void parseLinePlaneHalfInput2_test(){
		String d = "f 1/1 2/2 3/3";
		objparser.parseLine(d);
		Assert.assertEquals(new Integer(0), objparser.coordinateIndices.get(0));
		Assert.assertEquals(new Integer(1), objparser.coordinateIndices.get(1));
		Assert.assertEquals(new Integer(2), objparser.coordinateIndices.get(2));
		Assert.assertEquals(new Integer(0), objparser.textureCoordinateIndices.get(0));
		Assert.assertEquals(new Integer(1), objparser.textureCoordinateIndices.get(1));
		Assert.assertEquals(new Integer(2), objparser.textureCoordinateIndices.get(2));
		try{
			Assert.assertEquals(new Integer(0), objparser.normalIndices.get(0));
			Assert.fail();
		} catch(IndexOutOfBoundsException e){}
	}
	
	/**
	 * Test of the parseLine(String) method for a plane line with only coordinateIndices and normalIndices.
	 */
	@Test
	public void parseLinePlaneHalfInput3_test(){
		String d = "f 1//1 2//2 3//3";
		objparser.parseLine(d);
		Assert.assertEquals(new Integer(0), objparser.coordinateIndices.get(0));
		Assert.assertEquals(new Integer(1), objparser.coordinateIndices.get(1));
		Assert.assertEquals(new Integer(2), objparser.coordinateIndices.get(2));
		try{
			Assert.assertEquals(new Integer(0), objparser.textureCoordinateIndices.get(0));
			Assert.fail();
		} catch(IndexOutOfBoundsException e){}
		Assert.assertEquals(new Integer(0), objparser.normalIndices.get(0));
		Assert.assertEquals(new Integer(1), objparser.normalIndices.get(1));
		Assert.assertEquals(new Integer(2), objparser.normalIndices.get(2));
	}

}
