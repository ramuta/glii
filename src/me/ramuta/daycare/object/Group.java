package me.ramuta.daycare.object;

import java.util.ArrayList;

public class Group {
	private String ID;
	private String name;
	private ArrayList<Child> children;
	
	public Group() {
		super();
	}
	
	public Group(String ID, String name, ArrayList<Child> children) {
		super();
		setID(ID);
		setName(name);
		setChildren(children);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @return the children
	 */
	public ArrayList<Child> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<Child> children) {
		this.children = children;
	}
}
