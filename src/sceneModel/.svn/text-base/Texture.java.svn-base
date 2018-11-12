package sceneModel;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Triangle;
import indexed_Neighbour_Triangle_Mesh_With_Edges.Vertex;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import abstractModel.Color3f;
import abstractModel.Point3f;
import abstractModel.TexCoord2f;

/**
 * A SceneElement that represents a texture that can be used to shade another SceneElement.
 * 
 * @author Geert Van Campenhout
 * @version 1.0
 */
public class Texture implements SceneElement{

	private String name;
	private String src;
	private BufferedImage textureImage;
	
	/**
	 * Creates a new Texture with the name of the sourcefile and the name of the texture.
	 * 
	 * @param src	The name of the source file that contains the texture.
	 * @param name	The name of the texture.
	 * @throws IOException
	 * 		If the source file could not be loaded.
	 */
	public Texture(String src, String name) throws IOException{
		this.name = name;
		this.src = "TEXTURE/" + src;
		try {
			this.textureImage = ImageIO.read(new File(this.src));
		} catch (IOException e) {
			System.err.println("Failed to load the " + name + " texture from \"" + src + "\"");
			throw e;
		}
	}
	
	/* (non-Javadoc)
	 * @see sceneModel.SceneElement#getName()
	 */
	@Override
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns the color3f value at the given coordinate in the image.
	 * 
	 * @param x	The x coordinate to get the color3f value of.
	 * @param y	The y coordinate to get the color3f value of.
	 * @return	Color3f
	 * 		The color3f value at the given coordinate in the image.
	 */
	private Color3f getColor3f3AtIntern(int x, int y){
		int width = textureImage.getWidth();
		int height = textureImage.getHeight();
		if((x<0)||(x>width)||(y<0)||(y>height))
			throw new IndexOutOfBoundsException("Trying to access a pixel (" + x + ", " + y + ") out of the image range (" + width + "x" + height +")");
		//Zorgt ervoor dat tiling mogelijk is.
		if((x<0)||(x>width))
			x = x%width;
		if((y<0)||(y>height))
			y = y%height;
		//Texture in de y richting omdraaien want y begint hier van onder naar boven en bij ons omgekeerd.
		y = height -1 -y;
		Color c = new Color(textureImage.getRGB(x, y));
		return new Color3f(c);
	}
	
	/**
	 * Returns the color3f value at the given u,v coordinate of the surface.
	 * 
	 * @param u	The u coordinate in the surface.
	 * @param v	The v coordinate in the surface
	 * @return	Color3f
	 * 		The color3f value at the given u,v surface coordinate.
	 */
	public Color3f getColor3fAt(float u, float v){
		int width = textureImage.getWidth();
		int height = textureImage.getHeight();
		if((u<0) || (u>width))
			u = u%width;
		if((v<0) || (v>height))
			v = v%height;
		float imageU = u*(width-1);
		float imageV = v*(height-1);
		//Calculate the square in witch the color lies in the image.
		int xMin = (int) Math.floor(imageU);
		int xMax = (int) Math.ceil(imageU);
		if(xMax >= width) xMax = width-1;
		int yMin = (int) Math.floor(imageV);
		int yMax = (int) Math.ceil(imageV);
		if(yMax >= height) xMax = height-1;
		Color3f interpolatedColor = new Color3f();
		if(xMin == xMax){
			if(yMin == yMax){
				interpolatedColor = getColor3f3AtIntern(xMin, yMin);
			} else{
				Color3f color1 = getColor3f3AtIntern(xMin, yMin);
				Color3f color2 = getColor3f3AtIntern(xMin, yMax);
				interpolatedColor.interpolate(color1,color2, imageV-yMin);
			}
		} else{
			if(yMin == yMax){
				Color3f color1 = getColor3f3AtIntern(xMin, yMin);
				Color3f color2 = getColor3f3AtIntern(xMax, yMin);
				interpolatedColor.interpolate(color1,color2, imageU-xMin);
			} else{
				Color3f color1 = getColor3f3AtIntern(xMin, yMin);
				Color3f color2 = getColor3f3AtIntern(xMax, yMin);
				Color3f interpolatedColor1 = new Color3f();
				interpolatedColor1.interpolate(color1,color2, imageU-xMin);
				color1 = getColor3f3AtIntern(xMin, yMax);
				color2 = getColor3f3AtIntern(xMax, yMax);
				Color3f interpolatedColor2 = new Color3f();
				interpolatedColor2.interpolate(color1,color2, imageU-xMin);
				interpolatedColor.interpolate(interpolatedColor1,interpolatedColor2, imageV-yMin);
			}
		}
		return interpolatedColor;
	}

	/**
	 * Returns the needed color at the given intersectionPoint in the given triangle for the given pointLight.
	 * 
	 * @param a	The first vertex of the triangle.
	 * @param b The second vertex of the triangle.
	 * @param c The third vertex of the triangle.
	 * @param intersectionPoint	The intersectionPoint to find the color at.
	 * @param pointLight	The pointLight to find the color for.
	 * @return	Color3f
	 * 		The color3f value at the intersectionPoint in the given triangle.
	 */
	public Color3f shade(Vertex a, Vertex b, Vertex c,
			Point3f intersectionPoint, PointLight pointLight) {
		TexCoord2f textureCoord = Triangle.getInterpolatedTextureCoordinates(a, b, c, new Point3f(intersectionPoint));
		Color3f textureColor = getColor3fAt(textureCoord.x, textureCoord.y);
		//Scale texture color to pointLight intensity and color.
		Color3f scaledColor = new Color3f(
				textureColor.x*pointLight.intensity*pointLight.color.x,
				textureColor.y*pointLight.intensity*pointLight.color.y,
				textureColor.z*pointLight.intensity*pointLight.color.z);
		//Stick texture on the geometry
		return scaledColor;
	}
}
