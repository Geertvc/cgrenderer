package abstractModel.parser;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Mesh;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import main.Constants;
import abstractModel.Functions;
import abstractModel.Point3f;
import abstractModel.TexCoord2f;
import abstractModel.Vector3f;

/**
 * ObjectParser is used to parse .obj files.
 * An .obj file will be parsed and a Mesh will be created from it.
 * 
 * @author Geert Van Campenhout
 * @version 2.0
 */
public class ObjectParser {
	
	Mesh mesh;
	List<Point3f> coordinates;
	List<Vector3f> normals;
	List<TexCoord2f> textureCoordinates;
	List<Integer> coordinateIndices;
	List<Integer> normalIndices;
	List<Integer> textureCoordinateIndices;
	String fileName;
	
	/**
	 * Creates a new ObjectParser for the file with the given name.
	 * 
	 * @param fileName	The name of the file that needs to be parsed.
	 */
	public ObjectParser(String fileName){
		this.coordinates = new ArrayList<Point3f>();
		this.normals = new ArrayList<Vector3f>();
		this.textureCoordinates = new ArrayList<TexCoord2f>();
		this.coordinateIndices = new ArrayList<Integer>();
		this.normalIndices = new ArrayList<Integer>();
		this.textureCoordinateIndices = new ArrayList<Integer>();
		this.fileName = fileName;
	}
	
