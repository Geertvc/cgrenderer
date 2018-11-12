package abstractModel;

/**
 * Class that contains general functions that can be used in other classes.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class Functions {
	
	/**
	 * Calculates the extreme x, y and z values of the given Point3f array.
	 * The calculated values will be stored in the given min and max arguments.
	 * 
	 * @param coordinates	The Point3f array to calculate the extreme values for.
	 * @param min	Point3f to store the minimum values in.
	 * @param max	Point3f to store the maximum values in.
	 */
	public static void getExtremeXYZValues(Point3f[] coordinates, Point3f min, Point3f max){
		float xMin = Float.POSITIVE_INFINITY;
		float xMax = Float.NEGATIVE_INFINITY;
		float yMin = Float.POSITIVE_INFINITY;
		float yMax = Float.NEGATIVE_INFINITY;
		float zMin = Float.POSITIVE_INFINITY;
		float zMax = Float.NEGATIVE_INFINITY;
		for (Point3f point : coordinates) {
			if(point.x > xMax){ 
				xMax = point.x;
			}
			if(point.x < xMin){
				xMin = point.x;
			}
			if(point.y > yMax){
				yMax = point.y;
			}
			if(point.y < yMin){
				yMin = point.y;
			}
			if(point.z > zMax){
				zMax = point.z;
			}
			if(point.z < zMin){
				zMin = point.z;
			}
		}
		min.set(xMin, yMin, zMin);
		max.set(xMax, yMax, zMax);
	}
	
	/**
	 * Returns the incenter of the triangle that has the given points as vertices.
	 * The incenter is the center of the triangle's incircle - the largest circle that fits inside the triangle.
	 * The incenter is also the point where the angle bisectors of the triangle intersect. 
	 *  
	 * @param a	The first point of the triangle.
	 * @param b The second point of the triangle.
	 * @param c The third point of the triangle.
	 * @return	Point3f
	 * 		The incenter of the triangle.
	 */
	public static Point3f getIncenterOfTriangle(Point3f a, Point3f b, Point3f c){
		float lengthA = new Vector3f(b.x-c.x, b.y-c.y, b.z-c.z).length();
		float lengthB = new Vector3f(a.x-c.x, a.y-c.y, a.z-c.z).length();
		float lengthC = new Vector3f(a.x-b.x, a.y-b.y, a.z-b.z).length();
		float contour = lengthA+lengthB+lengthC;
		float x = ((lengthA*a.x)+(lengthB*b.x)+(lengthC*c.x))/(contour);
		float y = ((lengthA*a.y)+(lengthB*b.y)+(lengthC*c.y))/(contour);
		float z = ((lengthA*a.z)+(lengthB*b.z)+(lengthC*c.z))/(contour);
		return new Point3f(x,y,z);
	}
	
	private static float getLengthAOfTriangle(Point3f a, Point3f b, Point3f c){
		return new Vector3f(b.x-c.x, b.y-c.y, b.z-c.z).length();
	}
	
	private static float getLengthBOfTriangle(Point3f a, Point3f b, Point3f c){
		return new Vector3f(a.x-c.x, a.y-c.y, a.z-c.z).length();
	}
	
	private static float getLengthCOfTriangle(Point3f a, Point3f b, Point3f c){
		return new Vector3f(a.x-b.x, a.y-b.y, a.z-b.z).length();
	}
	
	/**
	 * Returns the contour of the triangle that has the given points as vertices.
	 * 
	 * @param a	The first point of the triangle.
	 * @param b The second point of the triangle.
	 * @param c The third point of the triangle.
	 * @return	float
	 * 		The contour of the triangle.
	 */
	public static float getContourOfTriangle(Point3f a, Point3f b, Point3f c){
		return getLengthAOfTriangle(a,b,c) + getLengthBOfTriangle(a, b, c)+ getLengthCOfTriangle(a, b, c);
	}

	/**
	 * Returns the x-coordinate of the incenter of the triangle that has the given points as vertices.
	 * The incenter is the center of the triangle's incircle - the largest circle that fits inside the triangle.
	 * The incenter is also the point where the angle bisectors of the triangle intersect. 
	 *  
	 * @param a	The first point of the triangle.
	 * @param b The second point of the triangle.
	 * @param c The third point of the triangle.
	 * @return	float
	 * 		The x-coordinate of the incenter of the triangle.
	 */
	public static float getXCoordOfIncenterOfTriangle(Point3f a, Point3f b, Point3f c){
		float lengthA = getLengthAOfTriangle(a, b, c);
		float lengthB = getLengthBOfTriangle(a, b, c);
		float lengthC = getLengthCOfTriangle(a, b, c);
		float contour = lengthA+lengthB+lengthC;
		return ((lengthA*a.x)+(lengthB*b.x)+(lengthC*c.x))/(contour);
	}
	
	/**
	 * Returns the y-coordinate of the incenter of the triangle that has the given points as vertices.
	 * The incenter is the center of the triangle's incircle - the largest circle that fits inside the triangle.
	 * The incenter is also the point where the angle bisectors of the triangle intersect. 
	 *  
	 * @param a	The first point of the triangle.
	 * @param b The second point of the triangle.
	 * @param c The third point of the triangle.
	 * @return	float
	 * 		The y-coordinate of the incenter of the triangle.
	 */
	public static float getYCoordOfIncenterOfTriangle(Point3f a, Point3f b, Point3f c){
		float lengthA = getLengthAOfTriangle(a, b, c);
		float lengthB = getLengthBOfTriangle(a, b, c);
		float lengthC = getLengthCOfTriangle(a, b, c);
		float contour = lengthA+lengthB+lengthC;
		return ((lengthA*a.y)+(lengthB*b.y)+(lengthC*c.y))/(contour);
	}
	
	/**
	 * Returns the z-coordinate of the incenter of the triangle that has the given points as vertices.
	 * The incenter is the center of the triangle's incircle - the largest circle that fits inside the triangle.
	 * The incenter is also the point where the angle bisectors of the triangle intersect. 
	 *  
	 * @param a	The first point of the triangle.
	 * @param b The second point of the triangle.
	 * @param c The third point of the triangle.
	 * @return	float
	 * 		The z-coordinate of the incenter of the triangle.
	 */
	public static float getZCoordOfIncenterOfTriangle(Point3f a, Point3f b, Point3f c){
		float lengthA = getLengthAOfTriangle(a, b, c);
		float lengthB = getLengthBOfTriangle(a, b, c);
		float lengthC = getLengthCOfTriangle(a, b, c);
		float contour = lengthA+lengthB+lengthC;
		return ((lengthA*a.z)+(lengthB*b.z)+(lengthC*c.z))/(contour);
	}
}
