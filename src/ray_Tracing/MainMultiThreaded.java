package ray_Tracing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;

import javax.swing.JFrame;

import main.Constants;
import abstractModel.CgPanel;
import abstractModel.Color3f;
import abstractModel.Scene;
import abstractModel.SceneBuilder;

/**
 * Main class running the ray tracing application multithreaded.
 * 
 * TODO ::
 * 	Spotlights
 * 	DirectionalLights
 * 	Reflecties
 * 	Refracties
 * 	mtl files inlezen
 * 	menger fractaal proberen door geometrische aftrekkingen van geometriee 
 * 
 * 
 * @author Geert Van Campenhout
 * @version 1.3
 */
public class MainMultiThreaded {
	
	/**
	 * The main method running the whole application.
	 * @param args
	 */
	public static void main(String[] args) {
		new MainMultiThreaded(Constants.FILENAME, Constants.SAVE_FILE_NAME);
	}

	private volatile int currentRow;
	private Color3f[][] colors;
	private volatile int nbOfFinishedRows = 0;
	private DecimalFormat df;
	private CgPanel imagePanel;
	
	/**
	 * Starts the application with the given filename of the XML file to be parsed.
	 * 
	 * @param fileName	The name of the file to be parsed.
	 * @param saveFileName	The name of the file to save the rendered image to.
	 */
	public MainMultiThreaded(String fileName, String saveFileName) {
		this.currentRow = 0;
		this.colors = new Color3f[Constants.NBOFVERTICALPIXELS][Constants.NBOFHORIZONTALPIXELS];
		df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		//Creating the scene
		long duration;
		Scene scene = createScene(fileName);
		
//		scene.getLeafNode("sphere").setRotationMatrix(angle);
//		scene.addMovingSphere1();
//		scene.addMovingSphere2();
		
		//Setting up JFrame and CgPanel
		imagePanel = initFrameAndImagePanel();
		//Setting up multiThreaded
		final long calcStartTime = System.currentTimeMillis();
		//this.lastLoggedTime = calcStartTime;
		RayTracer[] threads = createAndStartAllWorkThreads(scene);
		//TODO verander van do while loop naar een push van de thread dat hij dood gaat en dan hou je bij hoeveel er nog leven.
		waitWhileWorkThreadsAreCalculating(threads);
		final long calcStopTime = System.currentTimeMillis();
		duration = calcStopTime - calcStartTime;
		System.out.println("Scene visualized in " + duration + " msec");
		this.imagePanel.saveImage(saveFileName);
//		this.frame.dispose();
	}

	/**
	 * Draws the image on the imagePanel.
	 * After drawing the image is saved.
	 * 
	 * @param imagePanel	The panel to draw the image on.
	 */
	protected void drawImage() {
		try{
			for (int i = 0; i < Constants.NBOFVERTICALPIXELS; i++) {
				int yValue = Constants.NBOFVERTICALPIXELS-i;
				for (int j = 0; j < Constants.NBOFHORIZONTALPIXELS; j++) { 
					imagePanel.drawPixel(j, yValue, this.colors[i][j].x, this.colors[i][j].y, this.colors[i][j].z);
				}
			}
		} catch(ArrayIndexOutOfBoundsException e){}
		imagePanel.flush();
		this.imagePanel.saveImage(Constants.SAVE_FILE_NAME);
	}

	/**
	 * This method waits until all the given WorkThreads are dead.
	 * 
	 * @param threads	The given WorkThreads that calculate the image.
	 */
	protected void waitWhileWorkThreadsAreCalculating(RayTracer[] threads) {
		boolean allDead = false;
		do{
			allDead = true;
			for (int i = 0; i < threads.length; i++) {
				if(threads[i].isAlive())
					allDead = false;
			}
		} while(!allDead);
	}

	/**
	 * Creates all the workThreads. These will each calculate several parts of the image.
	 * 
	 * @param scene	The scene that needs to be calculated.
	 * @return	WorkThread[]
	 * 		All created WorkThreads.
	 */
	protected RayTracer[] createAndStartAllWorkThreads(Scene scene) {
		RayTracer[] threads = new RayTracer[Constants.NUMBER_OF_THREADS];
		for (int thread = 0; thread < Constants.NUMBER_OF_THREADS; thread++) {
			RayTracer workThread = new GlobalRayTracer(this, scene);
			System.out.println("Thread " + thread + " aangemaakt.");
			threads[thread] = workThread;
			workThread.start();
		}
		return threads;
	}
	
//	private JFrame frame;

	/**
	 * Initializes the JFrame in which the image will be shown.
	 * 
	 * @param panel	The panel that shows the image.
	 */
	protected CgPanel initFrameAndImagePanel() {
		CgPanel panel = new CgPanel();
		panel.setSize(Constants.NBOFHORIZONTALPIXELS, Constants.NBOFVERTICALPIXELS);
		panel.setPreferredSize(new Dimension(Constants.NBOFHORIZONTALPIXELS, Constants.NBOFVERTICALPIXELS));
		JFrame frame = new JFrame();
//		this.frame = new JFrame();
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

	/**
	 * Creates the scene found in the given file.
	 * 
	 * @param fileName	The given file name.	
	 * @return	Scene
	 * 		The created scene.
	 */
	protected Scene createScene(String fileName) {
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
	 * Returns the number of row that needs to be calculated next.
	 * 
	 * @return	int
	 * 		The next row that needs calculation.
	 */
	public synchronized int getNextRowData(){
		int nextRowToCalc;
		if(this.currentRow > Constants.NBOFVERTICALPIXELS-1){
			nextRowToCalc = -1;
		} else{
			nextRowToCalc = currentRow;
			this.currentRow++;
		}
		return nextRowToCalc;
	}
	
	/**
	 * Sets the calculated colors so they can be drawn on the panel.
	 * The parameter calculatedColors has to have a length of Constants.NBOFHORIZONTALPIXELS.
	 * 
	 * @param Row	The row of the calculated colors.
	 * @param calculatedColors	The calculated colors.
	 * @pre	The length of calculatedColors has to be Constants.NBOFHORIZONTALPIXELS
	 * 		calculatedColors.length == Constants.NBOFHORIZONTALPIXELS
	 */
	public synchronized void setColors(int Row, Color3f[] calculatedColors){
		if(calculatedColors.length != Constants.NBOFHORIZONTALPIXELS)
			throw new IllegalArgumentException(
					"The calculated array of colors has a wrong length: " + calculatedColors.length + 
					", it had to be: " + Constants.NBOFHORIZONTALPIXELS);
		this.nbOfFinishedRows++;
		float progress = ((float) this.nbOfFinishedRows*100)/Constants.NBOFVERTICALPIXELS;
		System.out.println("Progress: " + df.format(progress) + "% (" + nbOfFinishedRows + "/" + Constants.NBOFVERTICALPIXELS + ")"
				/*+ " ETC: " + getETC(Constants.NBOFVERTICALPIXELS-nbOfFinishedRows) + "msec"*/);
		this.colors[Row] = calculatedColors;
		imagePanel.drawRowOfPixels(Row, this.colors[Row]);
	}

}
