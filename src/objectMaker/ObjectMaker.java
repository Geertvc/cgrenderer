package objectMaker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ObjectMaker {
	public static final boolean DEBUG = false;

	public static final String FORMAT = "PBRT";
	

	public static final String OBJ_FORMAT = "OBJ";
	public static final String PBRT_FORMAT = "PBRT";
	
	public static void main(String[] args) {
		BufferedWriter out = null;
		try {
			float l = 1f;
			int indexOfObject = 0;
			int depth = 4;
			Point3f startPoint = new Point3f(-0.5f, -0.5f, 0f);
			GeometricObject obj = new Menger(l, indexOfObject,depth , startPoint);

			String filename = "menger" + depth + ".pbrt";
			out = new BufferedWriter(new FileWriter(filename));
			
			if(FORMAT.equals(OBJ_FORMAT)){
				printOBJFormat(out, obj);
			} else if(FORMAT.equals(PBRT_FORMAT)){
				printPRBTFormat(out, obj);
			} else{
				System.err.println("The set format: " + FORMAT + " is not supported.");
			}
			out.close();
		} catch (IOException e) {
			System.err.println("An IOException has occurred please try again.");
		}
	}

	private static void printPRBTFormat(BufferedWriter out, GeometricObject obj) throws IOException {
		println(out, "AttributeBegin");
		println(out, "Shape \"trianglemesh\"");
		println(out, "\"point P\" [");
		for (int i = 0; i < obj.basicVertices.length; i++) {
			String print = obj.basicVertices[i].x + " " + obj.basicVertices[i].y + " " + obj.basicVertices[i].z;
			println(out, print) ;
		}
		println(out, "]");
		
		println(out, "\"integer indices\" [");
		for (int i = 0; i < obj.indices.length; i++) {
			String print = obj.indices[i][0] + " " + obj.indices[i][1] + " " + obj.indices[i][2];
			println(out, print);
		}
		println(out, "]");
		println(out, "AttributeEnd");
		//System.out.println((int) Math.pow(5, 10));
	}
	
	private static void printOBJFormat(BufferedWriter out, GeometricObject obj) throws IOException {
		for (int i = 0; i < obj.basicVertices.length; i++) {
			String print = "v " + obj.basicVertices[i].x + " " + obj.basicVertices[i].y + " " + obj.basicVertices[i].z;
			println(out, print);
		}
		for (int i = 0; i < obj.indices.length; i++) {
			String print = "f " + obj.indices[i][0] + " " + obj.indices[i][1] + " " + obj.indices[i][2];
			println(out, print);
		}
		//System.out.println((int) Math.pow(5, 10));
	}
	
	private static void println(BufferedWriter out, String line) throws IOException{
		out.write(line + "\n");
		if(DEBUG){
			System.out.println(line);
		}
	}
}



