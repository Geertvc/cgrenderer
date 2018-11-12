package rasterization;

import abstractModel.Color3f;

/**
 * Class representing an entry in a ZBuffer, containing a color and a depth.
 * 
 * @author Geert Van Campenhout
 */
public class ZBufferEntry {
	
	private Color3f color;
	private double depth;
	
	/**
	 * Creates a new ZBufferEntry with the given color and depth value.
	 * 
	 * @param color	The color value.	
	 * @param depth	The depth value.
	 */
	public ZBufferEntry(Color3f color, double depth){
		this.color = color;
		this.depth = depth;
	}
	
	/**
	 * Returns the color value.
	 * @return
	 */
	public Color3f getColor() {
		return color;
	}

	/**
	 * Sets the color and depth value to the given values.
	 * @param color
	 * @param depth
	 */
	public void set(Color3f color, double depth) {
		this.color = new Color3f(color);
		this.depth = depth;
	}

	/**
	 * Returns the depth value.
	 * @return
	 */
	public double getDepth() {
		return depth;
	}
}
