package rasterization;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Mesh;
import indexed_Neighbour_Triangle_Mesh_With_Edges.Triangle;
import indexed_Neighbour_Triangle_Mesh_With_Edges.Vertex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;

import main.Constants;
import sceneModel.Camera;
import sceneModel.Material;
import sceneModel.PointLight;
import sceneModel.sceneGraph.BoundingBox;
import abstractModel.CgPanel;
import abstractModel.Color3f;
import abstractModel.Matrix3f;
import abstractModel.Matrix4f;
import abstractModel.Point3f;
import abstractModel.Point4f;
import abstractModel.Scene;
import abstractModel.SceneBuilder;
import abstractModel.Vector3f;

/**
 * Class that implements all the methods needed to rasterize a Scene on in Image.
 * 
 * @author Geert Van Campenhout
 */
public class Rasterization {
	
	/**
	 * Rasterizes the triangle given by its vertices with the given arguments.
	 * 
	 * @param a	The first vertex of the triangle.
	 * @param b The second vertex of the triangle.
	 * @param c The third vertex of the triangle.
	 * @param aOriginalCoord	The original coordinate of the first vertex of the triangle.
	 * @param bOriginalCoord	The original coordinate of the second vertex of the triangle.
	 * @param cOriginalCoord	The original coordinate of the third vertex of the triangle.
	 * @param aColor	The color of the first vertex of the triangle.
	 * @param bColor	The color of the second vertex of the triangle.
	 * @param cColor	The color of the third vertex of the triangle.
	 * @param material	The material of the triangle.
	 * @param pointLights	The PointLights to light the image.
	 * @param camera	The camera of the image.
	 */
	public void rasterizeTriangle(Vertex a, Vertex b, Vertex c, Point3f aOriginalCoord, Point3f bOriginalCoord, Point3f cOriginalCoord, Color3f aColor, Color3f bColor, Color3f cColor, Material material, Collection<PointLight> pointLights, Camera camera){

		int xMin = (int) Math.floor(Math.min(a.coord.x, Math.min(b.coord.x, c.coord.x)));
		int xMax = (int) Math.ceil(Math.max(a.coord.x, Math.max(b.coord.x, c.coord.x)));
		int yMin = (int) Math.floor(Math.min(a.coord.y, Math.min(b.coord.y, c.coord.y)));
		int yMax = (int) Math.ceil(Math.max(a.coord.y, Math.max(b.coord.y, c.coord.y)));
		xMin = xMin<0 ? 0 : xMin;
		yMin = yMin<0 ? 0 : yMin;
		xMax = xMax>=Constants.NBOFHORIZONTALPIXELS ? Constants.NBOFHORIZONTALPIXELS-1 : xMax;
		yMax = yMax>=Constants.NBOFVERTICALPIXELS ? Constants.NBOFVERTICALPIXELS-1 : yMax;
		float fAlpha = calcFxy(b, c, a.coord.x, a.coord.y);
		float fBeta = calcFxy(c, a, b.coord.x, b.coord.y);
		float fGamma = calcFxy(a, b, c.coord.x, c.coord.y);
		for (int x = xMin; x < xMax+1; x++) {
			for (int y = yMin; y < yMax+1; y++) {
				float alpha = calcFxy(b, c, x, y)/fAlpha;
				float beta = calcFxy(c, a, x, y)/fBeta;
				float gamma = calcFxy(a, b, x, y)/fGamma;
				if(alpha >= 0 && beta >= 0 && gamma >= 0){
					if((alpha > 0 || (fAlpha*calcFxy(b, c, -2,-1)) > 0) && 
							(beta > 0 || (fBeta*calcFxy(c, a, -2, -1)) > 0) && 
							(gamma > 0 || (fGamma*calcFxy(a, b, -2, -1)) > 0)){
						Point3f intersectionPoint = Triangle.getPointAt(a.coord, b.coord, c.coord, beta, gamma);
						double zDepth = inversePerspectiveTransZCoordinate(intersectionPoint.z);
						if(this.zBuffer.getDepthAt(x, y) - zDepth < 0){
							
							Color3f pixelColor = new Color3f();
							for (PointLight pointLight : pointLights) {

								Vector3f surfaceNormal = Triangle.getInterpolatedNormalizedNormal(a, b, c, intersectionPoint);
								pixelColor.add(material.shade(surfaceNormal, Triangle.getPointAt(aOriginalCoord, bOriginalCoord, cOriginalCoord, beta, gamma), pointLight, camera.position));
							}
							
							zBuffer.setEntryAt(x, y, pixelColor, zDepth);
						}
					}
				}
			}
		}
	}
	
