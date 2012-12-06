package me.ramuta.daycare.object;

public class Child {
	private String ID;
	private String firstName;
	private String lastName;
	private String mother;
	private String father;
	private String contactAddress;
	private String contactEmail;
	private String contactPhone1;
	private String contactPhone2;
	private String daycare;
	private Group group;
	private String imageUrl;
	
	public Child() {
		super();
	}
	
	public Child(String ID, String firstName, String lastName) {
		super();
		setID(ID);
		setFirstName(firstName);
		setLastName(lastName);
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
		ID = ID;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the mother
	 */
	public String getMother() {
		return mother;
	}
	/**
	 * @param mother the mother to set
	 */
	public void setMother(String mother) {
		this.mother = mother;
	}
	/**
	 * @return the father
	 */
	public String getFather() {
		return father;
	}
	/**
	 * @param father the father to set
	 */
	public void setFather(String father) {
		this.father = father;
	}
	/**
	 * @return the contactAddress
	 */
	public String getContactAddress() {
		return contactAddress;
	}
	/**
	 * @param contactAddress the contactAddress to set
	 */
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}
	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	/**
	 * @return the contactPhone1
	 */
	public String getContactPhone1() {
		return contactPhone1;
	}
	/**
	 * @param contactPhone1 the contactPhone1 to set
	 */
	public void setContactPhone1(String contactPhone1) {
		this.contactPhone1 = contactPhone1;
	}
	/**
	 * @return the contactPhone2
	 */
	public String getContactPhone2() {
		return contactPhone2;
	}
	/**
	 * @param contactPhone2 the contactPhone2 to set
	 */
	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}
	/**
	 * @return the daycare
	 */
	public String getDaycare() {
		return daycare;
	}
	/**
	 * @param daycare the daycare to set
	 */
	public void setDaycare(String daycare) {
		this.daycare = daycare;
	}
	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}
	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
