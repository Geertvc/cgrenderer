package ray_Tracing;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Triangle;
import main.Constants;
import sceneModel.Camera;
import sceneModel.Material;
import sceneModel.PointLight;
import sceneModel.Texture;
import sceneModel.sceneGraph.LeafNode;
import sceneModel.sceneGraph.MovingLeafNode;
import abstractModel.Color3f;
import abstractModel.IntersectionRecord;
import abstractModel.Matrix4f;
import abstractModel.Point3f;
import abstractModel.Scene;
import abstractModel.Tuple3f;
import abstractModel.Vector3f;

/**
 * Thread used to do the rendering of images in a multithreaded way.
 * 
 * @author Geert Van Campenhout
 */
public class RayTracer extends Thread {
	
	Scene scene;
	MainMultiThreaded main;
	
	/**
	 * Creates a new WorkThread for the given Main and Scene.
	 * 
	 * @param main	The main used to access the data needed to calculate parts of the image.
	 * @param scene	The scene to render in the image.
	 */
	public RayTracer(MainMultiThreaded main, Scene scene){
		this.scene = scene;
		this.main = main;
	}
	
	/**
	 * Asks data to render from the main until all the data is calculated and calculates the received data.
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		int rowToProcess = main.getNextRowData();
		while(rowToProcess != -1){
			Color3f[] calculatedColors = calculateGivenRow(rowToProcess, Constants.NBOFHORIZONTALPIXELS, 
					Constants.NBOFVERTICALPIXELS, Constants.DISTANCETOSCREEN);
			main.setColors(rowToProcess, calculatedColors);
			rowToProcess = main.getNextRowData();
		}
	}
	
	/**
	 * Calculates the color for all the pixels in the given row of the image.
	 * 
	 * @param Row	The given row to calculate.
	 * @param nbOfHorizontalPixels	The number of horizontal pixels in the image.
	 * @param nbOfVerticalPixels	The number of vertical pixels in the image.
	 * @param positionLeftEdgeImage	The position of the left edge of the image.
	 * @param positionRightEdgeImage	The position of the right edge of the image.
	 * @param positionTopEdgeImage	The position of the top edge of the image.
	 * @param positionBottomEdgeImage	The position of the bottom edge of the image.
	 * @param distanceToScreen	The perpendicular distance from the camera to the screen.
	 * @return	Color3f[]
	 * 		A calculated color object for each pixel in the given row. 
	 */
	@SuppressWarnings("unused")
	public Color3f[] calculateGivenRow(int Row,
			int nbOfHorizontalPixels, int nbOfVerticalPixels, float distanceToScreen) {
		Camera camera = scene.getActiveCamera();
		Color3f[] colors = new Color3f[nbOfHorizontalPixels];
		if(Constants.SAMPLESPERPIXEL > 1){
			if(Constants.ANTIALIASING > 1){
				//Depth of field AND antiAliasing
				Ray[][][] rays = getGeneratedRaysForRowWithDepthOfFieldAndAntiAliasing(Row, nbOfHorizontalPixels,
						nbOfVerticalPixels, scene.getPositionLeftEdgeImage(),
						scene.getPositionRightEdgeImage(), scene.getPositionTopEdgeImage(),
						scene.getPositionBottomEdgeImage(), distanceToScreen, camera);
				for (int j = 0; j < nbOfHorizontalPixels; j++) {
					Color3f sampleColor = new Color3f();
					for (int d = 0; d < Constants.SAMPLESPERPIXEL; d++) {
						Color3f c = new Color3f();
						for (int i = 0; i < Constants.ANTIALIASING; i++) {
							c.addSet(c, calculateColorForPixel(rays[j][d][i]));
						}
						c.scale((float) 1/Constants.ANTIALIASING);
						sampleColor.addSet(sampleColor, c);
					}
					sampleColor.scale((float) 1/Constants.SAMPLESPERPIXEL);
					colors[j] = new Color3f(sampleColor);
				}
			} else{
				//Only Depth of field
				Ray[][] rays = getGeneratedRaysForRowWithDepthOfField(Row, nbOfHorizontalPixels,
						nbOfVerticalPixels, scene.getPositionLeftEdgeImage(),
						scene.getPositionRightEdgeImage(), scene.getPositionTopEdgeImage(),
						scene.getPositionBottomEdgeImage(), distanceToScreen, camera);
				for (int j = 0; j < nbOfHorizontalPixels; j++) {
					Color3f sampleColor = new Color3f();
					for (int d = 0; d < Constants.SAMPLESPERPIXEL; d++) {
						sampleColor.addSet(sampleColor, calculateColorForPixel(rays[j][d]));
					}
					sampleColor.scale((float) 1/Constants.SAMPLESPERPIXEL);
					colors[j] = new Color3f(sampleColor);
				}
			}
		} else{
			if(Constants.ANTIALIASING > 1){
				//Only antiAliasing
				Ray[][] rays = getGeneratedRaysForRowWithAntiAliasing(Row, nbOfHorizontalPixels,
						nbOfVerticalPixels, scene.getPositionLeftEdgeImage(),
						scene.getPositionRightEdgeImage(), scene.getPositionTopEdgeImage(),
						scene.getPositionBottomEdgeImage(), distanceToScreen, camera);
				for (int j = 0; j < nbOfHorizontalPixels; j++) {
					Color3f c = new Color3f();
					for (int i = 0; i < Constants.ANTIALIASING; i++) {
						c.addSet(c, calculateColorForPixel(rays[j][i]));
					}
					c.scale((float) 1/Constants.ANTIALIASING);
					colors[j] = new Color3f(c);
				}
			} else{
				//No depth of field and no antiAliasing
				if(Constants.MBSAMPLESPERPIXEL > 0){
					Ray[] rays = getGeneratedRaysForRow(Row, nbOfHorizontalPixels,
							nbOfVerticalPixels, scene.getPositionLeftEdgeImage(),
							scene.getPositionRightEdgeImage(), scene.getPositionTopEdgeImage(),
							scene.getPositionBottomEdgeImage(), distanceToScreen, camera);
					for (int j = 0; j < nbOfHorizontalPixels; j++) {
						Color3f pixelColor = new Color3f();
						for (int d = 0; d < Constants.MBSAMPLESPERPIXEL; d++) {
							float randomTime = (float) Math.random()*(Constants.TEND - Constants.T0) + Constants.T0;
							pixelColor.addSet(pixelColor, calculateColorForPixelAtTime(rays[j], randomTime));
						}
						pixelColor.scale((float) 1/Constants.MBSAMPLESPERPIXEL);
						colors[j] = pixelColor;
					}
				} else{
					Ray[] rays = getGeneratedRaysForRow(Row, nbOfHorizontalPixels,
							nbOfVerticalPixels, scene.getPositionLeftEdgeImage(),
							scene.getPositionRightEdgeImage(), scene.getPositionTopEdgeImage(),
							scene.getPositionBottomEdgeImage(), distanceToScreen, camera);
					for (int j = 0; j < nbOfHorizontalPixels; j++) {
						colors[j] = calculateColorForPixel(rays[j]);
					}
				}
			}
		}
		return colors;
	}
	
