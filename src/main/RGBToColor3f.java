package main;

import abstractModel.Color3f;

public class RGBToColor3f {
	
	public static void main(String[] args) {
		float[] rgbValues = new float[] {179, 238, 58};
		Color3f color = new Color3f((float) rgbValues[0]/255F, (float) rgbValues[1]/255F, (float) rgbValues[2]/255F);
		System.out.println(color);
	}
}
