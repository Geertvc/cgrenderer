package abstractModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;

import main.Constants;

import org.xml.sax.InputSource;

import sceneModel.sceneGraph.GroupNode;
import sceneModel.sceneGraph.LeafNode;
import sceneModel.sceneGraph.SceneGraph;
import sceneModel.sceneGraph.SceneGraphElement;
import abstractModel.parser.Parser;
import abstractModel.parser.ParserHandler;

/**
  * Class used to build a scene from a given sdl file.
  * Implements the ParserHandler interface (these methods
  * need to be filled in by you).
  * 
  * Note that this class keeps the absolute path to the
  * directory where the sdl file was found.  If you put your
  * textures in the same directory, you can use this path
  * to construct an absolute file name for each texture.
  * You will probably need absolute file names when loading
  * the texture.
  */
public class SceneBuilder implements ParserHandler
{

    // the scene being build
    private Scene scene = null;

    // the path to the xml directory
    // this path can be used to put in front of the texture file name
    // to load the textures
    private String path = null;

    public String getPath() { return path; }


    /**
     * Load a scene.
     * @param filename The name of the file that contains the scene.
     * @return The scene, or null if something went wrong.
     * @throws FileNotFoundException The file could not be found.
     */
    public Scene loadScene(String filename) throws FileNotFoundException
    {
        // create file and file input stream
        File file = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(file);

        // set the system id so that the dtd can be a relative path
        // the first 2 lines of your sdl file should always be
        //    <?xml version='1.0' encoding='utf-8'?>
        //    <!DOCTYPE Sdl SYSTEM "sdl.dtd">
        // and sdl.dtd should be in the same directory as the dtd
        // if you experience dtd problems, commend the doctype declaration
        //    <!-- <!DOCTYPE Sdl SYSTEM "sdl.dtd"> -->
        // and disable validation (see further)
        // although this is in general not a good idea

        InputSource inputSource = new InputSource(fileInputStream);
        path = file.getParentFile().getAbsolutePath() + "/";
        inputSource.setSystemId("file:///" + file.getParentFile().getAbsolutePath() + "/");



        // create the new scene
        scene = new Scene();

        // create the parser and parse the input file
        Parser parser = new Parser();
        parser.setHandler(this);

        // if the output bothers you, set echo to false
        // also, if loading a large file (with lots of triangles), set echo to false
        // you should leave validate to true
        // if the docuement is not validated, the parser will not detect syntax errors
        if (parser.parse(inputSource, /* validate */ true, /* echo */ false) == false)
        {
            scene = null;
        }

        // return the scene
        return scene;
    }
    