	private double inversePerspectiveTransZCoordinate(float z){
		return -(far*near)/(z-near-far);
	}
	
	private Color3f getInterpolatedColorAt(float alpha, float beta,
			float gamma, Vertex a, Vertex b, Vertex c, Point3f aOriginalCoord, Point3f bOriginalCoord, Point3f cOriginalCoord, Material material, PointLight pointLight,
			Point3f position) {
//		Point3f intersectionPoint = getTrianglePointAt(alpha, beta, gamma, a, b, c);
		Color3f aColor = material.shade(a.normal, aOriginalCoord, pointLight, position);
		Color3f bColor = material.shade(b.normal, bOriginalCoord, pointLight, position);
		Color3f cColor = material.shade(c.normal, cOriginalCoord, pointLight, position);
		return new Color3f(
				alpha*aColor.x + beta*bColor.x + gamma*cColor.x,
				alpha*aColor.y + beta*bColor.y + gamma*cColor.y,
				alpha*aColor.z + beta*bColor.z + gamma*cColor.z);
	}

	private float calcFxy(Vertex first, Vertex second, float x, float y){
		return ((first.coord.y-second.coord.y)*x)+((second.coord.x-first.coord.x)*y)+(first.coord.x*second.coord.y)-(second.coord.x*first.coord.y);
	}
	
	private List<Vertex[]> clipTriangleAgainstViewVolume(Vertex a, Vertex b, Vertex c, float l, float r, float bot, float t, float n, float f) {
		final Vector3f[] viewVolumePlaneNormals = new Vector3f[] {
				/*leftNormal*/ new Vector3f(n*(t-bot), 0, l*(bot-t)),
				/*rightNormal*/ new Vector3f(n*(t-bot), 0, r*(bot-t)),
				/*topNormal*/ new Vector3f(0, -n*(r-l), -t*(l-r)),
				/*bottomNormal*/ new Vector3f(0, -n*(r-l), -bot*(l-r)),
				/*nearNormal*/ new Vector3f(0, 0, 1),
				/*farNormal*/ new Vector3f(0, 0, 1)
		};
		final float[] viewVolumePlaneD = new float[] {
				/*leftDValue*/ viewVolumePlaneNormals[0].dotProduct(new Vector3f(0, 0, 0)),
				/*rightDValue*/ viewVolumePlaneNormals[1].dotProduct(new Vector3f(0, 0, 0)),
				/*topDValue*/ viewVolumePlaneNormals[2].dotProduct(new Vector3f(0, 0, 0)),
				/*bottomDValue*/ viewVolumePlaneNormals[3].dotProduct(new Vector3f(0, 0, 0)),
				/*nearDValue*/ viewVolumePlaneNormals[0].dotProduct(new Vector3f(0, 0, n)),
				/*farDValue*/ viewVolumePlaneNormals[0].dotProduct(new Vector3f(0, 0, f))
		};
		List<Vertex[]> triangles = new ArrayList<Vertex[]>();
		triangles.add(new Vertex[] {a, b, c});
		for (int i = 0; i < 6; i++) {
			triangles = clipTrianglesAgainstPlane(triangles, viewVolumePlaneD[i], viewVolumePlaneNormals[i]);
		}
		return triangles;
	}
	
