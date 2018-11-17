package sceneModel;

import ray_Tracing.GlobalRayTracer;
import ray_Tracing.Ray;
import ray_Tracing.RayTracer;
import abstractModel.Color3f;
import abstractModel.IntersectionRecord;
import abstractModel.Point3f;
import abstractModel.Scene;
import abstractModel.Vector3f;
import main.Constants;

public class GlobalMaterial extends Material {
	
	private Color3f ambientColor;
	private Color3f spectralColor;
	private float phongExponent;
	private float reflectionCoefficient;
	
	private Color3f downScaledAmbientColor;

	public GlobalMaterial(String name){
		this.name = name;
	}
	
	public GlobalMaterial(String name, Color3f ambientColor, Color3f diffuseColor, Color3f spectralColor, float phongExponent, float reflectionCoefficient) {
		this(name);
		this.setAmbientColor(ambientColor);
		this.color = diffuseColor;
		this.setSpectralColor(spectralColor);
		this.setPhongExponent(phongExponent);
		this.setReflectionCoefficient(reflectionCoefficient);
		this.downScaledAmbientColor = new Color3f(ambientColor.x/Constants.SOFT_SHADOWS, ambientColor.y/Constants.SOFT_SHADOWS, ambientColor.z/Constants.SOFT_SHADOWS);
	}
	
	public Color3f shade(Vector3f surfaceNormal, Point3f intersectionPoint,
			PointLight pointLight, Point3f cameraPosition, Vector3f viewDirection, Scene scene, int depth) {
		Color3f totalColor = new Color3f();
		Color3f diffuseColor = diffuseShading(surfaceNormal, intersectionPoint, pointLight, cameraPosition);
		Color3f phongColor = phongShading(surfaceNormal, intersectionPoint, pointLight, cameraPosition);
		
		totalColor.add(diffuseColor);
		totalColor.add(phongColor);
		totalColor.scale(1-reflectionCoefficient);
		
		if(viewDirection != null) {
			Color3f reflectedColor = reflectionShading(surfaceNormal, intersectionPoint, viewDirection, scene, depth);
			reflectedColor.scale(reflectionCoefficient);
			totalColor.add(reflectedColor);
		}
//		totalColor.add(ambientColor);
		return totalColor;
	}
	
	private Color3f diffuseShading(Vector3f surfaceNormal, Point3f intersectionPoint, PointLight pointLight, Point3f cameraPosition){
		Vector3f lightVector = Vector3f.getNormalizedVectorBetween(pointLight.position, intersectionPoint);
		float value = pointLight.intensity*Math.max(0, surfaceNormal.dotProduct(lightVector));
		Color3f shadeColor = new Color3f(
				pointLight.color.x*this.color.x,
				pointLight.color.y*this.color.y,
				pointLight.color.z*this.color.z);
		shadeColor.scale(value);
		return shadeColor;
	}
	
	private Color3f phongShading(Vector3f surfaceNormal, Point3f intersectionPoint,
			PointLight pointLight, Point3f cameraPosition) {
		Vector3f l = Vector3f.getNormalizedVectorBetween(pointLight.position, intersectionPoint);
		Vector3f viewVector = Vector3f.getNormalizedVectorBetween(cameraPosition, intersectionPoint);
		Color3f specularComponent = new Color3f();
		Vector3f h = new Vector3f();
		h.addSet(l, viewVector);
		h.normalize();
		float value = (float) Math.pow(Math.max(0, surfaceNormal.dotProduct(h)), this.phongExponent);
		specularComponent.scaleSet(value*(pointLight.intensity), this.spectralColor);
		return specularComponent;
	}
	
	private Color3f reflectionShading(Vector3f surfaceNormal, Point3f intersectionPoint, Vector3f viewDirection, Scene scene, int depth){
		if(reflectionCoefficient == 0) {
			return new Color3f();
		}
		Vector3f twodnn = new Vector3f();
		twodnn.scaleSet(2*(viewDirection.dotProduct(surfaceNormal)), surfaceNormal);
		Vector3f reflectionDirection = new Vector3f();
		reflectionDirection.substractSet(viewDirection, twodnn);
		Ray reflectionRay = new Ray(intersectionPoint, reflectionDirection);
		IntersectionRecord intersect = scene.hit(reflectionRay, 0.001f, Float.MAX_VALUE);
		
		Color3f reflectionColor = new Color3f();
		if(intersect != null){
			//Real reflection, we have hit another object with the reflection ray
			//Note that we add a new depth of reflection here so that we would not go on forever.
			GlobalRayTracer.materialShading(scene,intersect,reflectionColor, depth+1);
		} else{
			//No object was hit by the reflection ray - background color is used.
			reflectionColor = RayTracer.calculateBackGroundColor(scene, reflectionRay);
		}
		return reflectionColor;
	}
	
//	public Color3f shadeInShadow() {
//		return ambientColor;
//		return downScaledAmbientColor;
//	}

	public String getName() {
		return name;
	}
	
	public void setColor(Color3f color) {
		this.color = color;
	}
	
	public Color3f getAmbientColor() {
		return ambientColor;
	}

	public void setAmbientColor(Color3f ambientColor) {
		this.ambientColor = ambientColor;
	}

	public Color3f getSpectralColor() {
		return spectralColor;
	}

	public void setSpectralColor(Color3f spectralColor) {
		this.spectralColor = spectralColor;
	}

	public float getPhongExponent() {
		return phongExponent;
	}

	public void setPhongExponent(float phongExponent) {
		this.phongExponent = phongExponent;
	}

	public float getReflectionCoefficient() {
		return reflectionCoefficient;
	}

	public void setReflectionCoefficient(float reflectionCoefficient) {
		this.reflectionCoefficient = reflectionCoefficient;
	}
}