	/**
	 * Parses the ObjectParser file to a Mesh with the given name.
	 * 
	 * @param nameMesh	The name to give to the parsed file.
	 * @return	Mesh
	 * 		The parsed file.
	 * @throws FileNotFoundException
	 * 		If the file is not found.
	 */
	public Mesh parse(String nameMesh) throws FileNotFoundException{
		long calcStartTime = System.currentTimeMillis();
		System.out.println("Start parsing file: " + fileName);
		File file = new File(this.fileName);
	    FileInputStream fileInputStream = new FileInputStream(file);
	    DataInputStream in = new DataInputStream(fileInputStream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
		//Read File Line By Line
		try {
			while ((strLine = br.readLine()) != null)   {
				parseLine(strLine);
			}
			//Close the input stream
			in.close();
		} catch (IOException e) {
			System.err.println("IOxception in ObjectParser.parse");
			e.printStackTrace();
		}
		Point3f[] coordinatesArray = this.coordinates.toArray(new Point3f[0]);
		if(Constants.BOUNDINGBOX){
			Point3f minValuesMesh = new Point3f();
			Point3f maxValuesMesh = new Point3f();
			Functions.getExtremeXYZValues(coordinatesArray, minValuesMesh, maxValuesMesh);
			mesh = new Mesh(coordinatesArray, 
					normals.toArray(new Vector3f[0]), textureCoordinates.toArray(new TexCoord2f[0]), 
					convertToIntArray(coordinateIndices), convertToIntArray(normalIndices), convertToIntArray(textureCoordinateIndices),
					nameMesh, minValuesMesh, maxValuesMesh);
		} else{
			mesh = new Mesh(coordinatesArray, 
					normals.toArray(new Vector3f[0]), textureCoordinates.toArray(new TexCoord2f[0]), 
					convertToIntArray(coordinateIndices), convertToIntArray(normalIndices), convertToIntArray(textureCoordinateIndices),
					nameMesh);
		}
		long calcStopTime = System.currentTimeMillis();
		long duration = calcStopTime - calcStartTime;
		System.out.println("Parsing of " + fileName + " finished in " + duration + " msec.");
	    return mesh;
	}
	
	/**
	 * Parses the given line.
	 * 
	 * @param strLine	The line to parse.
	 */
	public void parseLine(String strLine){
		String[] splittedString = strLine.split(" ");
		String cmd = splittedString[0];
		for (int i = 1; i < splittedString.length; i++) {
			if(splittedString[i].equals("")){
				for (int j = i; j < splittedString.length-1; j++) {
					splittedString[j] = splittedString[j+1];
				}
			}
		}
		if(cmd.equals("v")){
			//Vertex
			this.coordinates.add(new Point3f(
					Float.parseFloat(splittedString[1]),
					Float.parseFloat(splittedString[2]),
					Float.parseFloat(splittedString[3])));
		} else if(cmd.equals("vt")){
			//Textuurcoordinaat
			this.textureCoordinates.add(new TexCoord2f(
					Float.parseFloat(splittedString[1]), 
					Float.parseFloat(splittedString[2])));
		} else if(cmd.equals("vn")){
			//normaal
			this.normals.add(new Vector3f(
					Float.parseFloat(splittedString[1]),
					Float.parseFloat(splittedString[2]),
					Float.parseFloat(splittedString[3])));
		} else if(cmd.equals("f")){
			//Vlak
			if(splittedString.length-1 == 3){
				nbFaces++;
				for (int i = 1; i < splittedString.length; i++) {
					parsePlaneSubString(splittedString[i]);
				}
			} else if(splittedString.length-1 == 4){
				String[] triangle1 = new String[] {splittedString[0], splittedString[1], splittedString[2], splittedString[3]};
				String[] triangle2 = new String[] {splittedString[0], splittedString[1], splittedString[3], splittedString[4]};
				nbFaces++;
				for (int i = 1; i < triangle1.length; i++) {
					parsePlaneSubString(triangle1[i]);
				}
				nbFaces++;
				for (int i = 1; i < triangle2.length; i++) {
					parsePlaneSubString(triangle2[i]);
				}
			} else{
				throw new UnsupportedOperationException("Don't support faces with more than 4 vertices.");
			}
			
		} else if(cmd.equals("#")){
			//ignore Comment lines
		} else if(cmd.equals("g")){
			//Group start
			System.err.println("Implement groups");
		} else if(cmd.equals("mtllib")){
			//Mtllib start
			System.err.println("Implement mtllib");
		} else if(cmd.equals("o")){
			//objectname start
			System.err.println("Implement o");
		} else if(cmd.equals("usemtl")){
			//usemtl name material start
			System.err.println("Implement usemtl");
		} else if(cmd.equals("s")){
			//smoothgroup start
			System.err.println("Implement s");
		} else if(cmd.equals("")){
			//empty line
		} else{
			throw new UnsupportedOperationException("Lines starting with \"" + cmd + "\" not supported yet.");
		}
	}
	
	/**
	 * Parses a subString that came from a plane line of the .obj file.
	 * 
	 * @param subString	The subString to parse.
	 */
	public void parsePlaneSubString(String subString){
		String[] splitSubString;
		splitSubString = subString.split("/");
		this.coordinateIndices.add(Integer.parseInt(splitSubString[0])-1);
		if(splitSubString.length >1){
			if(!splitSubString[1].equals(""))
				this.textureCoordinateIndices.add(Integer.parseInt(splitSubString[1])-1);
			if(splitSubString.length > 2)
				this.normalIndices.add(Integer.parseInt(splitSubString[2])-1);
		}
	}
	
	public int nbFaces = 0;
	
	/**
	 * Main method to test the ObjectParser.
	 * 
	 * @param args	The args to give to the main method.
	 * @throws FileNotFoundException
	 * 		If the file is not found.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		ObjectParser objparser = new ObjectParser("OBJ/brilliant.obj");
		Mesh mesh = objparser.parse("sphere");
		System.out.println("done parsing");
		System.out.println("Number of coordinates: " + objparser.coordinates.size());
		System.out.println("Number of normals: " + objparser.normals.size());
		System.out.println("Number of texture coordinates: " + objparser.textureCoordinates.size());
		System.out.println("Number of faces: " + objparser.nbFaces);
		System.out.println("Number of coordinatesIndices: " + objparser.coordinateIndices.size());
		System.out.println("Number of normalIndices: " + objparser.normalIndices.size());
		System.out.println("Number of textureIndices: " + objparser.textureCoordinateIndices.size());
		
		mesh.getVertices();
		mesh.getTriangles();
//		ArrayList<Integer> a = new ArrayList<Integer>();
//		int[] b = objparser.convertToIntArray(a);
//		for (int i = 0; i < b.length; i++) {
//			System.out.println(b[i]);
//		}
	}
	
	/**
	 * Converts the given List of Integers to a int[].
	 * 
	 * @param list	The list to convert.	
	 * @return	int[]
	 * 		The converted list.
	 */
	private int[] convertToIntArray(List<Integer> list){
		int[] a = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			a[i] = list.get(i);
		}
		return a;
	}
}