	private List<Vertex[]> clipTrianglesAgainstPlane(List<Vertex[]> triangles, float D, Vector3f planeNormal){
		List<Vertex[]> clippedTriangles = new ArrayList<Vertex[]>();
		for (Vertex[] vertexs : triangles) {
			final float eps = 0.0001f;
			Point3f a = new Point3f(vertexs[0].coord);
			Point3f b = new Point3f(vertexs[1].coord);
			Point3f c = new Point3f(vertexs[2].coord);
			float fa = checkPlaneEquation(D, planeNormal, a);
			float fb = checkPlaneEquation(D, planeNormal, b);
			float fc = checkPlaneEquation(D, planeNormal, c);
			if(Math.abs(fa) < eps)
				fa = 0;
			if(Math.abs(fb) < eps)
				fb = 0;
			if(Math.abs(fc) < eps)
				fc = 0;
			if(fa < 0 && fb < 0 && fc < 0){
				//Normaal ligt de driehoek nu buiten het veld.
			} else if(fa >= 0 && fb >= 0 && fc >= 0){
				//Normaal ligt de driehoek nu in het veld.
				clippedTriangles.add(vertexs);
			} else{
				float tmp;
				Point3f temp;
				if((fa*fc) >= 0){
					tmp = fb;
					fb = fc;
					fc = tmp;
					temp = new Point3f(b);
					b = new Point3f(c);
					c = temp;
					tmp = fa;
					fa = fb;
					fb = tmp;
					temp = new Point3f(a);
					a = new Point3f(b);
					b = temp;
				} else if((fb*fc) >= 0){
					tmp = fa;
					fa = fc;
					fc = tmp;
					temp = new Point3f(a);
					a = new Point3f(c);
					c = temp;
					tmp = fa;
					fa = fb;
					fb = tmp;
					temp = new Point3f(a);
					a = new Point3f(b);
					b = temp;
				}
				Point3f A = new Point3f();
				Vector3f cMina = new Vector3f();
				cMina.substractSet(c, a);
				float tA = -(planeNormal.dotProduct(new Vector3f(a)) + D)/(planeNormal.dotProduct(cMina));
				cMina.scale(tA);
				A.addSet(a, cMina);
				Point3f B = new Point3f();
				Vector3f cMinb = new Vector3f();
				cMinb.substractSet(c, b);
				float tB = -(planeNormal.dotProduct(new Vector3f(b)) + D)/(planeNormal.dotProduct(cMinb));
				cMinb.scale(tB);
				B.addSet(b, cMinb);
				//Calc 3 triangles
				//Doe er iets mee
				//T1 = (a,b,A)
				//T2 = (b,B,A)
				//T3 = (A,B,c)
				if(fc >= 0){
					//T1 en T2 liggen buiten het veld.
					//T3 ligt binnen het veld.
					clippedTriangles.add(new Vertex[] {new Vertex(0, A), new Vertex(1, B), new Vertex(2, c)});
				} else{
					//T1 en T2 liggen binnen het veld.
					clippedTriangles.add(new Vertex[] {new Vertex(0, a), new Vertex(1, b), new Vertex(2, A)});
					clippedTriangles.add(new Vertex[] {new Vertex(0, b), new Vertex(1, B), new Vertex(2, A)});
					//T3 ligt buiten het veld.
				}
			}
		}
		return clippedTriangles;
	}
	
	private float checkPlaneEquation(float D, Vector3f normal, Point3f checkPoint){
		return normal.dotProduct(new Vector3f(checkPoint))+D;
	}
	
	
	/**
	 * NOTE: Na deze matrix te hebben toegepast moet je de coordinaten delen door het homogene vierde coordinaat
	 * vooralleer de coordinaten toe te passen.
	 * 
	 * @param cam
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param near
	 * @param far
	 * @return
	 */
	private Matrix4f getTotalTransformMatrix(Camera cam){
		Matrix4f totalTransformMatrix = new Matrix4f();
		totalTransformMatrix.multiplyRight(getViewPortMatrix(), getPerspectiveProjectionMatrix());
		totalTransformMatrix = totalTransformMatrix.multiplyRight(getCameraTranformationMatrix(cam));
		return totalTransformMatrix;
	}
	
	private Matrix4f getViewPortMatrix(){
		float nx = Constants.NBOFHORIZONTALPIXELS;
		float ny = Constants.NBOFVERTICALPIXELS;
		return new Matrix4f(
				(nx/2), 0, 0, ((nx-1)/2), 
				0, (ny/2), 0, ((ny-1)/2), 
				0, 0, 1, 0, 
				0, 0, 0, 1);
	}
	
	private Matrix4f getOrthographicProjectionTransformationMatrix(){
		return new Matrix4f(
				(2/(right-left)), 0, 0, (-(right+left)/(right-left)), 
				0, (2/(top-bot)), 0, (-(top+bot)/(top-bot)), 
				0, 0, (2/(near-far)), (-(near+far)/(near-far)), 
				0, 0, 0, 1);
	}
	
	private Matrix4f getCameraTranformationMatrix(Camera cam){
		Vector3f negatedViewDirection = new Vector3f();
		negatedViewDirection.negate(cam.direction);
		Matrix4f basisMatrix = new Matrix4f(createBasis(negatedViewDirection, cam.up)).transpose();
		Matrix4f moveOriginMatrix = new Matrix4f(
				1, 0, 0, -cam.position.x, 
				0, 1, 0, -cam.position.y, 
				0, 0, 1, -cam.position.z, 
				0, 0, 0, 1);
		Matrix4f productMatrix = new Matrix4f();
		productMatrix.multiplyRight(basisMatrix, moveOriginMatrix);
		return productMatrix;
	}
	
