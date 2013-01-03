package me.ramuta.daycare.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import me.ramuta.daycare.object.Child;
import me.ramuta.daycare.object.Event;
import me.ramuta.daycare.object.Group;
import me.ramuta.daycare.object.Photo;
import me.ramuta.daycare.object.Post;

public class DataHolder {
	private static final String TAG = "DataHolder";
	
	private static ArrayList<Post> posts = new ArrayList<Post>();
	//private static ArrayList<Child> children = new ArrayList<Child>();
	private static ArrayList<Group> groups = new ArrayList<Group>();
	private static ArrayList<Photo> photos = new ArrayList<Photo>();
	private static ArrayList<Event> events = new ArrayList<Event>();
	
	public DataHolder() {
		super();
	}
	
	// initialize some dummy data
	public void init() {
		
		/*
		// group and children
		Child child1 = new Child("1", "Tin", "Binbin");
		child1.setGroup(new Group("Zajèki"));
		child1.setImageUrl("http://www.ofiriha.org/wp-content/uploads/2010/11/South-Sudanese-child-in-Uganda.jpg");
		Child child2 = new Child("2", "Ana", "Banana");
		child2.setGroup(new Group("Medvedki"));
		child2.setImageUrl("http://m5.paperblog.com/i/32/323320/ten-annoying-child-actors-who-have-redeemed-t-L-Lr3kO3.jpeg");
		Child child3 = new Child("3", "Mihec", "Pihec");
		child3.setGroup(new Group("Medvedki"));
		child3.setImageUrl("http://us.123rf.com/400wm/400/400/oksun70/oksun701206/oksun70120600098/14105323-funny-child-drinking-fruits-over-white-background.jpg");
		Child child4 = new Child("4", "Denis", "Pokora");
		child4.setGroup(new Group("Srnice"));
		child4.setImageUrl("http://i00.i.aliimg.com/wsphoto/v0/672273646/Children-s-clothing-male-child-autumn-2012-drawstring-zipper-tooling-child-outerwear-font-b-tuxedo-b.jpg");
		Child child5 = new Child("5", "Maja", "Èbelca");
		child5.setGroup(new Group("Medvedki"));
		child5.setImageUrl("http://www.cornerstone-astrology.com/astrology-shop/5-13-thickbox/your-child.jpg");
		children.add(child1);
		children.add(child2);
		children.add(child3);
		children.add(child4);
		children.add(child5);
		//Log.i(TAG, "otrok1: "+children.get(0).getFirstName());
		 */
		
		// photos
		Photo photo1 = new Photo("http://www.coastal.ca.gov/publiced/directory/masks.jpg");
		Photo photo2 = new Photo("http://img.ehowcdn.com/article-new/ehow/images/a07/g2/9l/daysoftheweek-kindergarten-activities-800x800.jpg");
		Photo photo3 = new Photo("http://www.colourbox.com/preview/1197822-242755-art-and-craft-activity-in-the-kindergarten.jpg");
		Photo photo4 = new Photo("http://images.teamsugar.com/files/upl1/10/109609/18_2008/daycare.jpg");
		Photo photo5 = new Photo("http://www.mnn.com/sites/default/files/main_chanie_kindergarten.jpg");
		photos.add(photo1);
		photos.add(photo2);
		photos.add(photo3);
		photos.add(photo4);
		photos.add(photo5);
		
		// events
		Event event1 = new Event("Obisk Dedka Mraza.", "15.12.2012");
		Event event2 = new Event("Likovna delavnica.", "20.12.2012");
		Event event3 = new Event("Božièni dan.", "25.12.2012");
		events.add(event1);
		events.add(event2);
		events.add(event3);
	}
	
