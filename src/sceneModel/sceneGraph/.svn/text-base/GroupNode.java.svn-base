package sceneModel.sceneGraph;

import java.util.ArrayList;
import java.util.List;

import abstractModel.Matrix4f;

/**
 * A SceneGraphElement node containing other SceneGraphElements thus making it a GroupNode.
 * 
 * @author Geert Van Campenhout
 * @version 1.1
 */
public class GroupNode extends SceneGraphElement{
	
	private List<SceneGraphElement> elements;
	
	/**
	 * Creates a new GroupNode with the given name, localTransformMatrix and root SceneGraphElement.
	 * Give root = null as argument if the GroupNode is the top element of the SceneGraph.
	 * 
	 * @param name	The name of the GroupNode.
	 * @param localTransformMatrix	The localTransformMatrix of the GroupNode.
	 * @param root	The root SceneGraphElement of the GroupNode.
	 */
	public GroupNode(String name, Matrix4f localTransformMatrix, SceneGraphElement root){
		super(name, localTransformMatrix, root);
		this.elements = new ArrayList<SceneGraphElement>();
	}
	
	/**
	 * Adds the given SceneGraphElement to the GroupNode.
	 * 
	 * @param element	The SceneGraphElement to add.
	 */
	public void addElementToGroup(SceneGraphElement element){
		elements.add(element);
	}
	
	/**
	 * @return	The SceneGraphElements of this groupNode.
	 */
	public List<SceneGraphElement> getElements(){
		return this.elements;
	}
}