	private Matrix4f getPerspectiveProjectionMatrix(){
		Matrix4f P = new Matrix4f(
				near, 0, 0, 0, 
				0, near, 0, 0, 
				0, 0, near+far, -(far*near), 
				0, 0, 1, 0);
		Matrix4f perspectiveProjectionMatrix = new Matrix4f();
		perspectiveProjectionMatrix.multiplyRight(getOrthographicProjectionTransformationMatrix(), P);
		return perspectiveProjectionMatrix;
	}
	
	private static Matrix3f createBasis(Vector3f startVector, Vector3f nonCollinearVector) {
		Vector3f w = new Vector3f(startVector);
    	w.normalize();
    	Vector3f t = new Vector3f(nonCollinearVector);
    	Vector3f u = new Vector3f();
    	u.cross(t, w);
    	u.normalize();
    	Vector3f v = new Vector3f();
    	v.cross(w, u);
    	v.normalize();
		
    	Matrix3f uvw = new Matrix3f(u.x, v.x, w.x, 
    								u.y, v.y, w.y, 
    								u.z, v.z, w.z);
    	System.out.println("uvw: " + uvw);
		return uvw;
	}
	
	/**
	 * Rasterizes the given scene on the image.
	 * 
	 * @param scene	The scene to rasterize.
	 */
	public void rasterizeScene(Scene scene){
		Camera camera = scene.getCamera(scene.getActiveCameraName());
		Matrix4f transformMatrix = getTotalTransformMatrix(camera);
		Collection<PointLight> pointLights = scene.getActivePointLights();
		for (Mesh mesh : scene.getMeshes().values()) {
			Triangle[] triangles = mesh.getTriangles();
			Vertex[] vertices = mesh.getVertices();
			String materialName = scene.getLeafNode(mesh.getName()).getMaterialName();
			Material material = scene.getMaterials().get(materialName);
			for (int i = 0; i < triangles.length; i++) {
				Vertex a = vertices[triangles[i].vertex[0]];
				Vertex b = vertices[triangles[i].vertex[1]];
				Vertex c = vertices[triangles[i].vertex[2]];
				Point3f aOriginalCoord = new Point3f(a.coord);
				Point3f bOriginalCoord = new Point3f(b.coord);
				Point3f cOriginalCoord = new Point3f(c.coord);
				Color3f aColor = new Color3f();
				Color3f bColor = new Color3f();
				Color3f cColor = new Color3f();
				for (PointLight pointLight : pointLights) {
					aColor.add(material.shade(a.normal, a.coord, pointLight, camera.position));
					bColor.add(material.shade(b.normal, b.coord, pointLight, camera.position));
					cColor.add(material.shade(c.normal, c.coord, pointLight, camera.position));
				}
				transformTriangle(transformMatrix, a, b, c);
				rasterizeTriangle(a, b, c, aOriginalCoord, bOriginalCoord, cOriginalCoord, aColor, bColor, cColor, material, pointLights, camera);
			}
		}
	}

	private void transformTriangle(Matrix4f transMatrix, Vertex a, Vertex b, Vertex c) {
		Point4f aCoord = transMatrix.vectorMult(new Point4f(a.coord));
		Point4f bCoord = transMatrix.vectorMult(new Point4f(b.coord));
		Point4f cCoord = transMatrix.vectorMult(new Point4f(c.coord));
		a.coord = aCoord.convertTo3f();
		b.coord = bCoord.convertTo3f();
		c.coord = cCoord.convertTo3f();
	}
	
	private float calculateViewVolumeFar(Scene scene){
		BoundingBox sceneBox = scene.getSceneBoundingBox();
		Point3f minValues = sceneBox.getMinValues();
		Point3f maxValues = sceneBox.getMaxValues();
		Matrix4f camTransformMatrix = getCameraTranformationMatrix(scene.getCamera(scene.getActiveCameraName()));
		Point4f minCoord4f = camTransformMatrix.vectorMult(new Point4f(minValues));
		Point4f maxCoord4f = camTransformMatrix.vectorMult(new Point4f(maxValues));
		Point3f minCoord3f = minCoord4f.convertTo3f();
		Point3f maxCoord3f = maxCoord4f.convertTo3f();
		float far = minCoord3f.z < maxCoord3f.z ? minCoord3f.z : maxCoord3f.z;
		if(near == far)
			far-= 1;
		return far;
	}
	
