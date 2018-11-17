package abstractModel;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Mesh;
import indexed_Neighbour_Triangle_Mesh_With_Edges.Triangle;
import indexed_Neighbour_Triangle_Mesh_With_Edges.Vertex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import main.Constants;
import ray_Tracing.Ray;
import sceneModel.Camera;
import sceneModel.DiffuseMaterial;
import sceneModel.DirectionalLight;
import sceneModel.GlobalMaterial;
import sceneModel.LinearCombinedMaterial;
import sceneModel.Material;
import sceneModel.PhongMaterial;
import sceneModel.PointLight;
import sceneModel.SpotLight;
import sceneModel.Surface;
import sceneModel.Texture;
import sceneModel.sceneGraph.AbstractBoundingBox;
import sceneModel.sceneGraph.BoundingBox;
import sceneModel.sceneGraph.GroupNode;
import sceneModel.sceneGraph.LeafNode;
import sceneModel.sceneGraph.MovingLeafNode;
import sceneModel.sceneGraph.SceneGraph;
import sceneModel.sceneGraph.SceneGraphElement;
import abstractModel.parser.ObjectParser;

/**
 * Class representing a scene that can be visualized in an image.
 * 
 * @author Geert Van Campenhout
 * @version 5.2
 */
public class Scene extends Surface{
	
	protected HashMap<String, Camera> cameras;
	protected HashMap<String, DirectionalLight> dirLights;
	protected HashMap<String, PointLight> pointLights;
	protected HashMap<String, SpotLight> spotLights;
	protected HashMap<String, Mesh> meshes;
	protected HashMap<String, Material> materials;
	protected HashMap<String, Texture> textures;
	protected HashMap<String, String[]> linkedGeometries;
	
	
	protected Color3f background;
	protected String activeCamera;
	protected String[] activeLights;
	
	protected float positionLeftEdgeImage;
	protected float positionRightEdgeImage;
	protected float positionTopEdgeImage;
	protected float positionBottomEdgeImage;
	
	protected SceneGraph sceneGraph;
	
	BoundingBox bbox = null;
	
	/**
	 * Create a new Scene.
	 */
	public Scene(){
		super("Scene");
		this.cameras = new HashMap<String, Camera>();
		this.dirLights = new HashMap<String, DirectionalLight>();
		this.pointLights = new HashMap<String, PointLight>();
		this.spotLights = new HashMap<String, SpotLight>();
		this.meshes = new HashMap<String, Mesh>();
		this.materials = new HashMap<String, Material>();
		this.textures = new HashMap<String, Texture>();
		this.linkedGeometries = new HashMap<String, String[]>();
		sceneGraph = new SceneGraph();
	}
	
	/**
	 * Set the active components in the scene.
	 * 
	 * @param cameraName	The name of the active camera.
	 * @param lightNames	The names of the active lights.
	 * @param background	The color of the background.
	 */
	public void activateScene(String cameraName, String[] lightNames,
			Color3f background) {
		this.activeCamera = cameraName;
		this.activeLights = lightNames;
		this.background = background;
		calculateSceneEdges(cameraName);
	}
	
	/**
	 * Calculates the edges of the imageScreen.
	 * 
	 * @param cameraName	The name of the active camera.
	 */
	protected void calculateSceneEdges(String cameraName) {
		Camera activeCamera = cameras.get(cameraName);
		double angleInRadians = Math.toRadians(activeCamera.fovy);
		double tanAngle = Math.tan(angleInRadians/2);
		float topDistanceToMiddleScreen = (float) (Constants.DISTANCETOSCREEN*tanAngle);
		positionTopEdgeImage = topDistanceToMiddleScreen;
		positionBottomEdgeImage = -topDistanceToMiddleScreen;
		positionRightEdgeImage = topDistanceToMiddleScreen*Constants.NBOFHORIZONTALPIXELS/Constants.NBOFVERTICALPIXELS;
		positionLeftEdgeImage = -positionRightEdgeImage;
	}
	
