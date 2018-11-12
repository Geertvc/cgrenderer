package abstractModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Demo implements MouseListener {

	public static void main(String[] args) {
		new Demo("XML/example.sdl");
	}

	private int x;
	private int y;
	private JFrame frame;
	private CgPanel panel;

	public Demo(String fileName) {
		try {
			SceneBuilder sceneBuilder = new SceneBuilder();
			/*Scene scene = */sceneBuilder.loadScene(fileName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}


		panel = new CgPanel();
		panel.addMouseListener(this);
		frame = new JFrame();
		frame.setSize(640,480);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	public void mousePressed(MouseEvent e) { }
	public void mouseClicked(MouseEvent e) { timePerformance(); }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }

	/**
	 * Method that draws pixels in the correct color.
	 */
	public void drawPixels() {
		for (int row=10; row<panel.getHeight()-10; row++) {
			for (int column=10; column<panel.getWidth()-10; column++) {
				if (column>x && column < x+100) {
					//Color vertical collumn
					panel.drawPixel(column, row, 1,0,0);
				}
				else if (row>y && row < y+100) {
					//Color horizondal collumn
					panel.drawPixel(column, row, 1, 1,0);
				}
				else {
					//Color background
					panel.drawPixel(column, row, 0.5f,0.5f,0.5f);
				}
			}
		}
		x = (x+1)%(panel.getWidth()+1);
		y = (y+1)%(panel.getHeight()+1);
	}

	
	/**
	 * Method taking care of moving picture.
	 */
	public void timePerformance() {
		long starttime = System.currentTimeMillis();
		int numOfSteps = 100;
		for (int i=0; i<numOfSteps; i++) {
			panel.clear(0,0,1);
			drawPixels();
			panel.repaint();
			panel.flush();
		}
		long endtime = System.currentTimeMillis();
		double fps = 1000.0/((double)(endtime-starttime)/numOfSteps);
		System.out.println("Fps: " + fps);
		panel.saveImage("image.png");
	}
}