	public static void main(String[] args) {
		new Rasterization(Constants.FILENAME);
	}
	
	private CgPanel imagePanel;
	private ZBuffer zBuffer;
	private final float left;
	private final float right;
	private final float top;
	private final float bot;
	private final float near;
	private final float far;
	
	/**
	 * Creates a new Rasterization for the given fileName.
	 * 
	 * @param fileName	The name of the file representing the scene to rasterize.
	 */
	public Rasterization(String fileName){
		Scene scene = createScene(fileName);
		this.left = scene.getPositionLeftEdgeImage();
		this.right = scene.getPositionRightEdgeImage();
		this.top = scene.getPositionBottomEdgeImage();
		this.bot = scene.getPositionTopEdgeImage();
		
		final long calcStartTime = System.currentTimeMillis();
		
		double angle = getAngleBetweenZAxisAndViewDirection(scene.getCamera(scene.getActiveCameraName()));
		this.near = -Constants.DISTANCETOSCREEN;
		this.far = calculateViewVolumeFar(scene);
		System.out.println(this.far);
		imagePanel = initFrameAndImagePanel();
		zBuffer = new ZBuffer(Constants.NBOFHORIZONTALPIXELS, Constants.NBOFVERTICALPIXELS, scene.getBackground(), far);
		rasterizeScene(scene);
		drawImage();
		final long calcStopTime = System.currentTimeMillis();
		final long duration = calcStopTime - calcStartTime;
		System.out.println("Scene visualized in " + duration + " msec");
		this.imagePanel.saveImage(Constants.SAVE_FILE_NAME);
	}
	
	private double getAngleBetweenZAxisAndViewDirection(Camera cam) {
		Vector3f view = cam.direction;
		double angle = view.angle(new Vector3f(0,0,-1));
		System.out.println("cos(angle): " + Math.cos(angle));
		return angle;
	}

	private void drawImage() {
		for (int i = 0; i < Constants.NBOFHORIZONTALPIXELS; i++) {
			for (int j = 0; j < Constants.NBOFVERTICALPIXELS; j++) {
				Color3f colorXY = zBuffer.getColorAt(i, j);
				imagePanel.drawPixel(i, j, colorXY.x, colorXY.y, colorXY.z);
			}
		}
		imagePanel.flush();
	}

	/**
	 * Creates the scene found in the given file.
	 * 
	 * @param fileName	The given file name.	
	 * @return	Scene
	 * 		The created scene.
	 */
	private Scene createScene(String fileName) {
		System.out.println("Creating the scene.. " + 0);
		final long startTime = System.currentTimeMillis();
		Scene scene = null;
		try {
			SceneBuilder sceneBuilder = new SceneBuilder();
			scene = sceneBuilder.loadScene(fileName);
		}
		catch (Exception e) {
			System.err.println("Error while creating the scene.");
			e.printStackTrace();
		}
		final long sceneTime = System.currentTimeMillis();
		long duration = sceneTime - startTime;
		System.out.println("Scene built in " + duration + " msec");
		return scene;
	}
	
	/**
	 * Initializes the JFrame in which the image will be shown.
	 * 
	 * @param panel	The panel that shows the image.
	 */
	private CgPanel initFrameAndImagePanel() {
		CgPanel panel = new CgPanel();
		panel.setSize(Constants.NBOFHORIZONTALPIXELS, Constants.NBOFVERTICALPIXELS);
		panel.setPreferredSize(new Dimension(Constants.NBOFHORIZONTALPIXELS, Constants.NBOFVERTICALPIXELS));
		JFrame frame = new JFrame();
		Dimension dim = panel.getSize();
		int width = dim.width;
		int height = dim.height;
		panel.setSize(new Dimension(width, height));
		panel.setMinimumSize(new Dimension(width, height));
		panel.setPreferredSize(new Dimension(width, height));
		frame.setSize(new Dimension(width+20, height+46));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
			@Override
			public void windowClosed(WindowEvent arg0) {}
			@Override
			public void windowActivated(WindowEvent arg0) {}
		});
		frame.setVisible(true);
		return panel;
	}
}