    /**
     * Main method to test the sceneBuilder.
     */
    public static void main(String[] args) {
    	System.out.println("Creating the scene.. " + 0);
		final long startTime = System.currentTimeMillis();
		Scene scene = null;
		try {
			SceneBuilder sceneBuilder = new SceneBuilder();
			scene = sceneBuilder.loadScene(Constants.FILENAME);
		}
		catch (Exception e) {
			System.err.println("Error while creating the scene.");
			e.printStackTrace();
		}
		final long sceneTime = System.currentTimeMillis();
		long duration = sceneTime - startTime;
		System.out.println("Scene built in " + duration + " msec");
		
		SceneGraph sceneGraph = scene.sceneGraph;
		for (LeafNode leaf : sceneGraph.getLeafs()) {
			System.out.println("Leaf : " + leaf.getName());
			if(leaf.getRoot() != null)
				System.out.println("Has root: " + leaf.getRoot().getName());
			System.out.println("TotalTransformMatrix : \n" + leaf.getTotalTransformMatrix());
		}
	}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startSdl()
     */
    public void startSdl() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endSdl()
     */
    public void endSdl() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startCameras()
     */
    public void startCameras() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endCameras()
     */
    public void endCameras() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startCamera(abstractModel.Point3f, abstractModel.Vector3f, abstractModel.Vector3f, float, java.lang.String)
     */
    public void startCamera(Point3f position, Vector3f direction, Vector3f up, float fovy, String name) throws Exception {
    	//Create Camera in the scene
    	this.scene.addCamera(position, direction, up, fovy, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endCamera()
     */
    public void endCamera() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startLights()
     */
    public void startLights() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endLights()
     */
    public void endLights() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startDirectionalLight(abstractModel.Vector3f, float, abstractModel.Color3f, java.lang.String)
     */
    public void startDirectionalLight(Vector3f direction, float intensity, Color3f color, String name) throws Exception {
    	//Create DirectionalLight in the scene
    	this.scene.addDirectionalLight(direction, intensity, color, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endDirectionalLight()
     */
    public void endDirectionalLight() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startPointLight(abstractModel.Point3f, float, abstractModel.Color3f, java.lang.String)
     */
    public void startPointLight(Point3f position, float intensity, Color3f color, String name) throws Exception {
    	//Create PointLight in the scene 
    	this.scene.addPointLight(position, intensity, color, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endPointLight()
     */
    public void endPointLight() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startSpotLight(abstractModel.Point3f, abstractModel.Vector3f, float, float, abstractModel.Color3f, java.lang.String)
     */
    public void startSpotLight(Point3f position, Vector3f direction, float angle, float intensity, Color3f color, String name) throws Exception {
    	//Create SpotLight in the scene
    	this.scene.addSpotLight(position, direction, angle, intensity, color, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endSpotLight()
     */
    public void endSpotLight() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startGeometry()
     */
    public void startGeometry() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endGeometry()
     */
    public void endGeometry() throws Exception {}

	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#startCube(float, java.lang.String)
	 */
	@Override
	public void startPlane(float size, String name) throws Exception {
		this.scene.addPlane(size, name);
	}
	
	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#endCube()
	 */
	@Override
	public void endPlane() throws Exception {}
	
	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#startCube(float, java.lang.String)
	 */
	@Override
	public void startCube(float size, String name) throws Exception {
		this.scene.addCube(size, name);
	}
	
	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#endCube()
	 */
	@Override
	public void endCube() throws Exception {}
	
    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startSphere(float, java.lang.String)
     */
    public void startSphere(float radius, String name) throws Exception {
    	this.scene.addSphere(radius, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endSphere()
     */
    public void endSphere() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startCylinder(float, float, boolean, java.lang.String)
     */
    public void startCylinder(float radius, float height, boolean capped, String name) throws Exception {
    	this.scene.addCylinder(radius, height, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endCylinder()
     */
    public void endCylinder() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startCone(float, float, boolean, java.lang.String)
     */
    public void startCone(float radius, float height, boolean capped, String name) throws Exception {
    	this.scene.addCone(radius, height, capped, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endCone()
     */
    public void endCone() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startTorus(float, float, java.lang.String)
     */
    public void startTorus(float innerRadius, float outerRadius, String name) throws Exception {
    	this.scene.addTorus(innerRadius, outerRadius, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endTorus()
     */
    public void endTorus() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startTeapot(float, java.lang.String)
     */
    public void startTeapot(float size, String name) throws Exception {
    	this.scene.addTeapot(size, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endTeapot()
     */
    public void endTeapot() throws Exception {}

	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#startCube(float, java.lang.String)
	 */
	@Override
	public void startBunny(float size, String name) throws Exception {
		this.scene.addBunny(size, name);
	}
	
	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#endCube()
	 */
	@Override
	public void endBunny() throws Exception {}

	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#startCube(float, java.lang.String)
	 */
	@Override
	public void startVenus(float size, String name) throws Exception {
		this.scene.addVenus(size, name);
	}
	
	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#endCube()
	 */
	@Override
	public void endVenus() throws Exception {}

	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#startCube(float, java.lang.String)
	 */
	@Override
	public void startTriceratops(float size, String name) throws Exception {
		this.scene.addTriceratops(size, name);
	}
	
	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#endCube()
	 */
	@Override
	public void endTriceratops() throws Exception {}

	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#startCube(float, java.lang.String)
	 */
	@Override
	public void startTable(float size, String name) throws Exception {
		this.scene.addTable(size, name);
	}
	
	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#endCube()
	 */
	@Override
	public void endTable() throws Exception {}

	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#startCube(float, java.lang.String)
	 */
	@Override
	public void startTablecloth(float size, String name) throws Exception {
		this.scene.addTablecloth(size, name);
	}
	
	/* (non-Javadoc)
	 * @see abstractModel.parser.ParserHandler#endCube()
	 */
	@Override
	public void endTablecloth() throws Exception {}
	
    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startIndexedTriangleSet(abstractModel.Point3f[], abstractModel.Vector3f[], abstractModel.TexCoord2f[], int[], int[], int[], java.lang.String)
     */
    public void startIndexedTriangleSet(Point3f [] coordinates, Vector3f [] normals, TexCoord2f [] textureCoordinates, int [] coordinateIndices, int [] normalIndices, int [] textureCoordinateIndices, String name) throws Exception {
    	this.scene.addIndexedTriangleSet(coordinates, normals, textureCoordinates, coordinateIndices, normalIndices, textureCoordinateIndices, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endIndexedTriangleSet()
     */
    public void endIndexedTriangleSet() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startTextures()
     */
    public void startTextures() throws Exception {
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endTextures()
     */
    public void endTextures() throws Exception {
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startTexture(java.lang.String, java.lang.String)
     */
    public void startTexture(String src, String name) throws Exception {
    	this.scene.addTexture(src, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endTexture()
     */
    public void endTexture() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startMaterials()
     */
    public void startMaterials() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endMaterials()
     */
    public void endMaterials() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startDiffuseMaterial(abstractModel.Color3f, java.lang.String)
     */
    public void startDiffuseMaterial(Color3f color, String name) throws Exception {
    	scene.addDiffuseMaterial(color, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endDiffuseMaterial()
     */
    public void endDiffuseMaterial() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startPhongMaterial(abstractModel.Color3f, float, java.lang.String)
     */
    public void startPhongMaterial(Color3f color, float shininess, String name) throws Exception {
    	scene.addPhongMaterial(color, shininess, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endPhongMaterial()
     */
    public void endPhongMaterial() throws Exception {}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startLinearCombinedMaterial(java.lang.String, float, java.lang.String, float, java.lang.String)
     */
    public void startLinearCombinedMaterial(String material1Name, float weight1, String material2Name, float weight2, String name) throws Exception {
    	scene.addLinearCombinedMaterial(material1Name, weight1, material2Name, weight2, name);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endLinearCombinedMaterial()
     */
    public void endLinearCombinedMaterial() throws Exception {
    }

    Stack<SceneGraphElement> currentRoot;
    
    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startScene(java.lang.String, java.lang.String[], abstractModel.Color3f)
     */
    public void startScene(String cameraName, String [] lightNames, Color3f background) throws Exception {
    	scene.activateScene(cameraName, lightNames, background);
    	currentRoot = new Stack<SceneGraphElement>();
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endScene()
     */
    public void endScene() throws Exception {
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startShape(java.lang.String, java.lang.String, java.lang.String)
     */
    public void startShape(String geometryName, String materialName, String textureName) throws Exception {
    	Matrix4f localTransformMatrix = new Matrix4f();
    	localTransformMatrix.setIdentity();
    	SceneGraphElement root = null;
    	if(!currentRoot.empty())
    		root = currentRoot.peek();
    	scene.addLeafNode(geometryName, materialName, textureName, localTransformMatrix, root);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endShape()
     */
    public void endShape() throws Exception {
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startRotate(abstractModel.Vector3f, float)
     */
    public void startRotate(Vector3f axis, float angle) throws Exception {
    	if(axis.x == 0 && axis.y == 0 && axis.z == 0)
    		throw new IllegalArgumentException("Cannot rotate about an axis that is the origin. (0,0,0)");
    	System.err.println("axis: " + axis);
    	System.err.println("angle: " + angle);
    	//Make orthonormal base to make rotationMatrix with.
    	Vector3f w = new Vector3f(axis);
    	w.normalize();
//    	System.err.println("w: " + w);
    	Vector3f t = getNonCollinearVector(w);
//    	System.err.println("t: " + t);
    	Vector3f u = new Vector3f();
    	u.cross(t, w);
//    	System.err.println("u_notnormalized: " + u);
    	u.normalize();
//    	System.err.println("u_normalized:" + u);
    	Vector3f v = new Vector3f();
    	v.cross(w, u);
    	v.normalize();
    	System.err.println("v: " + v);
    	Matrix4f uvw = new Matrix4f(u.x, v.x, w.x, 0, 
    								u.y, v.y, w.y, 0, 
    								u.z, v.z, w.z, 0, 
    								0, 0, 0, 1);
    	System.err.println("uvw: \n" + uvw);
    	double angleInRadians = Math.toRadians(angle);
    	System.err.println("angleInRadians: " + angleInRadians);
    	Matrix4f rotateAngleAroundZ = new Matrix4f(	(float) Math.cos(angleInRadians), (float)-Math.sin(angleInRadians), 0, 0,
    												(float) Math.sin(angleInRadians), (float) Math.cos(angleInRadians), 0, 0,
    												0, 0, 1, 0,
    												0, 0, 0, 1);
    	System.err.println("rotateAngleAroundZ: \n" + rotateAngleAroundZ);
    	Matrix4f uvwTranspose = uvw.transpose();
    	System.err.println("uvwTranspose: \n" + uvwTranspose);
    	//Make rotationMatrix
    	Matrix4f rotationMatrix = new Matrix4f();
    	rotationMatrix.multiplyRight(uvw, rotateAngleAroundZ);
    	System.err.println("after first multiplication: \n" + rotationMatrix);
    	
    	rotationMatrix = rotationMatrix.multiplyRight(uvwTranspose);
    	System.err.println("rotationMatrix: \n" + rotationMatrix);
    	//Make new GroupNode
    	SceneGraphElement root = null;
    	if(!currentRoot.empty())
    		root = currentRoot.peek();
    	GroupNode rotateNode = new GroupNode("Rotate " + axis + ", degrees: " + angle, rotationMatrix, root);
    	scene.addGroupNode(rotateNode);
    	//Push de root in de rootStack
    	currentRoot.push(rotateNode);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endRotate()
     */
    public void endRotate() throws Exception {
    	//pop de bovenste root uit de rootStack
    	currentRoot.pop();
    }

	/**
	 * Returns a vector that is not collinear with the given vector.
	 * 
	 * @param w	The given vector.
	 * @return	Vector3f
	 * 		Non collinear vector.
	 */
	protected Vector3f getNonCollinearVector(Vector3f w) {
		Vector3f t = new Vector3f(w);
		float tempX = Math.abs(w.x);
		float tempY = Math.abs(w.y);
		float tempZ = Math.abs(w.z);
    	if(tempY < tempX){
    		if(tempZ < tempY){
    			t.set(w.x, w.y, 1);
    		} else{
    			t.set(w.x, 1, w.z);
    		}
    	} else if(tempZ < tempX){
    		t.set(w.x, w.y, 1);
    	} else{
    		t.set(1, w.y, w.z);
    	}
    	return t;
	}

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startTranslate(abstractModel.Vector3f)
     */
    public void startTranslate(Vector3f vector) throws Exception {
    	//Make TranslateMatrix
    	Matrix4f translateMatrix = new Matrix4f(1, 0, 0, vector.x, 
    											0, 1, 0, vector.y, 
    											0, 0, 1, vector.z, 
    											0, 0, 0, 1);
    	//Make new GroupNode
    	SceneGraphElement root = null;
    	if(!currentRoot.empty())
    		root = currentRoot.peek();
    	GroupNode translateNode = new GroupNode("Translate " + vector, translateMatrix, root);
    	scene.addGroupNode(translateNode);
    	//Push de root in de rootStack
    	currentRoot.push(translateNode);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endTranslate()
     */
    public void endTranslate() throws Exception {
    	//Pop de bovenste root uit de rootStack
    	currentRoot.pop();
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#startScale(abstractModel.Vector3f)
     */
    public void startScale(Vector3f scale) throws Exception {
    	//Make ScaleMatrix
    	Matrix4f scaleMatrix = new Matrix4f(scale.x, 0, 0, 0, 
    										0, scale.y, 0, 0, 
    										0, 0, scale.z, 0, 
    										0, 0, 0, 1);
    	//Make new GroupNode
    	SceneGraphElement root = null;
    	if(!currentRoot.empty())
    		root = currentRoot.peek();
    	GroupNode scaleNode = new GroupNode("Scale " + scale, scaleMatrix, root);
    	scene.addGroupNode(scaleNode);
    	//Push de root in de rootStack
    	currentRoot.push(scaleNode);
    }

    /* (non-Javadoc)
     * @see abstractModel.parser.ParserHandler#endScale()
     */
    public void endScale() throws Exception {
    	//Pop de bovenste root uit de rootStack
    	currentRoot.pop();
    }
    
}