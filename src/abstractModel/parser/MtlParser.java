package abstractModel.parser;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstractModel.Color3f;
import abstractModel.Scene;

import sceneModel.GlobalMaterial;

public class MtlParser {

	String fileName;
	GlobalMaterial currentMaterial = null;
	List<GlobalMaterial> materials;
	Scene scene;
	
	/**
	 * Creates a new ObjectParser for the file with the given name.
	 * 
	 * @param fileName	The name of the file that needs to be parsed.
	 */
	public MtlParser(String fileName, Scene scene){
		this.fileName = fileName;
		this.materials = new ArrayList<GlobalMaterial>();
		this.scene = scene;
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
	public void parse(String nameMTL) throws FileNotFoundException{
		long calcStartTime = System.currentTimeMillis();
		System.out.println("Start parsing Mtlfile: " + fileName);
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
		scene.addGlobalMaterials(this.materials);
		
		long calcStopTime = System.currentTimeMillis();
		long duration = calcStopTime - calcStartTime;
		System.out.println("Parsing of " + fileName + " finished in " + duration + " msec.");
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
		if(cmd.equals("newmtl")){
			//name of the material
			if(currentMaterial != null){
				materials.add(currentMaterial);
				currentMaterial = new GlobalMaterial(splittedString[1]);
			} else{
				currentMaterial = new GlobalMaterial(splittedString[1]);
			}
		} else if(cmd.equals("Ns")){
			currentMaterial.setPhongExponent(Float.parseFloat(splittedString[1]));
		} else if(cmd.equals("Ni")){
			System.err.println("Ni not yet implemented.");
		} else if(cmd.equals("d")){
			System.err.println("d not yet implemented.");
		} else if(cmd.equals("Tr")){
			System.err.println("Tr not yet implemented.");
		} else if(cmd.equals("Tf")){
			System.err.println("Tf not yet implemented.");
		} else if(cmd.equals("illum")){
			System.err.println("illum not yet implemented.");
		} else if(cmd.equals("Ka")){
			currentMaterial.setAmbientColor(new Color3f(
					Float.parseFloat(splittedString[1]), 
					Float.parseFloat(splittedString[2]), 
					Float.parseFloat(splittedString[3])));
		} else if(cmd.equals("Kd")){
			currentMaterial.setColor(new Color3f(
					Float.parseFloat(splittedString[1]), 
					Float.parseFloat(splittedString[2]), 
					Float.parseFloat(splittedString[3])));
		} else if(cmd.equals("Ks")){
			currentMaterial.setSpectralColor(new Color3f(
					Float.parseFloat(splittedString[1]), 
					Float.parseFloat(splittedString[2]), 
					Float.parseFloat(splittedString[3])));
		} else if(cmd.equals("Ke")){
			System.err.println("Ke not yet implemented.");
		} else{
			throw new UnsupportedOperationException("Lines starting with \"" + cmd + "\" not supported yet.");
		}
	}

}
//TODO this class is not used anywhere yet.

/*
newmtl wire_087224198
Ns 32
d 1
Tr 1
Tf 1 1 1
illum 2
Ka 0.0000 0.0000 0.0000
Kd 0.3412 0.8784 0.7765
Ks 0.3500 0.3500 0.3500
*/