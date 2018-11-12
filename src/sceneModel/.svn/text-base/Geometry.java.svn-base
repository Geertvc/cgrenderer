package sceneModel;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Vertex;

import java.util.List;

import main.Constants;
import ray_Tracing.Ray;
import sceneModel.sceneGraph.LeafNode;
import abstractModel.Color3f;
import abstractModel.IntersectionRecord;
import abstractModel.Matrix4f;
import abstractModel.Point3f;
import abstractModel.Scene;
import abstractModel.Vector3f;

/**
 * An abstract class representing a general geometry.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public abstract class Geometry implements SceneElement{
	
	String name;
	
	/**
	 * Creates a new geometry with the given name.
	 * 
	 * @param name	The name of the geometry.
	 */
	public Geometry(String name){
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see sceneModel.SceneElement#getName()
	 */
	@Override
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns the color at the specific intersection using all the lights for shading.
	 * 
	 * @param activePointLights	The lights used for shading.
	 * @param scene	The scene to shade.
	 * @param rec	The intersectionRecord with the intersection
	 * @return	Color3f
	 * 		The color at the given intersection.
	 */
	public Color3f shadeWithAllActiveLights(List<PointLight> activePointLights,Scene scene, IntersectionRecord rec){
		Color3f totalColor = new Color3f();
		LeafNode leaf = scene.getLeafNode(rec.geometryName);
		Matrix4f transformMatrix = leaf.getTotalTransformMatrix();
		Point3f intersectionPoint = new Point3f(transformMatrix.locationMult(rec.intersectionPoint));
		for (PointLight pointLight : activePointLights) {
			Color3f colorToAdd;
			if(Constants.SHADOW){
				Vector3f shadowDirection = new Vector3f();
				shadowDirection.substractSet(pointLight.position, intersectionPoint);
				Ray shadowRay = new Ray(intersectionPoint, shadowDirection);
				if(scene.hitClosest(shadowRay, 0.01f, Float.MAX_VALUE) != null){
					colorToAdd = new Color3f();
				} else{
					colorToAdd = shade(rec.a, rec.b, rec.c, rec.intersectionPoint, pointLight);
				}
			} else{
				colorToAdd = shade(rec.a, rec.b, rec.c, rec.intersectionPoint, pointLight);
			}
			totalColor.add(colorToAdd);
		}
		return totalColor;
	}
	
	/**
	 * Shades the given intersectionPoint of the given triangle(given through his vertices) for the given pointLight.
	 * 
	 * @param a	The first vertex of the triangle the intersectionPoint lies in.
	 * @param b	The second vertex of the triangle the intersectionPoint lies in.
	 * @param c	The third vertex of the triangle the intersectionPoint lies in.
	 * @param intersectionPoint	The intersectionPoint to shade.
	 * @param pointLight	The pointLight used as only calculation in this shading.
	 * @return	Color3f
	 * 		The color at the given intersectionPoint.
	 */
	public abstract Color3f shade(Vertex a, Vertex b, Vertex c, Point3f intersectionPoint, PointLight pointLight);
}
