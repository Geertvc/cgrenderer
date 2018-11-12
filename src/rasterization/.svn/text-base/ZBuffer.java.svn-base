package rasterization;

import abstractModel.Color3f;

/**
 * Class representing a ZBuffer.
 * 
 * @author Geert Van Campenhout
 */
public class ZBuffer {
	
	private ZBufferEntry[][] zBuffer;
	
	/**
	 * Creates a new ZBuffer with the given arguments.
	 * 
	 * @param numberOfHorizontalPixels	The first size argument of the ZBuffer.
	 * @param numberOfVerticalPixels	The second size argument of the ZBuffer.
	 * @param backGroundColor	The backgroundColor, which is the default color value of the ZBufferEntries.
	 * @param initialValue	The initial depth values.
	 */
	public ZBuffer(int numberOfHorizontalPixels, int numberOfVerticalPixels, Color3f backGroundColor, double initialValue){
		zBuffer = new ZBufferEntry[numberOfHorizontalPixels][numberOfVerticalPixels];
		for (int x = 0; x < numberOfHorizontalPixels; x++) {
			for (int y = 0; y < numberOfVerticalPixels; y++) {
				zBuffer[x][y] = new ZBufferEntry(backGroundColor, initialValue);
			}
		}
	}
	
	/**
	 * Returns the ZBufferEntry at the given place.
	 * @param x
	 * @param y
	 * @return
	 */
	public ZBufferEntry getEntryAt(int x, int y){
		return this.zBuffer[x][y];
	}
	
	/**
	 * Sets the ZBufferEntry at the given place with the given arguments.
	 * @param x
	 * @param y
	 * @param color
	 * @param depth
	 */
	public void setEntryAt(int x, int y, Color3f color, double depth){
		this.zBuffer[x][y].set(color, depth);
	}
	
	/**
	 * Returns the depth value at the given place.
	 * @param x
	 * @param y
	 * @return
	 */
	public double getDepthAt(int x, int y){
		return this.zBuffer[x][y].getDepth();
	}
	
	/**
	 * Returns the color value at the given place.
	 * @param x
	 * @param y
	 * @return
	 */
	public Color3f getColorAt(int x, int y){
		return this.zBuffer[x][y].getColor();
	}
}
