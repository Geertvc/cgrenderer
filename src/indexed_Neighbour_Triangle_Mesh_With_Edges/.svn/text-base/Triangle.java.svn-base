package indexed_Neighbour_Triangle_Mesh_With_Edges;

import ray_Tracing.Ray;
import abstractModel.Intersection;
import abstractModel.Matrix3f;
import abstractModel.Point3f;
import abstractModel.TexCoord2f;
import abstractModel.Tuple3f;
import abstractModel.Vector3f;

/**
 * Class representing a triangle in the Triangle Mesh.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class Triangle {
	//Array with indices of vertices belonging to the triangle. always length 3
	public int[] vertex;
	//Array with indices of edges belonging to the triangle. always length 3
	public int[] edgeNbr;
	
	public Vector3f normal;
	
	/** Initializes a triangle with his edges and vertices and normal.
	 * Vertices and triangleNBr both need to be of length 3.
	 * 
	 * @param vertices	Array with indices of his vertices.
	 * @param edgeNbr	Array with indices of his edges.
	 * @param normal	The normal vector perpendicular to the triangle.
	 */
	public Triangle(int[] vertex, int[] edgeNbr, Vector3f normal){
		this.vertex = vertex;
		this.edgeNbr = edgeNbr;
		this.normal = normal;
	}
	
	/**
	 * @return	The normal of the triangle.
	 */
	public Vector3f getNormal(){
		return this.normal;
	}
	
	/**
	 * Calculates the intersection between the given ray and the given triangle.
	 * 
	 * @param ray	The ray to intersect with the triangle.
	 * @param a	The first vertex of the triangle.
	 * @param b	The second vertex of the triangle.
	 * @param c The third vertex of the triangle.
	 * @param currentT	The current t value of the last intersection. (the new t value must be lower or this method returns null)
	 * @return	Intersection
	 * 		The new intersection if the intersection is closer nearby than the last intersection.
	 * 		null if there is no intersection or the intersection is farther away than the last intersection.
	 */
	public static Intersection rayTriangleIntersection(Ray ray, Vertex a, Vertex b, Vertex c, float currentT){
		float[] mult = new float[3];
		Matrix3f matrix = new Matrix3f(
				a.coord.x-b.coord.x, a.coord.x-c.coord.x, ray.direction.x,
				a.coord.y-b.coord.y, a.coord.y-c.coord.y, ray.direction.y,
				a.coord.z-b.coord.z, a.coord.z-c.coord.z, ray.direction.z);
		Vector3f rightHandSide = new Vector3f(a.coord.x-ray.origin.x, a.coord.y-ray.origin.y, a.coord.z-ray.origin.z);
		//Compute M
		float eiMinhf = (matrix.m11*matrix.m22)-(matrix.m12*matrix.m21);
		float gfMindi = (matrix.m02*matrix.m21)-(matrix.m01*matrix.m22);
		float dhMineg = (matrix.m01*matrix.m12)-(matrix.m11*matrix.m02);
		mult[0] = matrix.m00;
		mult[1] = matrix.m10; 
		mult[2] = matrix.m20;
		float M = computeSolution(mult,1f,eiMinhf,gfMindi,dhMineg);
		//Compute t
		float akMinjb = (matrix.m00*rightHandSide.y)-(rightHandSide.x*matrix.m10);
		float jcMinal = (rightHandSide.x*matrix.m20)-(matrix.m00*rightHandSide.z);
		float blMinkc = (matrix.m10*rightHandSide.z)-(rightHandSide.y*matrix.m20);
		mult[0] = matrix.m21;
		mult[1] = matrix.m11;
		mult[2] = matrix.m01;
		float t = computeSolution(mult,-M,akMinjb,jcMinal,blMinkc);
		if(((t<0) || (t>currentT)) && t != Float.NaN)
			return null;
		//Compute gamma
		mult[0] = matrix.m22;
		mult[1] = matrix.m12;
		mult[2] = matrix.m02;
		float gamma = computeSolution(mult,M,akMinjb,jcMinal,blMinkc);
		if((gamma<0)||(gamma>1))
			return null;
		//Compute beta
		mult[0] = rightHandSide.x;
		mult[1] = rightHandSide.y;
		mult[2] = rightHandSide.z;
		float beta = computeSolution(mult,M,eiMinhf,gfMindi,dhMineg);
		if((beta<0)||(beta>(1-gamma)))
			return null;
		Point3f intersectionPoint = new Point3f(a.coord);
		Vector3f bMinA = new Vector3f(b.coord.x-a.coord.x, b.coord.y-a.coord.y, b.coord.z-a.coord.z);
		intersectionPoint.scaleAdd(beta, bMinA, intersectionPoint);
		Vector3f cMinA = new Vector3f(c.coord.x-a.coord.x, c.coord.y-a.coord.y, c.coord.z-a.coord.z);
		intersectionPoint.scaleAdd(gamma, cMinA, intersectionPoint);
		return new Intersection(intersectionPoint, t);
	}
	
	/**
	 * Intern method that computes a solution to a problem.
	 * This method prevents code duplication.
	 */
	private static float computeSolution(float[] multiplying, float divider, float first, float second, float third){
		return ((multiplying[0]*first)+
				(multiplying[1]*second)+
				(multiplying[2]*third)
				)/divider;
	}
	
	/**
	 * Returns the array of the given triangle.
	 * 
	 * @param a	The first vertex of the triangle.
	 * @param b The second vertex of the triangle.
	 * @param c The third vertex of the triangle.
	 * @return	float
	 * 		The area of the given triangle.
	 */
	public static float getArea(Tuple3f a, Tuple3f b, Tuple3f c){
		Vector3f v = new Vector3f();
		v.substractSet(b, a);
		Vector3f w = new Vector3f();
		w.substractSet(c, a);
		Vector3f crossProduct = new Vector3f();
		crossProduct.cross(v, w);
		return crossProduct.length()/2;
	}
	
	/**
	 * Returns the interpolated and normalized normal at the given point of the given triangle.
	 * 
	 * @param a	The first vertex of the triangle.
	 * @param b The second vertex of the triangle.
	 * @param c The third vertex of the triangle.
	 * @param intersectionPoint	The intersectionPoint to calculate the interpolated normal at. 
	 * @return	Vector3f
	 * 		The interpolated and normalized normal.
	 */
	public static Vector3f getInterpolatedNormalizedNormal(Vertex a, Vertex b, Vertex c, Point3f intersectionPoint){
		Vector3f bary = Triangle.getBarycentricCoordinates3DCalculatedWithAreas(a, b, c, intersectionPoint);
		
		if(!(Math.abs(bary.x - (1-bary.y-bary.z))<0.0001))
			System.err.println("intersectionPoint not in triangle!!");
		
		Vector3f normalA = new Vector3f(a.normal);
		Vector3f normalB = new Vector3f(b.normal);
		Vector3f normalC = new Vector3f(c.normal);
		
		normalA.scale(1-bary.y-bary.z);		
		normalB.scale(bary.y);
		normalC.scale(bary.z);
		Vector3f interpolatedNormal = new Vector3f();
		interpolatedNormal.addSet(normalA, normalB);
		interpolatedNormal.add(normalC);
		
		//normalize the total normal.
		interpolatedNormal.scale(1/interpolatedNormal.length());
		return interpolatedNormal;
	}
	
	/**
	 * Returns the interpolated and normalized normal at the given point of the given triangle.
	 * 
	 * @param normalA	The normal of the first vertex of the triangle.
	 * @param normalB	The normal of the second vertex of the triangle.
	 * @param normalC	The normal of the third vertex of the triangle.
	 * @param beta	The barycentric beta coordinate.
	 * @param gamma	The barycentric gamma coordinate.
	 * @return	Vector3f
	 * 		The interpolated and normalized normal.
	 */
	public static Vector3f getInterpolatedNormalizedNormal(Vector3f normalA, Vector3f normalB, Vector3f normalC, float beta, float gamma){
		normalA.scale(1-beta-gamma);		
		normalB.scale(beta);
		normalC.scale(gamma);
		Vector3f interpolatedNormal = new Vector3f();
		interpolatedNormal.addSet(normalA, normalB);
		interpolatedNormal.add(normalC);
		//normalize the total normal.
		interpolatedNormal.scale(1/interpolatedNormal.length());
		return interpolatedNormal;
	}
	
	/**
	 * Returns a vector3f with the barycentric coordinates at the intersectionPoint calculated with areas.
	 * 
	 * @param a	The first vertex of the triangle.
	 * @param b The second vertex of the triangle.
	 * @param c The third vertex of the triangle.
	 * @param intersectionPoint	The intersectionPoint.
	 * @return	Vector3f
	 * 		The barycentric coordinates calculated with triangle areas.
	 */
	public static Vector3f getBarycentricCoordinates3DCalculatedWithAreas(
			Vertex a, Vertex b, Vertex c, Point3f intersectionPoint){
		float totalArea = Triangle.getArea(a.coord, b.coord, c.coord);
		float alpha = Triangle.getArea(intersectionPoint, b.coord, c.coord)/totalArea;
		float beta = Triangle.getArea(a.coord, intersectionPoint, c.coord)/totalArea;
		float gamma = Triangle.getArea(a.coord, b.coord, intersectionPoint)/totalArea;
		return new Vector3f(alpha, beta, gamma);
	}
	
	/**
	 * Returns a vector3f with the barycentric coordinates at the intersectionPoint calculated with areas.
	 * 
	 * @param a	The first vertex of the triangle.
	 * @param b The second vertex of the triangle.
	 * @param c The third vertex of the triangle.
	 * @param intersectionPoint	The intersectionPoint.
	 * @return	Vector3f
	 * 		The barycentric coordinates calculated with triangle areas.
	 */
	public static Vector3f getBarycentricCoordinates3DCalculatedWithAreas(
			Point3f a, Point3f b, Point3f c, Point3f intersectionPoint){
		float totalArea = Triangle.getArea(a, b, c);
		float alpha = Triangle.getArea(intersectionPoint, b, c)/totalArea;
		float beta = Triangle.getArea(a, intersectionPoint, c)/totalArea;
		float gamma = Triangle.getArea(a, b, intersectionPoint)/totalArea;
		return new Vector3f(alpha, beta, gamma);
	}

	/**
	 * Calculates an intersection at which the given ray the given triangle intersects.
	 * Returns the intersection only of the t value is between t0 and t1
	 * 
	 * @param ray	The ray to intersect.
	 * @param a	The first vertex of the triangle.
	 * @param b The second vertex of the triangle.
	 * @param c The third vertex of the triangle.
	 * @param t0	The first t value of the interval.
	 * @param t1	The second t value of the interval.
	 * @return	Intersection
	 * 		The intersection between the ray and the triangle.
	 */
	public static Intersection rayTriangleIntersection(Ray ray, Vertex a,
			Vertex b, Vertex c, float t0, float t1) {
		float[] mult = new float[3];
		Matrix3f matrix = new Matrix3f(
				a.coord.x-b.coord.x, a.coord.x-c.coord.x, ray.direction.x,
				a.coord.y-b.coord.y, a.coord.y-c.coord.y, ray.direction.y,
				a.coord.z-b.coord.z, a.coord.z-c.coord.z, ray.direction.z);
		Vector3f rightHandSide = new Vector3f(a.coord.x-ray.origin.x, a.coord.y-ray.origin.y, a.coord.z-ray.origin.z);
		//Compute M
		float eiMinhf = (matrix.m11*matrix.m22)-(matrix.m12*matrix.m21);
		float gfMindi = (matrix.m02*matrix.m21)-(matrix.m01*matrix.m22);
		float dhMineg = (matrix.m01*matrix.m12)-(matrix.m11*matrix.m02);
		mult[0] = matrix.m00;
		mult[1] = matrix.m10; 
		mult[2] = matrix.m20;
		float M = computeSolution(mult,1f,eiMinhf,gfMindi,dhMineg);
		//Compute t
		float akMinjb = (matrix.m00*rightHandSide.y)-(rightHandSide.x*matrix.m10);
		float jcMinal = (rightHandSide.x*matrix.m20)-(matrix.m00*rightHandSide.z);
		float blMinkc = (matrix.m10*rightHandSide.z)-(rightHandSide.y*matrix.m20);
		mult[0] = matrix.m21;
		mult[1] = matrix.m11;
		mult[2] = matrix.m01;
		float t = computeSolution(mult,-M,akMinjb,jcMinal,blMinkc);
		if(((t<t0) || (t>t1)) && t != Float.NaN)
			return null;
		//Compute gamma
		mult[0] = matrix.m22;
		mult[1] = matrix.m12;
		mult[2] = matrix.m02;
		float gamma = computeSolution(mult,M,akMinjb,jcMinal,blMinkc);
		if((gamma<0)||(gamma>1))
			return null;
		//Compute beta
		mult[0] = rightHandSide.x;
		mult[1] = rightHandSide.y;
		mult[2] = rightHandSide.z;
		float beta = computeSolution(mult,M,eiMinhf,gfMindi,dhMineg);
		if((beta<0)||(beta>(1-gamma)))
			return null;
		Point3f intersectionPoint = Triangle.getPointAt(a.coord, b.coord, c.coord, beta, gamma);
		return new Intersection(intersectionPoint, t);
	}
	
	/**
	 * Returns the point of the triangle with the given Points at the given barycentric coordinates.
	 * 
	 * @param normalA	The normal of the first vertex of the triangle.
	 * @param normalB	The normal of the second vertex of the triangle.
	 * @param normalC	The normal of the third vertex of the triangle.
	 * @param beta	The barycentric beta coordinate.
	 * @param gamma	The barycentric gamma coordinate.
	 * @return	Point3f
	 * 		The point at the given barycentric coordinates.
	 */
	public static Point3f getPointAt(Point3f a, Point3f b, Point3f c, float beta, float gamma){
		Point3f intersectionPoint = new Point3f(a);
		Vector3f bMinA = new Vector3f();
		bMinA.substractSet(b, a);
		intersectionPoint.scaleAdd(beta, bMinA, intersectionPoint);
		Vector3f cMinA = new Vector3f();
		cMinA.substractSet(c, a);
		intersectionPoint.scaleAdd(gamma, cMinA, intersectionPoint);
		return intersectionPoint;
	}

	/**
	 * 
	 * @param normalA	The normal of the first vertex of the triangle.
	 * @param normalB	The normal of the second vertex of the triangle.
	 * @param normalC	The normal of the third vertex of the triangle.
	 * @param intersectionPoint	The intersectionPoint at which to calculate the textureCoordinate.
	 * @return	TexCoord2f
	 * 		The interpolated textureCoordinate.
	 */
	public static TexCoord2f getInterpolatedTextureCoordinates(Vertex a,
			Vertex b, Vertex c, Point3f intersectionPoint) {
		TexCoord2f aTex = a.texCoord;
		TexCoord2f bTex = b.texCoord;
		TexCoord2f cTex = c.texCoord;
		if(aTex == null || bTex == null || cTex == null)
			throw new IllegalArgumentException("There is no textureCoordinate available.");
		Vector3f bary = Triangle.getBarycentricCoordinates3DCalculatedWithAreas(a, b, c, intersectionPoint);
		if(!(Math.abs(bary.x - (1-bary.y-bary.z))<0.0001))
			System.err.println("intersectionPoint not in triangle!!");
		float u = aTex.x + (bary.y*(bTex.x-aTex.x)) + (bary.z*(cTex.x-aTex.x));
		float v = aTex.y + (bary.y*(bTex.y-aTex.y)) + (bary.z*(cTex.y-aTex.y));
		return new TexCoord2f(u, v);
	}
	
}
