package main;

/**
 * Class containing all the constants needed to render the wanted image with the rendering application.
 * 
 * @author Geert Van Campenhout
 */
public class Constants {
	
	/************************
	 *		FILES			*
	 ************************/
	/** The name of the XML file to parse so the scene can be visualized. */
	public static final String FILENAME =
//			"XML/example.sdl";
//			"XML/simpleScene.sdl";
//			"XML/sphereScene.sdl";
//			"XML/rotateScene.sdl";
//			"XML/teapotWithFloor.sdl";
//			"XML/shadowScene.sdl";
			//"XML/testScene.sdl";
//			"XML/textureScene.sdl";
			//"XML/AliasingScene.sdl";
			//"XML/depthOfFieldScene.sdl";
			//"XML/MovingScene.sdl";
			//"XML/OneTriangleScene.sdl";
			//"XML/visibilityScene.sdl";
			//"XML/perspectiveView.sdl";
			//"XML/diffuseShadingRasterization.sdl";
			//"XML/tables.sdl";
//			"XML/sierpinski.sdl";
			"XML/globalshadowScene.sdl";
	/** The name of the file to save the rendered image to. */
	public static final String SAVE_FILE_NAME = "image.png";
	/** The number of threads to run at the same time. */
	public static final int NUMBER_OF_THREADS = 8;
	
	/************************
	 *		SHADOWS			*
	 ************************/
	/** Whether shadows must be rendered or not. */
	public static final boolean SHADOW = true;
	/** With how much rays per pixel the shadows must be rendered. */
	public static final int SOFT_SHADOWS = 1;
	
	/************************
	 *		ANTIALIASING	*
	 ************************/
	/** The total number of rays cast per pixel for AntiAliasing (has to be a square of an integer). */
	public static final int ANTIALIASING = 0;
	/** The number of rays cast per internal row of the pixel. */
	public static final int ANTIALIASINGPERROW = (int) Math.sqrt(ANTIALIASING);
	
	/************************
	 *	ENVIRONMENT MAP		*
	 ************************/
	/** If false the background color in de .sdl file is used, if true the texture files in the next constants are used. */
	public static final boolean ENVIRONMENTMAP = false;
	public static final String ENVIRONMENTMAPLEFT = "worldMap";
	public static final String ENVIRONMENTMAPRIGHT = "worldMap";
	public static final String ENVIRONMENTMAPTOP = "worldMap";
	public static final String ENVIRONMENTMAPBOTTOM = "worldMap";
	public static final String ENVIRONMENTMAPFRONT = "worldMap";
	public static final String ENVIRONMENTMAPBACK = "worldMap";
	
	/************************
	 *	DEPTH OF FIELD		*
	 ************************/
	/** The size of the lens where random values are chosen from. */
	public static final float LENSSIZE = 0.5f;
	/** The distance of the screen to the lens. */
	public static final float DISTANCETOLENS = 10f;
	/** The number of samples per pixel. */
	public static final int SAMPLESPERPIXEL = 0;
	
	/************************
	 *		MOTION BLUR		*
	 ************************/
	
	/** How to use motion blur, uncomment the scene.moveSphere() line in MainMultiThreaded. and use a scene with a "movingSphere" object. */
	/** Motion Blur works only if antialiasing is off and depth of field is off at the moment. */
	/** How much samples per pixels need to be taken for motion blur. */
	public static final int MBSAMPLESPERPIXEL = 0;
	/** The initial time of movement. */ 
	public static final int T0 = 0;
	/** The end time of movement. */
	public static final int TEND = 3;
	
	/************************
	 *		ACCELERATION	*
	 ************************/
	/** Whether boundingboxes must be used as acceleration structure. */
	public static final boolean BOUNDINGBOX = true;
	/** Whether the used boundingboxes must be hierarchical or not. */
	public static final boolean HIERARCHICALBOUNDINGBOX = false;
	/** The depth of the tree with bounding boxes in it. */
	public static final int LEVELOFHIERARCHICALBOUNDINGBOXTREE = 10;
	
	/************************
	 *IMAGE CHARACTERISTICS	*
	 ************************/
	/** Whether the raytracer will use perspectiveRays (true) or parallel rays (false). */
	public static final boolean PERSPECTIVERAYS = true;
	public static final int NBOFHORIZONTALPIXELS 		= 512;
	public static final int NBOFVERTICALPIXELS			= 512;
	public static final float DISTANCETOSCREEN 			=  1;
	
	/************************
	 *		OBJ FILES		*
	 ************************/
	public static final String PLANE_OBJ = 
			"OBJ/plane.obj";
			//"OBJ/pyramid.obj";
//			"OBJ/sierpinski1.obj";
	public static final String SPHERE_OBJ =
			//"OBJ/plane.obj";
			"OBJ/sphere.obj";
//			"OBJ/cube.obj";
			//"OBJ/teapot.obj";
			//"OBJ/bunny.obj";
			//"OBJ/venus.obj";
			//"OBJ/triceratops.obj";
			//"OBJ/table.obj";
			//"OBJ/table-fixed.obj";
			//"OBJ/tablecloth.obj";
			//"OBJ/treeleaves.obj";
			//"OBJ/treebranches.obj";
//			"OBJ/android.obj";
			//"OBJ/brilliant.obj";
//			"OBJ/testOutput.obj";
	public static final String CUBE_OBJ = 
			"OBJ/cube.obj";
			//"OBJ/treebranches.obj";
	public static final String CYLINDER_OBJ = "OBJ/cylinder.obj";
	public static final String CONE_OBJ = "OBJ/cone.obj";
	public static final String TORUS_OBJ = "OBJ/torus.obj";
	public static final String TEAPOT_OBJ = "OBJ/teapot.obj";
	public static final String BUNNY_OBJ = "OBJ/bunny.obj";
	public static final String TABLE_OBJ = "OBJ/table.obj";
	public static final String TABLE_FIXED_OBJ = "OBJ/table-fixed.obj";
	public static final String TABLECLOTH_OBJ = "OBJ/tablecloth.obj";
	public static final String TRICERATOPS_OBJ = "OBJ/triceratops.obj";
	public static final String VENUS_OBJ = "OBJ/venus.obj";
	
	/************************
	 *REFLECTION			*
	 ************************/
	public static final int REFLECTIONDEPTH = 2;
	
}
