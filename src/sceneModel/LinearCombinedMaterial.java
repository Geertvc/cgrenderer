package sceneModel;

import abstractModel.Color3f;
import abstractModel.Point3f;
import abstractModel.Scene;
import abstractModel.Vector3f;

/**
 * A material that exists of 2 other materials.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class LinearCombinedMaterial extends Material{
	
	Material material1;
	float weight1;
	Material material2;
	float weight2;
	
	/**
	 * Creates a new Linear combined material existing of the given 2 materials and there weights.
	 * 
	 * @param material1	The first material to combine in this material.
	 * @param weight1	The weight of the first material in this material.
	 * @param material2	The second material to combine in this material.
	 * @param weight2	The weight of the second material in this material.
	 * @param name	The name of the new material
	 */
	public LinearCombinedMaterial(Material material1, float weight1, Material material2, float weight2, String name){
		this.name = name;
		this.material1 = material1;
		this.weight1 = weight1;
		this.material2 = material2;
		this.weight2 = weight2;
		Color3f combinedColor = material1.color;
		combinedColor.scale(weight1);
		combinedColor.scaleAdd(weight2, material2.color, combinedColor);
		this.color = combinedColor;
	}

	/* (non-Javadoc)
	 * @see sceneModel.Material#shade(abstractModel.Vector3f, abstractModel.Point3f, sceneModel.PointLight, abstractModel.Point3f)
	 */
	@Override
	public Color3f shade(Vector3f surfaceNormal, Point3f intersectionPoint,
			PointLight pointLight, Point3f cameraPosition, Vector3f viewDirection, Scene scene, int depth) {
		Color3f material1Color = material1.shade(surfaceNormal, intersectionPoint, pointLight, cameraPosition, viewDirection, scene, depth);
		Color3f material2Color = material2.shade(surfaceNormal, intersectionPoint, pointLight, cameraPosition, viewDirection, scene, depth);
		Color3f totalColor = new Color3f();
		totalColor.scaleSet(weight1, material1Color);
		totalColor.scaleAdd(weight2, material2Color, totalColor);
		return totalColor;
	}
}
