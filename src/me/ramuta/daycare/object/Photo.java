package me.ramuta.daycare.object;

import java.util.ArrayList;

public class Photo {
	private String photoUrl;
	private ArrayList<Child> taggedChildren = new ArrayList<Child>();
	
	public Photo() {
		super();
	}
	
	public Photo(String photoUrl) {
		super();
		setPhotoUrl(photoUrl);
	}

	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * @param photoUrl the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
