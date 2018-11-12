package sceneModel;

import abstractModel.Color3f;
import abstractModel.Point3f;
import abstractModel.Vector3f;

/**
 * A material only containing a specular component.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class PhongMaterial extends Material{
	
	public float shininess;
	
	/**
	 * Creates a new PhongMaterial with the given parameters.
	 * 
	 * @param specularColor	The specular color of the phongMaterial.
	 * @param shininess	The shininess factor of the PhongMaterial.
	 * @param name	The name of the PhongMaterial.
	 */
	public PhongMaterial(Color3f specularColor, float shininess, String name){
		this.name = name;
		this.color = specularColor;
		this.shininess = shininess;
	}

	/**
	 * Calculates the color at the given intersectionPoint for the given pointLight using the Phong shading technique.
	 * Phong shading technique in pseudo code: 
	 * Color = kd*I*PointLightColor*|dotProduct(normal, lightVector)| + ks*I*PointLightColor*pow(dotProduct(normal, h),phongExponent)
	 * With lightVector the vector pointing from the intersectionPoint to the position of the pointLight.
	 * With h = normalize(viewVector+lightVector)
	 * 
	 * @param kd	The diffuse color of the intersected material.
	 * @param ks	The specular color of the intersected material.
	 * @param phongExponent		The phong exponent of the intersected material.
	 * @param viewVector	The vector pointing from the intersectionPoint to the camera.
	 * @param normal	The surface normal.
	 * @param intersectionPoint		The intersectionPoint.
	 * @param pointLight	The pointLight as only lightSource in this calculation.
	 * @return	Color3f
	 * 		The color at the given intersectionPoint.
	 */
	@Override
	public Color3f shade(Vector3f surfaceNormal, Point3f intersectionPoint,
			PointLight pointLight, Point3f cameraPosition) {
		Vector3f l = Vector3f.getNormalizedVectorBetween(pointLight.position, intersectionPoint);
		Vector3f viewVector = Vector3f.getNormalizedVectorBetween(cameraPosition, intersectionPoint);
		Color3f specularComponent = new Color3f();
		Vector3f h = new Vector3f();
		h.addSet(l, viewVector);
		h.normalize();
		float diffuseValue = (float) Math.pow(Math.max(0, surfaceNormal.dotProduct(h)), this.shininess);
		specularComponent.scaleSet(diffuseValue*(pointLight.intensity), this.color);
		return specularComponent;
	}
}