	/*
	 * Set post objects for creating news stream.
	 */
	public void setPostObjects(String response) {
		posts.clear();
		try {
			JSONArray jArray = new JSONArray(response);
			
			JSONObject jObject = null;
			for(int i = 0; i < jArray.length(); i++) {
				jObject = jArray.getJSONObject(i);
				
				String postID = jObject.getString("PostId");
				String text = jObject.getString("Note");
				String name = jObject.getString("AuthorName");
				String lastName = jObject.getString("AuthorSurname");
				
				JSONObject jPhoto;
				String photoUrl = "";
				String thumbUrl = "";
				
				// try catch if Photo parameter is null
				try {
					jPhoto = jObject.getJSONObject("Photo");
					
					photoUrl = jPhoto.getString("PhotoUrl");
					thumbUrl = jPhoto.getString("ThumbUrl");
				} catch (Exception e) {
					jPhoto = null;
					photoUrl = "";
					thumbUrl = "";
				}
				
				Post post;
				boolean hasImage;
				
				if (photoUrl.equals("")) {
					hasImage = false;
					post = new Post(postID, text, name, lastName, hasImage, null, null);
				} else {
					hasImage = true;
					post = new Post(postID, text, name, lastName, hasImage, photoUrl, thumbUrl);
				}
				
				//Log.i(TAG, text+", "+name+", "+hasImage+", "+photoUrl);
				
				posts.add(post);
			}
			
		} catch (JSONException e) {
			Log.e(TAG, "json ex: "+e);
		}
	}
	
	/** Convert string to group objects for creating groups. */
	public void setGroupObjects(String groupResponse) {
		groups.clear();
		try {
			JSONArray jArray = new JSONArray(groupResponse);
			
			JSONObject jObject = null;
			for(int i = 0; i < jArray.length(); i++) {
				jObject = jArray.getJSONObject(i);
				
				String groupID = jObject.getString("GroupId");
				String groupName = jObject.getString("GroupName");
				
				JSONArray childJArray = jObject.getJSONArray("Children");
				
				ArrayList<Child> tempChildren = new ArrayList<Child>();
				
				for (int g = 0; g < childJArray.length(); g++) {
					JSONObject childJObject = childJArray.getJSONObject(g);
					String childName = childJObject.getString("Name");
					String childLastName = childJObject.getString("Surname");
					String childID = childJObject.getString("ChildId");
					
					Child child = new Child(childID, childName, childLastName);
					child.setImageUrl("http://m5.paperblog.com/i/32/323320/ten-annoying-child-actors-who-have-redeemed-t-L-Lr3kO3.jpeg");
					tempChildren.add(child);
				}

				Group group = new Group(groupID, groupName, tempChildren);
				groups.add(group);
			}
			
		} catch (JSONException e) {
			Log.e(TAG, "json ex: "+e);
		}
	}
	
	/** Get arraylist of all children in all groups. */
	public ArrayList<Child> getAllChildren() {
		ArrayList<Child> allChildren = new ArrayList<Child>();
		
		for (int c = 0; c < groups.size(); c++) {
			Group group = groups.get(c);
			
			for (int cg = 0; cg < group.getChildren().size(); cg++) {
				Child gChild = group.getChildren().get(cg);
				allChildren.add(gChild);
			}
		}
		
		return allChildren;
	}
	
	/**
	 * Insert group name and you'll get an arraylist of children objects in that group.
	 *  
	public ArrayList<Child> getChildrenByGroup(String group) {
		ArrayList<Child> groupChildren = new ArrayList<Child>();
		if (group.equals("Vsi")) {
			return children;
		} else {
			for (int x = 0; x < children.size(); x++) {
				Child tempChild = children.get(x);
				//Log.i(TAG, "otrok2: "+tempChild.getFirstName());				
				//Log.i(TAG, "grupa otroka2: "+tempChild.getGroup().getName().toString());
				if (tempChild.getGroup().getName().equals(group)) {
					groupChildren.add(tempChild);			
				}
			}			
			return groupChildren;
		}

	}*/

	/**
	 * @return the posts
	 */
	public static ArrayList<Post> getPosts() {
		return posts;
	}

	/**
	 * @param posts the posts to set
	 */
	public static void setPosts(ArrayList<Post> posts) {
		DataHolder.posts = posts;
	}

	/**
	 * @return the groups
	 */
	public static ArrayList<Group> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public static void setGroups(ArrayList<Group> groups) {
		DataHolder.groups = groups;
	}

	/**
	 * @return the photos
	 */
	public static ArrayList<Photo> getPhotos() {
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public static void setPhotos(ArrayList<Photo> photos) {
		DataHolder.photos = photos;
	}

	/**
	 * @return the events
	 */
	public static ArrayList<Event> getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public static void setEvents(ArrayList<Event> events) {
		DataHolder.events = events;
	}
}