	private Tuple3f calculateColorForPixelAtTime(Ray ray, float time) {
		Color3f pixelColor;
		IntersectionRecord intersectionRecord = scene.hitAtTime(ray, 0, Float.MAX_VALUE, time);
		if(intersectionRecord != null){
			Color3f totalColor = new Color3f();
			String textureName = scene.getLeafNode(intersectionRecord.geometryName).getTextureName();
			if(textureName != null){
				LeafNode leaf = scene.getLeafNode(intersectionRecord.geometryName);
				Matrix4f transformMatrix;
				if(leaf instanceof MovingLeafNode){
					MovingLeafNode movingLeaf = (MovingLeafNode) leaf;
					transformMatrix = movingLeaf.getTotalTransformMatrix(time);
				} else{
					transformMatrix = leaf.getTotalTransformMatrix();
				}
				Point3f intersectionPoint = new Point3f(transformMatrix.locationMult(intersectionRecord.intersectionPoint));
				Texture texture = scene.getTextures().get(textureName);
				String[] activeLights = scene.getActiveLights();
				for (int i = 0; i < activeLights.length; i++) {
					PointLight pointLight = scene.getPointLights().get(activeLights[i]);
					Color3f colorToAdd;
					if(Constants.SHADOW){
						colorToAdd = textureShadowShading(intersectionRecord,
								intersectionPoint, texture, pointLight);
					} else{
						colorToAdd = texture.shade(intersectionRecord.a, intersectionRecord.b, intersectionRecord.c, intersectionRecord.intersectionPoint, pointLight);
					}
					totalColor.add(colorToAdd);
				}
			} else{
				//Use normal shading on the geometry
				String materialName = scene.getLeafNode(intersectionRecord.geometryName).getMaterialName();
				Material material = scene.getMaterials().get(materialName);
				//Find normal
				Vector3f normal = Triangle.getInterpolatedNormalizedNormal(intersectionRecord.a, intersectionRecord.b, intersectionRecord.c, new Point3f(intersectionRecord.intersectionPoint));
				LeafNode leaf = scene.getLeafNode(intersectionRecord.geometryName);
				Matrix4f transformMatrix;
				Matrix4f invertedTransformMatrix = new Matrix4f();
				if(leaf instanceof MovingLeafNode){
					MovingLeafNode movingLeaf = (MovingLeafNode) leaf;
					transformMatrix = movingLeaf.getTotalTransformMatrix(time);
				} else{
					transformMatrix = leaf.getTotalTransformMatrix();
				}
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
						colorToAdd = materialShadowShading(material, normal,
								intersectionPoint, pointLight);
					} else{
						colorToAdd = material.shade(normal, intersectionPoint, pointLight, scene.getActiveCamera().position);
					}
					totalColor.add(colorToAdd);
				}
			}
			pixelColor = new Color3f(
					((totalColor.x > 1) ? 1 : totalColor.x),
					((totalColor.y > 1) ? 1 : totalColor.y),
					((totalColor.z > 1) ? 1 : totalColor.z));
		} else{
			if(!Constants.ENVIRONMENTMAP){
				pixelColor = new Color3f(scene.getBackground());
			} else{
				Vector3f dir = ray.direction;
				float x = dir.x;
				float y = dir.y;
				float z = dir.z;
				if(Math.abs(x) > Math.abs(y) && Math.abs(x) > Math.abs(z)){
					if(x>0){
						//Right face
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPRIGHT);
						pixelColor = new Color3f(texture.getColor3fAt((dir.y+dir.x)/(2*dir.x), (dir.z+dir.x)/(2*dir.x)));
					} else{
						//Left face
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPLEFT);
						pixelColor = new Color3f(texture.getColor3fAt((dir.y+dir.x)/(2*dir.x), (dir.z+dir.x)/(2*dir.x)));
					}
				} else if(Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(z)){
					if(y>0){
						//Back face
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPBACK);
						pixelColor = new Color3f(texture.getColor3fAt((dir.x+dir.y)/(2*dir.y), (dir.z+dir.y)/(2*dir.y)));
					} else{
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPFRONT);
						pixelColor = new Color3f(texture.getColor3fAt((dir.x+dir.y)/(2*dir.y), (dir.z+dir.y)/(2*dir.y)));
					}
				} else if(Math.abs(z) > Math.abs(y) && Math.abs(z) > Math.abs(x)){
					if(z>0){
						//Top face
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPTOP);
						pixelColor = new Color3f(texture.getColor3fAt((dir.y+dir.z)/(2*dir.z), (dir.x+dir.z)/(2*dir.z)));
					} else{
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPBOTTOM);
						pixelColor = new Color3f(texture.getColor3fAt((dir.y+dir.z)/(2*dir.z), (dir.x+dir.z)/(2*dir.z)));
					}
				} else{
					pixelColor = new Color3f(scene.getBackground());
				}
			}
			
		}
		return pixelColor;
	}

	/**
	 * Calculates a Color3f object for the pixel with the given ray.
	 * 
	 * @param	Ray	The given ray going through the needed pixel to intersect with all the meshes in the scene.
	 * @return	Color3f
	 * 		The color the pixel gets by intersecting the ray with all the meshes in the scene.
	 * 		If there is no intersection the color = this.background.
	 */
	protected Color3f calculateColorForPixel(Ray ray) {
		Color3f pixelColor;
		IntersectionRecord intersectionRecord = scene.hit(ray, 0, Float.MAX_VALUE);
		if(intersectionRecord != null){
			Color3f totalColor = new Color3f();
			String textureName = scene.getLeafNode(intersectionRecord.geometryName).getTextureName();
			if(textureName != null){
				LeafNode leaf = scene.getLeafNode(intersectionRecord.geometryName); 
				Matrix4f transformMatrix = leaf.getTotalTransformMatrix();
				Point3f intersectionPoint = new Point3f(transformMatrix.locationMult(intersectionRecord.intersectionPoint));
				Texture texture = scene.getTextures().get(textureName);
				String[] activeLights = scene.getActiveLights();
				for (int i = 0; i < activeLights.length; i++) {
					PointLight pointLight = scene.getPointLights().get(activeLights[i]);
					Color3f colorToAdd;
					if(Constants.SHADOW){
						colorToAdd = textureShadowShading(intersectionRecord,
								intersectionPoint, texture, pointLight);
					} else{
						colorToAdd = texture.shade(intersectionRecord.a, intersectionRecord.b, intersectionRecord.c, intersectionRecord.intersectionPoint, pointLight);
					}
					totalColor.add(colorToAdd);
				}
			} else{
				//Use normal shading on the geometry
				String materialName = scene.getLeafNode(intersectionRecord.geometryName).getMaterialName();
				Material material = scene.getMaterials().get(materialName);
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
						colorToAdd = materialShadowShading(material, normal,
								intersectionPoint, pointLight);
					} else{
						colorToAdd = material.shade(normal, intersectionPoint, pointLight, scene.getActiveCamera().position);
					}
					totalColor.add(colorToAdd);
				}
			}
			pixelColor = new Color3f(
					((totalColor.x > 1) ? 1 : totalColor.x),
					((totalColor.y > 1) ? 1 : totalColor.y),
					((totalColor.z > 1) ? 1 : totalColor.z));
		} else{
			if(!Constants.ENVIRONMENTMAP){
				pixelColor = new Color3f(scene.getBackground());
			} else{
				Vector3f dir = ray.direction;
				float x = dir.x;
				float y = dir.y;
				float z = dir.z;
				if(Math.abs(x) > Math.abs(y) && Math.abs(x) > Math.abs(z)){
					if(x>0){
						//Right face
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPRIGHT);
						pixelColor = new Color3f(texture.getColor3fAt((dir.y+dir.x)/(2*dir.x), (dir.z+dir.x)/(2*dir.x)));
					} else{
						//Left face
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPLEFT);
						pixelColor = new Color3f(texture.getColor3fAt((dir.y+dir.x)/(2*dir.x), (dir.z+dir.x)/(2*dir.x)));
					}
				} else if(Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(z)){
					if(y>0){
						//Back face
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPBACK);
						pixelColor = new Color3f(texture.getColor3fAt((dir.x+dir.y)/(2*dir.y), (dir.z+dir.y)/(2*dir.y)));
					} else{
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPFRONT);
						pixelColor = new Color3f(texture.getColor3fAt((dir.x+dir.y)/(2*dir.y), (dir.z+dir.y)/(2*dir.y)));
					}
				} else if(Math.abs(z) > Math.abs(y) && Math.abs(z) > Math.abs(x)){
					if(z>0){
						//Top face
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPTOP);
						pixelColor = new Color3f(texture.getColor3fAt((dir.y+dir.z)/(2*dir.z), (dir.x+dir.z)/(2*dir.z)));
					} else{
						Texture texture = scene.getTextures().get(Constants.ENVIRONMENTMAPBOTTOM);
						pixelColor = new Color3f(texture.getColor3fAt((dir.y+dir.z)/(2*dir.z), (dir.x+dir.z)/(2*dir.z)));
					}
				} else{
					pixelColor = new Color3f(scene.getBackground());
				}
			}
			
		}
		return pixelColor;
	}
	
	/**
	 * Returns the generated rays for the given row of pixels in the image with antialiasing.
	 * 
	 * @param Row	The given row to generate rays for.
	 * @param nbOfHorizontalPixels	The number of horizontal pixels in the image.
	 * @param nbOfVerticalPixels	The number of vertical pixels in the image.
	 * @param positionLeftEdgeImage	The position of the left edge of the image.
	 * @param positionRightEdgeImage	The position of the right edge of the image.
	 * @param positionTopEdgeImage	The position of the top edge of the image.
	 * @param positionBottomEdgeImage	The position of the bottom edge of the image.
	 * @param distanceToScreen	The perpendicular distance from the camera to the screen.
	 * @param camera	The active camera.
	 * @return	Ray[][][]
	 * 		For each depth of field sample:
	 * 		A generated Ray object for each of the pixels in the given row of the image and multiple of those object for antialiasing.
	 */
	protected Ray[][] getGeneratedRaysForRowWithDepthOfField(int Row, int nbOfHorizontalPixels,
			int nbOfVerticalPixels, float positionLeftEdgeImage,
			float positionRightEdgeImage, float positionTopEdgeImage,
			float positionBottomEdgeImage, float distanceToScreen, Camera camera) {
		Ray_Generation rayGen = new Ray_Generation(
				camera.position, 
				nbOfHorizontalPixels, nbOfVerticalPixels, 
				positionLeftEdgeImage, positionRightEdgeImage, 
				positionTopEdgeImage, positionBottomEdgeImage, 
				distanceToScreen);
		Vector3f wAxis = new Vector3f();
		wAxis.negate(camera.direction);
		wAxis.normalize();
    	Vector3f t = new Vector3f(camera.up);
    	Vector3f uAxis = new Vector3f();
    	uAxis.cross(t, wAxis);
    	uAxis.normalize();
    	Vector3f vAxis = new Vector3f();
    	vAxis.cross(wAxis, uAxis);
    	vAxis.normalize();
		Ray[][] rays;
		rays = generateRaysForRowWithDepthOfField(Row, camera, rayGen, vAxis, wAxis, uAxis);
		return rays;
	}
	
	/**
	 * Generates rays for the given row with antialiasing.
	 * 
	 * @param Row	The row to generate rays for.
	 * @param camera	The active camera.
	 * @param rayGen	The Ray_Generation object that will generate the rays.
	 * @param vAxis		The v axis of the local coordinate system originated at the camera position.
	 * @param wAxis		The w axis of the local coordinate system originated at the camera position.
	 * @param uAxis		The u axis of the local coordinate system originated at the camera position.
	 * @return	Ray[][]
	 * 		A generated Ray object for each of the pixels in the given row of the image and multiple of these for antialiasing.
	 */
	protected Ray[][] generateRaysForRowWithDepthOfField(int Row, Camera camera,
			Ray_Generation rayGen, Vector3f vAxis, Vector3f wAxis,
			Vector3f uAxis) {
		Ray[][] rays;
		if(Constants.PERSPECTIVERAYS){
			rays = rayGen.createPerspectiveProjectionRaysUsingDepthOfField(Row, camera.position, uAxis, vAxis, wAxis);
		} else{
			//parallel rays
			rays = rayGen.createParallelProjectionRaysUsingDepthOfField(Row, camera.position, uAxis, vAxis, wAxis);
		}
		return rays;
	}
	
	/**
	 * Returns the generated rays for the given row of pixels in the image with antialiasing.
	 * 
	 * @param Row	The given row to generate rays for.
	 * @param nbOfHorizontalPixels	The number of horizontal pixels in the image.
	 * @param nbOfVerticalPixels	The number of vertical pixels in the image.
	 * @param positionLeftEdgeImage	The position of the left edge of the image.
	 * @param positionRightEdgeImage	The position of the right edge of the image.
	 * @param positionTopEdgeImage	The position of the top edge of the image.
	 * @param positionBottomEdgeImage	The position of the bottom edge of the image.
	 * @param distanceToScreen	The perpendicular distance from the camera to the screen.
	 * @param camera	The active camera.
	 * @return	Ray[][][]
	 * 		For each depth of field sample:
	 * 		A generated Ray object for each of the pixels in the given row of the image and multiple of those object for antialiasing.
	 */
	protected Ray[][][] getGeneratedRaysForRowWithDepthOfFieldAndAntiAliasing(int Row, int nbOfHorizontalPixels,
			int nbOfVerticalPixels, float positionLeftEdgeImage,
			float positionRightEdgeImage, float positionTopEdgeImage,
			float positionBottomEdgeImage, float distanceToScreen, Camera camera) {
		Ray_Generation rayGen = new Ray_Generation(
				camera.position, 
				nbOfHorizontalPixels, nbOfVerticalPixels, 
				positionLeftEdgeImage, positionRightEdgeImage, 
				positionTopEdgeImage, positionBottomEdgeImage, 
				distanceToScreen);
		Vector3f wAxis = new Vector3f();
		wAxis.negate(camera.direction);
		wAxis.normalize();
    	Vector3f t = new Vector3f(camera.up);
    	Vector3f uAxis = new Vector3f();
    	uAxis.cross(t, wAxis);
    	uAxis.normalize();
    	Vector3f vAxis = new Vector3f();
    	vAxis.cross(wAxis, uAxis);
    	vAxis.normalize();
		Ray[][][] rays;
		rays = generateRaysForRowWithDepthOfFieldAndAntiAliasing(Row, camera, rayGen, vAxis, wAxis, uAxis);
		return rays;
	}
	
	/**
	 * Generates rays for the given row with antialiasing.
	 * 
	 * @param Row	The row to generate rays for.
	 * @param camera	The active camera.
	 * @param rayGen	The Ray_Generation object that will generate the rays.
	 * @param vAxis		The v axis of the local coordinate system originated at the camera position.
	 * @param wAxis		The w axis of the local coordinate system originated at the camera position.
	 * @param uAxis		The u axis of the local coordinate system originated at the camera position.
	 * @return	Ray[][]
	 * 		A generated Ray object for each of the pixels in the given row of the image and multiple of these for antialiasing.
	 */
	protected Ray[][][] generateRaysForRowWithDepthOfFieldAndAntiAliasing(int Row, Camera camera,
			Ray_Generation rayGen, Vector3f vAxis, Vector3f wAxis,
			Vector3f uAxis) {
		Ray[][][] rays;
		if(Constants.PERSPECTIVERAYS){
			rays = rayGen.createPerspectiveProjectionRaysUsingDepthOfFieldAndAntiAliasing(Row, camera.position, uAxis, vAxis, wAxis);
		} else{
			//parallel rays
			rays = rayGen.createParallelProjectionRaysUsingDepthOfFieldAndAntiAliasing(Row, camera.position, uAxis, vAxis, wAxis);
		}
		return rays;
	}
	
	/**
	 * Returns the generated rays for the given row of pixels in the image with antialiasing.
	 * 
	 * @param Row	The given row to generate rays for.
	 * @param nbOfHorizontalPixels	The number of horizontal pixels in the image.
	 * @param nbOfVerticalPixels	The number of vertical pixels in the image.
	 * @param positionLeftEdgeImage	The position of the left edge of the image.
	 * @param positionRightEdgeImage	The position of the right edge of the image.
	 * @param positionTopEdgeImage	The position of the top edge of the image.
	 * @param positionBottomEdgeImage	The position of the bottom edge of the image.
	 * @param distanceToScreen	The perpendicular distance from the camera to the screen.
	 * @param camera	The active camera.
	 * @return	Ray[][]
	 * 		A generated Ray object for each of the pixels in the given row of the image and multiple of those object for antialiasing.
	 */
	protected Ray[][] getGeneratedRaysForRowWithAntiAliasing(int Row, int nbOfHorizontalPixels,
			int nbOfVerticalPixels, float positionLeftEdgeImage,
			float positionRightEdgeImage, float positionTopEdgeImage,
			float positionBottomEdgeImage, float distanceToScreen, Camera camera) {
		Ray_Generation rayGen = new Ray_Generation(
				camera.position, 
				nbOfHorizontalPixels, nbOfVerticalPixels, 
				positionLeftEdgeImage, positionRightEdgeImage, 
				positionTopEdgeImage, positionBottomEdgeImage, 
				distanceToScreen);
		Vector3f wAxis = new Vector3f();
		wAxis.negate(camera.direction);
		wAxis.normalize();
    	Vector3f t = new Vector3f(camera.up);
    	Vector3f uAxis = new Vector3f();
    	uAxis.cross(t, wAxis);
    	uAxis.normalize();
    	Vector3f vAxis = new Vector3f();
    	vAxis.cross(wAxis, uAxis);
    	vAxis.normalize();
		Ray[][] rays;
		rays = generateRaysForRowWithAntiAliasing(Row, camera, rayGen, vAxis, wAxis, uAxis);
		return rays;
	}
	
	/**
	 * Generates rays for the given row with antialiasing.
	 * 
	 * @param Row	The row to generate rays for.
	 * @param camera	The active camera.
	 * @param rayGen	The Ray_Generation object that will generate the rays.
	 * @param vAxis		The v axis of the local coordinate system originated at the camera position.
	 * @param wAxis		The w axis of the local coordinate system originated at the camera position.
	 * @param uAxis		The u axis of the local coordinate system originated at the camera position.
	 * @return	Ray[][]
	 * 		A generated Ray object for each of the pixels in the given row of the image and multiple of these for antialiasing.
	 */
	protected Ray[][] generateRaysForRowWithAntiAliasing(int Row, Camera camera,
			Ray_Generation rayGen, Vector3f vAxis, Vector3f wAxis,
			Vector3f uAxis) {
		Ray[][] rays;
		if(Constants.PERSPECTIVERAYS){
			rays = rayGen.createPerspectiveProjectionRaysUsingAntiAliasing(Row, camera.position, uAxis, vAxis, wAxis);
		} else{
			//parallel rays
			rays = rayGen.createParallelProjectionRaysUsingAntiAliasing(Row, camera.position, uAxis, vAxis, wAxis);
		}
		return rays;
	}
	
	/**
	 * Returns the generated rays for the given row of pixels in the image.
	 * 
	 * @param Row	The given row to generate rays for.
	 * @param nbOfHorizontalPixels	The number of horizontal pixels in the image.
	 * @param nbOfVerticalPixels	The number of vertical pixels in the image.
	 * @param positionLeftEdgeImage	The position of the left edge of the image.
	 * @param positionRightEdgeImage	The position of the right edge of the image.
	 * @param positionTopEdgeImage	The position of the top edge of the image.
	 * @param positionBottomEdgeImage	The position of the bottom edge of the image.
	 * @param distanceToScreen	The perpendicular distance from the camera to the screen.
	 * @param camera	The active camera.
	 * @return	Ray[]
	 * 		A generated Ray object for each of the pixels in the given row of the image.
	 */
	protected Ray[] getGeneratedRaysForRow(int Row, int nbOfHorizontalPixels,
			int nbOfVerticalPixels, float positionLeftEdgeImage,
			float positionRightEdgeImage, float positionTopEdgeImage,
			float positionBottomEdgeImage, float distanceToScreen, Camera camera) {
		Ray_Generation rayGen = new Ray_Generation(
				camera.position, 
				nbOfHorizontalPixels, nbOfVerticalPixels, 
				positionLeftEdgeImage, positionRightEdgeImage, 
				positionTopEdgeImage, positionBottomEdgeImage, 
				distanceToScreen);
		Vector3f wAxis = new Vector3f();
		wAxis.negate(camera.direction);
		wAxis.normalize();
    	Vector3f t = new Vector3f(camera.up);
    	Vector3f uAxis = new Vector3f();
    	uAxis.cross(t, wAxis);
    	uAxis.normalize();
    	Vector3f vAxis = new Vector3f();
    	vAxis.cross(wAxis, uAxis);
    	vAxis.normalize();
		
		Ray[] rays;
		rays = generateRaysForRow(Row, camera, rayGen, vAxis, wAxis, uAxis);
		return rays;
	}

	/**
	 * Generates rays for the given row.
	 * 
	 * @param Row	The row to generate rays for.
	 * @param camera	The active camera.
	 * @param rayGen	The Ray_Generation object that will generate the rays.
	 * @param vAxis		The v axis of the local coordinate system originated at the camera position.
	 * @param wAxis		The w axis of the local coordinate system originated at the camera position.
	 * @param uAxis		The u axis of the local coordinate system originated at the camera position.
	 * @return	Ray[]
	 * 		A generated Ray object for each of the pixels in the given row of the image.
	 */
	protected Ray[] generateRaysForRow(int Row, Camera camera,
			Ray_Generation rayGen, Vector3f vAxis, Vector3f wAxis,
			Vector3f uAxis) {
		Ray[] rays;
		if(Constants.PERSPECTIVERAYS){
			rays = rayGen.createPerspectiveProjectionRays(Row, camera.position, uAxis, vAxis, wAxis);
		} else{
			//parallel rays
			rays = rayGen.createParallelProjectionRays(Row, camera.position, uAxis, vAxis, wAxis);
		}
		return rays;
	}
	
	/**
	 * Calculates whether the given intersectionPoint must be shaded or overshadowed.
	 * 
	 * @param intersectionRecord	The intersection to shade at.
	 * @param intersectionPoint	The intersectionPoint to shade at.
	 * @param Texture	The texture to shade with.
	 * @param pointLight	The pointLight as only lightsource in this calculation.
	 * @return	Color3f
	 * 		The shaded value.
	 */
	@SuppressWarnings("unused")
	protected Color3f textureShadowShading(
			IntersectionRecord intersectionRecord, Point3f intersectionPoint,
			Texture texture, PointLight pointLight) {
		Color3f colorToAdd = null;
		final float lightSize = 1f;
		if(Constants.SOFT_SHADOWS >0){
			PointLight softPointLight = new PointLight(pointLight.position, pointLight.intensity/Constants.SOFT_SHADOWS, pointLight.color, "softPointLight");
			Color3f shadowColor = new Color3f();
			for (int i = 0; i < Constants.SOFT_SHADOWS; i++) {
				Vector3f shadowDirection = new Vector3f();
				Ray shadowRay = getSoftShadowRay(intersectionPoint, lightSize,
						softPointLight, shadowDirection);
				shadowColor.add(getTextureShadeColor(intersectionRecord, texture, softPointLight, shadowRay));
			}
			colorToAdd = shadowColor;
		} else{
			Vector3f shadowDirection = new Vector3f();
			shadowDirection.substractSet(pointLight.position, intersectionPoint);
			Ray shadowRay = new Ray(intersectionPoint, shadowDirection);
			colorToAdd = getTextureShadeColor(intersectionRecord, texture,
					pointLight, shadowRay);
		}
		return colorToAdd;
	}
	
	/**
	 * Returns the color to shade from the texture at the intersectionPoint.
	 * 
	 * @param intersectionRecord	The intersection to shade.
	 * @param texture	The texture to get the color from.
	 * @param pointLight	The pointLight that shines on the scene.
	 * @param shadowRay	The shadowRay to check if the intersectionPoint is in shadow or not.
	 * @return	Color3f
	 * 		The color to shade the intersection with.
	 */
	protected Color3f getTextureShadeColor(
			IntersectionRecord intersectionRecord, Texture texture,
			PointLight pointLight, Ray shadowRay) {
		Color3f shadeColor;
		if(scene.hitClosest(shadowRay, 0.01f, Float.MAX_VALUE) != null){
			shadeColor = new Color3f();
		} else{
			shadeColor = texture.shade(intersectionRecord.a, intersectionRecord.b, intersectionRecord.c, intersectionRecord.intersectionPoint, pointLight);
		}
		return shadeColor;
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
	protected Color3f materialShadowShading(Material material, Vector3f normal,
			Point3f intersectionPoint, PointLight pointLight) {
		final float lightSize = 1f;
		Color3f colorToAdd = null;
		if(Constants.SOFT_SHADOWS >0){
			PointLight softPointLight = new PointLight(pointLight.position, pointLight.intensity/Constants.SOFT_SHADOWS, pointLight.color, "softPointLight");
			Color3f shadowColor = new Color3f();
			for (int i = 0; i < Constants.SOFT_SHADOWS; i++) {
				Vector3f shadowDirection = new Vector3f();
				Ray shadowRay = getSoftShadowRay(intersectionPoint, lightSize,
						softPointLight, shadowDirection);
				shadowColor.add(getMaterialShadeColor(material, normal,
						intersectionPoint, softPointLight, shadowRay));
			}
			colorToAdd = shadowColor;
		} else{
			Vector3f shadowDirection = new Vector3f();
			shadowDirection.substractSet(pointLight.position, intersectionPoint);
			Ray shadowRay = new Ray(intersectionPoint, shadowDirection);
			colorToAdd = getMaterialShadeColor(material, normal,
					intersectionPoint, pointLight, shadowRay);
		}
		return colorToAdd;
	}

	/**
	 * Returns a ray to get soft shadows with.
	 * 
	 * @param intersectionPoint	The intersectionPoint to get the ray at.
	 * @param lightSize	The size of the light.
	 * @param softPointLight	The pointLight to transform into a soft PointLight.
	 * @param shadowDirection	The direction of the shadow.
	 * @return	Ray
	 * 		A softShadowRay.
	 */
	protected Ray getSoftShadowRay(Point3f intersectionPoint,
			final float lightSize, PointLight softPointLight,
			Vector3f shadowDirection) {
		Point3f startingPoint = new Point3f(softPointLight.position);
		float xDisplacement = (float) (Math.random()-0.5)*lightSize;
		float yDisplacement = (float) (Math.random()-0.5)*lightSize;
		float zDisplacement = (float) (Math.random()-0.5)*lightSize;
		startingPoint.add(new Vector3f(xDisplacement, yDisplacement, zDisplacement));
		shadowDirection.substractSet(startingPoint, intersectionPoint);
		Ray shadowRay = new Ray(intersectionPoint, shadowDirection);
		return shadowRay;
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
	protected Color3f getMaterialShadeColor(Material material, Vector3f normal,
			Point3f intersectionPoint, PointLight pointLight, Ray shadowRay) {
		Color3f shadeColor;
		if(scene.hitClosest(shadowRay, 0.01f, Float.MAX_VALUE) != null){
			shadeColor = new Color3f();
		} else{
			shadeColor = material.shade(normal, intersectionPoint, pointLight, scene.getActiveCamera().position);
		}
		return shadeColor;
	}
}
