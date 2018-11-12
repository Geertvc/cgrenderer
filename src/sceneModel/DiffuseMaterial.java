package sceneModel;

import abstractModel.Color3f;
import abstractModel.Point3f;
import abstractModel.Vector3f;

/**
 * A Material with only a diffuse component.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class DiffuseMaterial extends Material {
	
	/**
	 * Creates a new DiffuseMaterial with the given diffuse color and name.
	 * 
	 * @param color	The diffuse color of the material.
	 * @param name	The name of the material.
	 */
	public DiffuseMaterial(Color3f color, String name){
		this.name = name;
		this.color = color;
	}

	/**
	 * Calculates the color at the given intersectionPoint for the given pointLight using the Lambertian shading technique.
	 * Lambertian shading technique in pseudo code: 
	 * Color = kd*I*PointLightColor*|dotProduct(normal, lightVector)|
	 * With lightVector the vector pointing from the intersectionPoint to the position of the pointLight.
	 * 
	 * @param kd	The diffuse color of the intersected material.
	 * @param normal	The surface normal.
	 * @param intersectionPoint	The intersectionPoint.
	 * @param pointLight	The pointLight as only lightSource in this calculation.
	 * @return	Color3f
	 * 		The color at the given intersectionPoint.
	 */
	@Override
	public Color3f shade(Vector3f surfaceNormal, Point3f intersectionPoint,
			PointLight pointLight, Point3f cameraPosition) {
		Vector3f lightVector = Vector3f.getNormalizedVectorBetween(pointLight.position, intersectionPoint);
		float value = pointLight.intensity*Math.max(0, surfaceNormal.dotProduct(lightVector));
		Color3f shadeColor = new Color3f(
				pointLight.color.x*this.color.x,
				pointLight.color.y*this.color.y,
				pointLight.color.z*this.color.z);
		shadeColor.scale(value);
		return shadeColor;
	}
}
