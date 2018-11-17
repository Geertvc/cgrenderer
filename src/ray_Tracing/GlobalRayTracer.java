package ray_Tracing;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Triangle;
import main.Constants;
import sceneModel.GlobalMaterial;
import sceneModel.Material;
import sceneModel.PointLight;
import sceneModel.sceneGraph.LeafNode;
import abstractModel.Color3f;
import abstractModel.IntersectionRecord;
import abstractModel.Matrix4f;
import abstractModel.Point3f;
import abstractModel.Scene;
import abstractModel.Vector3f;

public class GlobalRayTracer extends RayTracer {

	public GlobalRayTracer(MainMultiThreaded main, Scene scene) {
		super(main, scene);
	}
	
	@Override
	public void materialShading(IntersectionRecord intersectionRecord,
			Color3f totalColor) {
		GlobalRayTracer.materialShading(scene, intersectionRecord, totalColor,0);
	}
	
	/**
	 * Calculates whether the given intersectionPoint must be shaded or overshadowed.
	 * 
	 * @param material	The material to shade.
	 * @param normal	The normal at the intersectionPoint.
	 * @param intersectionPoint	The intersectionPoint to shade at.
	 * @param pointLight	The pointLight as only lightsource in this calculation.
	 * @return	Color3f
	 * 		The shaded value.
	 */
	@SuppressWarnings("unused")
	protected static Color3f globalmaterialShadowShading(Scene scene, Material material, Vector3f normal,
			Point3f intersectionPoint, PointLight pointLight, int depth) {
		final float lightSize = 1f;
		Color3f colorToAdd = null;
		if(Constants.SOFT_SHADOWS >0){
			PointLight softPointLight = new PointLight(pointLight.position, pointLight.intensity/*/Constants.SOFT_SHADOWS*/, pointLight.color, "softPointLight");
			Color3f shadowColor = new Color3f();
			for (int i = 0; i < Constants.SOFT_SHADOWS; i++) {
				Vector3f shadowDirection = new Vector3f();
				Ray shadowRay = getSoftShadowRay(intersectionPoint, lightSize,
						softPointLight, shadowDirection);
				shadowColor.add(getGlobalMaterialShadeColor(scene, material, normal,
						intersectionPoint, softPointLight, shadowRay, depth));
			}
			shadowColor.scale((float) 1/Constants.SOFT_SHADOWS);
			colorToAdd = shadowColor;
		} else{
			Vector3f shadowDirection = new Vector3f();
			shadowDirection.substractSet(pointLight.position, intersectionPoint);
			Ray shadowRay = new Ray(intersectionPoint, shadowDirection);
			colorToAdd = getGlobalMaterialShadeColor(scene, material, normal,
					intersectionPoint, pointLight, shadowRay, depth);
		}
		return colorToAdd;
	}
	
	/**
	 * The shade color to shade the intersectionPoint with based on the given arguments.
	 * 
	 * @param material	The material to shade with.
	 * @param normal	The normal at the intersectionPoint.
	 * @param intersectionPoint	The intersectionPoint.
	 * @param pointLight	The PointLight used for this shading.
	 * @param shadowRay	The shadowRay to decide whether the intersectionPoint is in shadow or not.
	 * @return	Color3f
	 * 		The material shade color.
	 */
	protected static Color3f getGlobalMaterialShadeColor(Scene scene, Material material, Vector3f normal,
			Point3f intersectionPoint, PointLight pointLight, Ray shadowRay, int depth) {
		Color3f shadeColor;
		if(scene.hitClosest(shadowRay, 0.001f, Float.MAX_VALUE) != null){
			if(depth == 0) {
				shadeColor = material.shadeInShadow();
			} else {
				shadeColor = new Color3f();
			}
		} else{
//			TODO does the viewDirection need to be not null here?
			
			//calculate viewdirection
			Vector3f viewDirection = new Vector3f();
			Point3f activeCamera = scene.getActiveCamera().position;
			viewDirection.substractSet(intersectionPoint, activeCamera);
			shadeColor = material.shade(normal, intersectionPoint, pointLight, scene.getActiveCamera().position, viewDirection, scene, depth);
//			shadeColor = material.shadeInShadow();
		}
		return shadeColor;
	}
	
	public static void materialShading(Scene scene, IntersectionRecord intersectionRecord,
			Color3f totalColor, int depth) {
		if(depth > Constants.REFLECTIONDEPTH) {
			return;
		}
		//Use normal shading on the geometry
		String hitGeometryName = intersectionRecord.geometryName;
		LeafNode hitLeafNode = scene.getLeafNode(hitGeometryName);
		String materialName = hitLeafNode.getMaterialName();
		Material globalMaterial = scene.getMaterials().get(materialName);
		//Find normal
		Vector3f normal = Triangle.getInterpolatedNormalizedNormal(intersectionRecord.a, intersectionRecord.b, intersectionRecord.c, new Point3f(intersectionRecord.intersectionPoint));
		LeafNode leaf = scene.getLeafNode(intersectionRecord.geometryName);
		
		//Transform the normal 
		Matrix4f transformMatrix = leaf.getTotalTransformMatrix();
		Matrix4f invertedTransformMatrix = new Matrix4f();
		invertedTransformMatrix.invert(transformMatrix);
		Matrix4f invertedTransposeTransformMatrix = invertedTransformMatrix.transpose();
		normal = new Vector3f(invertedTransposeTransformMatrix.directionMult(normal));
		
		normal.scale(1/normal.length());
		//Get transformed intersectionPoint
		Point3f intersectionPoint = new Point3f(transformMatrix.locationMult(intersectionRecord.intersectionPoint));
		String[] activeLights = scene.getActiveLights();
		for (int i = 0; i < activeLights.length; i++) {
			PointLight pointLight = scene.getPointLights().get(activeLights[i]);
			Color3f colorToAdd;
			if(Constants.SHADOW){
				colorToAdd = globalmaterialShadowShading(scene, globalMaterial, normal,
						intersectionPoint, pointLight, depth);
			} else{
				//calculate viewdirection
				Vector3f viewDirection = new Vector3f();
				Point3f activeCamera = scene.getActiveCamera().position;
				viewDirection.substractSet(intersectionPoint, activeCamera);
				colorToAdd = globalMaterial.shade(normal, intersectionPoint, pointLight, activeCamera, viewDirection, scene, depth);
			}
			totalColor.add(colorToAdd);
		}
	}

}
