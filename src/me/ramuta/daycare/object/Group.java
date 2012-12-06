package me.ramuta.daycare.object;

public class Group {
	private String name;
	
	public Group() {
		super();
	}
	
	public Group(String name) {
		super();
		setName(name);
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
}
