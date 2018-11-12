package ray_Tracing;

import abstractModel.Matrix4f;
import abstractModel.Point3f;
import abstractModel.Vector3f;

/**
 * Class representing a general ray used for ray tracing.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class Ray {
	
	//TODO : geef elke straal een rgb waarde mee, nodig voor reflectie en refractie.
	
	public Point3f origin;
	public Vector3f direction;
	
	/**
	 * Creates a new ray that starts at the given origin and has the given direction.
	 * 
	 * @param origin	The start of the ray.
	 * @param direction	The direction of the ray.
	 */
	public Ray(Point3f origin, Vector3f direction){
		this.origin = origin;
		this.direction = direction;
	}
	
	/**
	 * Creates a new ray that starts at the given origin.
	 * The direction of the ray is calculated by substracting the given secondPoint on the ray and the origin of the ray.
	 * 
	 * @param origin	The start point of the ray.
	 * @param secondPointOnRay	One of the points of the ray (must not be the origin).
	 */
	public Ray(Point3f origin, Point3f secondPointOnRay){
		this.origin = origin;
		Vector3f dir = new Vector3f();
		dir.substractSet(secondPointOnRay, origin);
		this.direction = dir;
	}
	
	/**
	 * Inverse transforms the given ray on base of the given transformMatrix and 
	 * returns the transformed ray as a new Ray.
	 * 
	 * @param ray	The ray to transform.
	 * @param totalTransformMatrix	The totalTransformMatrix to apply inversed.
	 * @return	Ray
	 * 		The inverse transformed ray.
	 */
	public Ray inverseTransformRay(Matrix4f totalTransformMatrix) {
		Matrix4f inverseTransformMatrix = new Matrix4f();
		inverseTransformMatrix.invert(totalTransformMatrix);
		Point3f transformedOrigin = new Point3f(inverseTransformMatrix.locationMult(this.origin));
		Vector3f transformedRayDirection = new Vector3f(inverseTransformMatrix.directionMult(this.direction));
		return new Ray(transformedOrigin, transformedRayDirection);
	}
	
	@Deprecated
	public Ray transformShadowRay(Matrix4f totalTransformMatrix) {
		Matrix4f inverseTransformMatrix = new Matrix4f();
		inverseTransformMatrix.invert(totalTransformMatrix);
		Point3f transformedOrigin = new Point3f(inverseTransformMatrix.locationMult(this.origin));
		Point3f secondPointOnRay = new Point3f();
		secondPointOnRay.addSet(this.direction, this.origin);
		//Point3f transformedSecondPoint = new Point3f(inverseTransformMatrix.locationMult(secondPointOnRay));
		Vector3f transformedRayDirection = new Vector3f();
		transformedRayDirection.substractSet(secondPointOnRay, transformedOrigin);
		return new Ray(transformedOrigin, transformedRayDirection);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ray other = (Ray) obj;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		return true;
	}
	
	
}
