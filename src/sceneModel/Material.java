package sceneModel;

import abstractModel.Color3f;
import abstractModel.Point3f;
import abstractModel.Scene;
import abstractModel.Vector3f;

/**
 * An abstract class representing a general material that can be used for a SceneElement.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public abstract class Material implements SceneElement{
	
	String name;
	Color3f color;
	
	/**
	 * @return	The color of the material.
	 */
	public Color3f getColor(){
		return this.color;
	}
	
	/* (non-Javadoc)
	 * @see sceneModel.SceneElement#getName()
	 */
	@Override
	public String getName(){
		return this.name;
	}
	
	/**
	 * Shade method the shade a surface with this material.
	 * 
	 * @param surfaceNormal	The normal at the current intersectionPoint.
	 * @param intersectionPoint	The intersectionPoint to shade.
	 * @param pointLight	The pointLight to use for the shading.
	 * @param cameraPosition	The position of the camera.
	 * @return	Color3f
	 * 		The calculated shading color.
	 */
	public abstract Color3f shade(Vector3f surfaceNormal, Point3f intersectionPoint, PointLight pointLight, Point3f cameraPosition, Vector3f viewDirection, Scene scene, int depth);

	public Color3f shadeInShadow() {
		return new Color3f();
	}
}