	/**
	 * Returns the BoundingBox that contains the whole scene.
	 * 
	 * @return	BoundingBox
	 * 		The BoundingBox that contains the whole scene.
	 */
	public BoundingBox getSceneBoundingBox(){
		if(this.bbox == null){
			AbstractBoundingBox temp = null;
			float xMin = Float.MAX_VALUE;
			float xMax = -Float.MAX_VALUE;
			float yMin = Float.MAX_VALUE;
			float yMax = -Float.MAX_VALUE;
			float zMin = Float.MAX_VALUE;
			float zMax = -Float.MAX_VALUE;
			for (Mesh mesh : this.meshes.values()) {
				temp = mesh.getBbox();
				if(temp == null)
					throw new IllegalStateException("There is a mesh that has no BoundingBox.");
				if(xMin > temp.getxMin())
					xMin = temp.getxMin();
				if(xMax < temp.getxMax())
					xMax = temp.getxMax();
				if(yMin > temp.getyMin())
					yMin = temp.getyMin();
				if(yMax < temp.getyMax())
					yMax = temp.getyMax();
				if(zMin > temp.getzMin())
					zMin = temp.getzMin();
				if(zMax < temp.getzMax())
					zMax = temp.getzMax();
			}
			this.bbox = new BoundingBox(getName(), xMin, xMax, yMin, yMax, zMin, zMax);
			return this.bbox;
		}else{
			return this.bbox;
		}
	}
	
	/**
	 * Adds a camera with the given parameters to the scene.
	 * 
	 * @param position	The position of the camera.
	 * @param direction	The direction in which the camera views.
	 * @param up	The up direction of the camera.
	 * @param fovy	The field of view of the camera.
	 * @param name	The name of the camera.
	 */
	public void addCamera(Point3f position, 
			Vector3f direction, Vector3f up, float fovy, String name){
		direction.scale(1/direction.length());
		up.scale(1/up.length());
		this.cameras.put(name, new Camera(position, direction, up, fovy, name));
	}

	/**
	 * Adds a directionalLight to the scene.
	 * 
	 * @param direction	The direction of the light.
	 * @param intensity	The intensity of the light.
	 * @param color	The color of the light.
	 * @param name	The name of the directionalLight.
	 */
	public void addDirectionalLight(Vector3f direction,
			float intensity, Color3f color, String name) {
		direction.scale(1/direction.length());
		this.dirLights.put(name, new DirectionalLight(direction, intensity, color, name));
	}

	/**
	 * Adds a pointLight to the scene.
	 * 
	 * @param position	The position of the light.
	 * @param intensity	The intensity of the light.
	 * @param color	The color of the light.
	 * @param name	The name of the pointLight.
	 */
	public void addPointLight(Point3f position, float intensity,
			Color3f color, String name) {
		this.pointLights.put(name, new PointLight(position, intensity, color, name));
	}

	/**
	 * Adds a spotLight to the scene.
	 * 
	 * @param position	The position of the light.
	 * @param direction	The direction of the light.
	 * @param angle	The angle of the lightOpening
	 * @param intensity	The intensity of the light.
	 * @param color	The color of the light.
	 * @param name	The name of the SpotLight.
	 */
	public void addSpotLight(Point3f position, Vector3f direction,
			float angle, float intensity, Color3f color, String name) {
		direction.scale(1/direction.length());
		this.spotLights.put(name, new SpotLight(position, direction, angle, intensity, color, name));
	}
	
