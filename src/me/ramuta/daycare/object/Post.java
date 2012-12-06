package me.ramuta.daycare.object;

public class Post {
	private String ID;
	private String text;
	private String authorFirstName;
	private String authorLastName;
	private boolean hasImage;

	// konstruktor 1
	public Post() {
		super();
	}
	
	// konstruktor 2
	public Post(String ID, String text, String authorFirstName, String authorLastName, boolean hasImage) {
		setID(ID);
		setText(text);
		setAuthorFirstName(authorFirstName);
		setAuthorLastName(authorLastName);
		setHasImage(hasImage);
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
	public void setID(String ID) {
		this.ID = ID;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the authorFirstName
	 */
	public String getAuthorFirstName() {
		return authorFirstName;
	}

	/**
	 * @param authorFirstName the authorFirstName to set
	 */
	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	/**
	 * @return the authorLastName
	 */
	public String getAuthorLastName() {
		return authorLastName;
	}

	/**
	 * @param authorLastName the authorLastName to set
	 */
	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	/**
	 * @return the hasImage
	 */
	public boolean hasImage() {
		return hasImage;
	}

	/**
	 * @param hasImage the hasImage to set
	 */
	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}
}