	/**
	 * Adds a Plane to the scene.
	 * 
	 * @param size	The size of the plane.
	 * @param name	The name of the plane.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addPlane(float size, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.PLANE_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a cube to the scene.
	 * 
	 * @param size	The size of the cube.
	 * @param name	The name of the cube.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addCube(float size, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.CUBE_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a sphere to the scene.
	 * 
	 * @param radius	The radius of the sphere.
	 * @param name	The name of the sphere.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addSphere(float radius, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.SPHERE_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a cylinder to the scene.
	 * 
	 * @param radius	The radius of the cylinder.
	 * @param height	The height of the cylinder.
	 * @param name	The name of the cylinder.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addCylinder(float radius, float height, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.CYLINDER_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a cone to the scene.
	 * 
	 * @param radius	The radius of the cone.
	 * @param height	The height of the cone.
	 * @param capped	Whether the bottom of the cone is closed or not.
	 * @param name	The name of the cone
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addCone(float radius, float height, boolean capped, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.CONE_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a torus to the scene.
	 * 
	 * @param innerRadius	The innerRadius of the torus.
	 * @param outerRadius	The outerRadius of the torus.
	 * @param name	The name of the torus.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addTorus(float innerRadius, float outerRadius, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.TORUS_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a Teapot to the scene.
	 * 
	 * @param size	The size of the teapot.
	 * @param name	The name of the teapot.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found
	 */
	public void addTeapot(float size, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.TEAPOT_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a bunny to the scene.
	 * 
	 * @param size	The size of the bunny.
	 * @param name	The name of the bunny.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addBunny(float size, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.BUNNY_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a venus to the scene.
	 * 
	 * @param size	The size of the venus.
	 * @param name	The name of the venus.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addVenus(float size, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.VENUS_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a triceratops to the scene.
	 * 
	 * @param size	The size of the triceratops.
	 * @param name	The name of the triceratops.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addTriceratops(float size, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.TRICERATOPS_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a table to the scene.
	 * 
	 * @param size	The size of the table.
	 * @param name	The name of the table.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addTable(float size, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.TABLE_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/**
	 * Adds a tablecloth to the scene.
	 * 
	 * @param size	The size of the tablecloth.
	 * @param name	The name of the tablecloth.
	 * @throws FileNotFoundException
	 * 		If the .obj file is not found.
	 */
	public void addTablecloth(float size, String name) throws FileNotFoundException{
		ObjectParser parser = new ObjectParser(Constants.TABLECLOTH_OBJ);
		Mesh mesh = parser.parse(name);
		this.meshes.put(name, mesh);
	}
	
	/*
	 * coordinates zijn de coordinaten van de vertices
	 * normals zijn de normalen -> OPTIONEEL dus ga hier niet vanuit
	 * textureCoordinates is een lijst van alle textuurcoordinaten die gebruikt worden in de de mesh. -> OPTIONEEL
	 * coordinateIndices bevat alle driehoeken, elke 3 opeenvolgende nummers bepaald een driehoek
	 * normalIndices  Dit attribuut definieert voor elke vertex een normaalvector. coordinateIndices[3] is de eerste vertex van de tweede driehoek. De normaal die hier bijhoort is normals[normalIndices [3]]. -> OPTIONEEL
	 * textureCoordinateIndices Dit attribuut definieert voor elke vertex een textuurcoordinaat. -> Optioneel*/
	/**
	 * Adds an indexed triangle set to the scene.
	 * 
	 * @param coordinates	The coordinates of the vertices.
	 * @param normals	The normals of the vertices.
	 * @param textureCoordinates	The texture coordinates of the vertices.
	 * @param coordinateIndices	The indices of the vertices. Each three indices represent a triangle.
	 * @param normalIndices	Defines for every vertex a normal vector.
	 * @param textureCoordinateIndices	Defines for every vertex a texture coordinate.
	 * @param name	The name of the indexed triangle set.
	 */
	public void addIndexedTriangleSet(Point3f [] coordinates, Vector3f [] normals, TexCoord2f [] textureCoordinates, int [] coordinateIndices, int [] normalIndices, int [] textureCoordinateIndices, String name){
		if(Constants.BOUNDINGBOX){
			Point3f minValuesMesh = new Point3f();
			Point3f maxValuesMesh = new Point3f();
			Functions.getExtremeXYZValues(coordinates, minValuesMesh, maxValuesMesh);
			this.meshes.put(name, new Mesh(coordinates, normals, textureCoordinates, coordinateIndices, normalIndices, textureCoordinateIndices, name, minValuesMesh, maxValuesMesh));
		} else{
			this.meshes.put(name, new Mesh(coordinates, normals, textureCoordinates, coordinateIndices, normalIndices, textureCoordinateIndices, name));
		}
	}
	
	/**
	 * Adds a texture to the scene.
	 * 
	 * @param src	The name of the textureFile.
	 * @param name	The name of the texture.
	 * @throws IOException 
	 */
	public void addTexture(String src, String name) throws IOException{
		this.textures.put(name, new Texture(src, name));
	}
	
	/**
	 * Adds a diffuse material to the scene.
	 * 
	 * @param color	The diffuse color of the material.
	 * @param name	The name of the diffuse material.
	 */
	public void addDiffuseMaterial(Color3f color, String name){
		this.materials.put(name, new DiffuseMaterial(color, name));
	}
	
	/**
	 * Adds a Phong material to the scene.
	 * 
	 * @param color	The color of the material.
	 * @param shininess	The shininess of the material.
	 * @param name	The name of the Phong material.
	 */
	public void addPhongMaterial(Color3f color, float shininess, String name){
		this.materials.put(name, new PhongMaterial(color, shininess, name));
	}
	
	/**
	 * Adds a linear combined material to the scene.
	 * 
	 * @param material1Name	The name of the first material.
	 * @param weight1	The weight of the first material in the combined material.
	 * @param material2Name The name of the second material.
	 * @param weight2	The weight of the second material in the combined material.
	 * @param name	The name of the linear combined material.
	 */
	public void addLinearCombinedMaterial(String material1Name, float weight1, String material2Name, float weight2, String name){
		this.materials.put(name, new LinearCombinedMaterial(this.materials.get(material1Name), weight1, this.materials.get(material2Name), weight2, name));
	}
	
	/**
	 * Returns the interpolated normal at the intersectionPoint.
	 * 
	 * @param triangle	The triangle in which the intersectionPoint lies.
	 * @param a		The first vertex of the triangle.
	 * @param b		The second vertex of the triangle.
	 * @param c		The third vertex of the triangle.
	 * @param intersectionPoint		The intersectionPoint.
	 * @return	Vector3f
	 * 		The interpolated normal at the intersectionPoint.
	 */
	protected Vector3f getIntersectionNormal(Triangle triangle, Vertex a, Vertex b, Vertex c, Point3f intersectionPoint) {
		Vector3f barycentricCoordinates = getBarycentricCoordinates3D(triangle, a, b, c, intersectionPoint);
		Vector3f aNormal = a.normal;
		Vector3f bNormal = b.normal;
		Vector3f cNormal = c.normal;
		Vector3f normal = new Vector3f();
		normal.scaleSet(barycentricCoordinates.x, aNormal);
		normal.scaleAdd(barycentricCoordinates.y, bNormal, normal);
		normal.scaleAdd(barycentricCoordinates.z, cNormal, normal);
		//Make normal a unit vector
		normal.scale(1/normal.length());
		return normal;
	}

	/**
	 * Returns a vector with the barycentricCoordinates of the intersectionPoint in the given triangle.
	 * 
	 * @param triangle	The triangle in which the intersectionpoint lies.
	 * @param a		The first vertex of the triangle.
	 * @param b		The second vertex of the triangle.
	 * @param c		The third vertex of the triangle.
	 * @param intersectionPoint		The intersectionPoint.
	 * @return	Vector3f
	 * 		The barycentric coordinates of the intersectionPoint.
	 */
	protected Vector3f getBarycentricCoordinates3D(Triangle triangle, Vertex a, Vertex b, Vertex c, Point3f intersectionPoint) {
		// normal = n = (a-b)x(a-c)
		Vector3f n = triangle.getNormal();
		float lengthSquared = n.lengthSquared();
		// alpha = (n*n_a)/(||n||^2)
		float alpha = getBaryCentricCoordinate(n, lengthSquared, c.coord, b.coord, intersectionPoint);
		// beta = (n*n_b)/(||n||^2)
		float beta = getBaryCentricCoordinate(n, lengthSquared, a.coord, c.coord, intersectionPoint);
		// gamma = (n*n_c)/(||n||^2)
		float gamma = getBaryCentricCoordinate(n, lengthSquared, b.coord, a.coord, intersectionPoint);
		return new Vector3f(alpha, beta, gamma);
	}
	
	/**
	 * Returns the baryCentricCoordinate (depending on point one and two) of the point p.
	 * 
	 * @param n	The normal of the triangle.
	 * @param lengthSquared	The squared length of the normal of the triangle.
	 * @param one	The first point.
	 * @param two	The second point.
	 * @param p		The point to calculate the barycentric coordinate for.
	 * @return	float
	 * 		The calculated barycentriccoordinate.
	 */
	protected float getBaryCentricCoordinate(Vector3f n, float lengthSquared, Point3f one,
			Point3f two, Point3f p) {
		Vector3f twoMinOne = new Vector3f();
		Vector3f twoMinP = new Vector3f();
		Vector3f n_Temp = new Vector3f();
		
		twoMinOne.substractSet(two, one);
		twoMinP.substractSet(two, p);
		n_Temp.cross(twoMinOne, twoMinP);
		n_Temp.scale(1/n_Temp.length());
		return (n_Temp.dotProduct(n))/lengthSquared;
	}

	/**
	 * Adds a LeafNode to the sceneGraph.
	 * 
	 * @param geometryName	The name of the geometry the leafNode is for.
	 * @param materialName	The name of the material of the leafNode.
	 * @param textureName	The name of the texture of the leafNode.
	 * @param localTransformMatrix	The local transform matrix of the leafNode.
	 * @param root	The root SceneGraphElement of the leafNode.
	 */
	public void addLeafNode(String geometryName, String materialName,
			String textureName, Matrix4f localTransformMatrix,
			SceneGraphElement root) {
		LeafNode node;
		if(textureName == null){
			node = new LeafNode(geometryName, materialName, localTransformMatrix, root, meshes.get(geometryName));
		} else{
			node = new LeafNode(geometryName, materialName, textureName, localTransformMatrix, root, meshes.get(geometryName));
		}
		sceneGraph.addLeafNode(geometryName, node);
	}

	/**
	 * Adds a GroupNode to the sceneGraph.
	 * 
	 * @param groupNode	The groupNode to add.
	 */
	public void addGroupNode(GroupNode groupNode) {
		sceneGraph.addGroupNode(groupNode.getName(), groupNode);
	}

	/* (non-Javadoc)
	 * @see sceneModel.Surface#hit(ray_Tracing.Ray, float, float)
	 */
	@Override
	public IntersectionRecord hit(Ray ray, float t0, float t1) {
		IntersectionRecord intersectionRecord = null;
		IntersectionRecord temp = null;
		float currentFoundT = t1;
		for (LeafNode leaf : this.sceneGraph.getLeafs()) {
			Ray inverseTransformRay = ray.inverseTransformRay(leaf.getTotalTransformMatrix());
			temp = leaf.getSurface().hit(inverseTransformRay, t0, currentFoundT);
			if(temp != null){
				intersectionRecord = temp;
				currentFoundT = intersectionRecord.currentT;
			}
		}
		return intersectionRecord;
	}
	
	/**
	 * Returns the first occurred intersection using the given ray and t arguments.
	 * 
	 * @param ray	The ray to intersect.
	 * @param t0	The minimum value of the intersection.
	 * @param t1	The maximum value of the intersection.
	 * @return	IntersectionRecord
	 * 		The first occurred intersection.
	 */
	public IntersectionRecord hitClosest(Ray ray, float t0, float t1) {
		IntersectionRecord temp = null;
		float currentFoundT = t1;
		for (LeafNode leaf : this.sceneGraph.getLeafs()) {
			Ray transformedShadowRay = ray.inverseTransformRay(leaf.getTotalTransformMatrix());
			temp = leaf.getSurface().hit(transformedShadowRay, t0, currentFoundT);
			if(temp != null){
				return temp;
			}
		}
		return null;
	}
	
	
	/** 
	 * Returns the leafNode with the given name.
	 * @return
	 */
	public LeafNode getLeafNode(String leafName){
		return this.sceneGraph.getLeafNode(leafName);
	}
	
	/**
	 * Returns the cameras of this scene.
	 * @return
	 */
	public HashMap<String, Camera> getCameras() {
		return cameras;
	}

	/**
	 * Returns the camera with the given name.
	 * 
	 * @param camera	The name of the camera to retrieve.
	 * @return	Camera
	 * 		The camera with the given name.
	 */
	public Camera getCamera(String camera){
		return this.cameras.get(camera);
	}

	/**
	 * Returns the directionalLights of this scene.
	 * @return
	 */
	public HashMap<String, DirectionalLight> getDirLights() {
		return dirLights;
	}

	/**
	 * Returns the PointLights of this scene.
	 * @return
	 */
	public HashMap<String, PointLight> getPointLights() {
		return pointLights;
	}

	/**
	 * Returns the SpotLights of this scene.
	 * @return
	 */
	public HashMap<String, SpotLight> getSpotLights() {
		return spotLights;
	}

	/**
	 * Returns the meshes of this scene.
	 * @return
	 */
	public HashMap<String, Mesh> getMeshes() {
		return meshes;
	}

	/**
	 * Returns the materials of this scene.
	 * @return
	 */
	public HashMap<String, Material> getMaterials() {
		return materials;
	}

	/**
	 * Returns the textures of this scene.
	 * @return
	 */
	public HashMap<String, Texture> getTextures() {
		return textures;
	}

	/**
	 * Returns the linkedGeometries of this scene.
	 * @return
	 */
	public HashMap<String, String[]> getLinkedGeometries() {
		return linkedGeometries;
	}

	/**
	 * Returns the background color of the scene.
	 * @return
	 */
	public Color3f getBackground() {
		return background;
	}

	/**
	 * Returns the name of the active camera of the scene.
	 * @return
	 */
	public String getActiveCameraName() {
		return activeCamera;
	}
	
	/**
	 * Returns the active Camera of the scene.
	 * @return
	 */
	public Camera getActiveCamera(){
		return this.cameras.get(this.activeCamera);
	}

	/**
	 * Returns the names of the active lights of the scene.
	 * @return
	 */
	public String[] getActiveLights() {
		return activeLights;
	}
	
	/**
	 * Returns the active PointLights of the scene.
	 * @return
	 */
	public Collection<PointLight> getActivePointLights(){
		Collection<PointLight> activePointLights = new ArrayList<PointLight>();
		for (String pointLight : getActiveLights()) {
			activePointLights.add(this.pointLights.get(pointLight));
		}
		return activePointLights;
	}

	/**
	 * Returns the value of the left edge of the image.
	 * @return
	 */
	public float getPositionLeftEdgeImage() {
		return positionLeftEdgeImage;
	}

	/**
	 * Returns the value of the right edge of the image.
	 * @return
	 */
	public float getPositionRightEdgeImage() {
		return positionRightEdgeImage;
	}

	/**
	 * Returns the value of the top edge of the image.
	 * @return
	 */
	public float getPositionTopEdgeImage() {
		return positionTopEdgeImage;
	}

	/**
	 * Returns the value of the bottom edge of the image.
	 * @return
	 */
	public float getPositionBottomEdgeImage() {
		return positionBottomEdgeImage;
	}

	/**
	 * Returns the sceneGraph of this scene.
	 * @return
	 */
	public SceneGraph getSceneGraph() {
		return sceneGraph;
	}
	
	public void addMovingSphere1(){
		LeafNode sphere = this.sceneGraph.getLeafNode("movingSphere1");
		MovingLeafNode movingSphere = new MovingLeafNode(sphere.getName(), sphere.getMaterialName(), sphere.getLocalTransformMatrix(), sphere.getRoot(), sphere.getSurface(), 1f, new Vector3f(0,1,0));
		this.sceneGraph.removeLeafNode(sphere.getName());
		this.sceneGraph.addLeafNode(movingSphere.getName(), movingSphere);
	}
	
	public void addMovingSphere2(){
		LeafNode sphere = this.sceneGraph.getLeafNode("movingSphere2");
		MovingLeafNode movingSphere = new MovingLeafNode(sphere.getName(), sphere.getMaterialName(), sphere.getLocalTransformMatrix(), sphere.getRoot(), sphere.getSurface(), 2f, new Vector3f(1,0,0));
		this.sceneGraph.removeLeafNode(sphere.getName());
		this.sceneGraph.addLeafNode(movingSphere.getName(), movingSphere);
	}

	public IntersectionRecord hitAtTime(Ray ray, float t0, float t1, float time) {
		IntersectionRecord intersectionRecord = null;
		IntersectionRecord temp = null;
		float currentFoundT = t1;
		for (LeafNode leaf : this.sceneGraph.getLeafs()) {
			Ray inverseTransformRay;
			if(leaf instanceof MovingLeafNode){
				MovingLeafNode movingLeaf = (MovingLeafNode) leaf;
				Matrix4f transformMatrix = movingLeaf.getTotalTransformMatrix(time);
				inverseTransformRay = ray.inverseTransformRay(transformMatrix);
			} else{
				inverseTransformRay = ray.inverseTransformRay(leaf.getTotalTransformMatrix());
			}
			temp = leaf.getSurface().hit(inverseTransformRay, t0, currentFoundT);
			if(temp != null){
				intersectionRecord = temp;
				currentFoundT = intersectionRecord.currentT;
			}
		}
		return intersectionRecord;
	}
	
	public void addGlobalMaterials(List<GlobalMaterial> materials){
		GlobalMaterial material;
		for (int i = 0; i < materials.size(); i++) {
			material = materials.get(i);
			this.materials.put(material.getName(), material);
		}
	}

	public void addGlobalMaterial(String name, Color3f ambientColor, Color3f diffuseColor, Color3f spectralColor,
			float phongExponent, float reflectionCoeff) {
		this.materials.put(name, new GlobalMaterial(name, ambientColor, diffuseColor, spectralColor, phongExponent, reflectionCoeff));
	}
	
}
