package me.ramuta.daycare.data;

import java.util.ArrayList;
import java.util.Collections;

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
	private static ArrayList<Post> photos = new ArrayList<Post>();
	private static ArrayList<Event> events = new ArrayList<Event>();
	
	// post types
	public enum PostType {
		NEWS, GALLERY
	}
	
	public DataHolder() {
		super();
	}
	
	// initialize some dummy data
	public void init() {
		
		// posts
		
		/*
		Post post22 = new Post("1", "Obiskal nas je božièek.", "Metka", "Novak", "Zajèki", true, "http://www.vgcc.edu/newsimages/daycare-santa-web.jpg", "http://www.vgcc.edu/newsimages/daycare-santa-web.jpg");
		Post post33 = new Post("2", "Šli smo na sprehod.", "Vesna", "Mikiè", "Zajèki", true, "http://c2717642.r42.cf0.rackcdn.com/75d4c49d-5ad9-47a0-a09b-feb8f721cd1f.jpg", "http://c2717642.r42.cf0.rackcdn.com/75d4c49d-5ad9-47a0-a09b-feb8f721cd1f.jpg");
		Post post44 = new Post("3", "Danes pojemo pesmice.", "Metka", "Novak", "Srnice", true, "http://api.glii.me/GliiImages/280a1b74-270c-40c9-8982-34226cbb762b.jpg", "http://api.glii.me/GliiImages/280a1b74-270c-40c9-8982-34226cbb762b.jpg");
		Post post55 = new Post("4", "Imamo risarsko delavnico.", "Metka", "Novak", "Medvedki", true, "http://www.mylhumc.com/images/501_daycare-draw.jpg", "http://www.mylhumc.com/images/501_daycare-draw.jpg");
		posts.add(post22);
		posts.add(post33);
		posts.add(post44);
		posts.add(post55);
		
		
		// photos
		Post post66 = new Post("1", "Obiskal nas je božièek.", "Metka", "Novak", "Zajèki", true, "http://www.vgcc.edu/newsimages/daycare-santa-web.jpg", "http://www.vgcc.edu/newsimages/daycare-santa-web.jpg");
		Post post77 = new Post("2", "Šli smo na sprehod.", "Vesna", "Mikiè", "Zajèki", true, "http://c2717642.r42.cf0.rackcdn.com/75d4c49d-5ad9-47a0-a09b-feb8f721cd1f.jpg", "http://c2717642.r42.cf0.rackcdn.com/75d4c49d-5ad9-47a0-a09b-feb8f721cd1f.jpg");
		Post post88 = new Post("4", "Imamo risarsko delavnico.", "Metka", "Novak", "Medvedki", true, "http://www.mylhumc.com/images/501_daycare-draw.jpg", "http://www.mylhumc.com/images/501_daycare-draw.jpg");
		photos.add(post66);
		photos.add(post77);
		photos.add(post88);
		*/
		
		// events
		Event event1 = new Event("Obisk Dedka Mraza.", "15.12.2012");
		Event event2 = new Event("Likovna delavnica.", "20.12.2012");
		Event event3 = new Event("Božièni dan.", "25.12.2012");
		events.add(event1);
		events.add(event2);
		events.add(event3);
		
		// children in a group
		/*
		Child child11 = new Child("1", "Mihec", "Pihec", "http://www.my-childs-future.net/wp-content/uploads/9_3_orig.jpg", "http://www.my-childs-future.net/wp-content/uploads/9_3_orig.jpg");
		Child child22 = new Child("2", "Ana", "Banana", "http://tusb.stanford.edu/wp-content/uploads/2011/01/Child-pic3.jpg", "http://tusb.stanford.edu/wp-content/uploads/2011/01/Child-pic3.jpg");
		Child child33 = new Child("3", "Tinca", "Binca", "http://www.expectmorechildcare.com/images/child-laughing-at-day-care2.jpg", "http://www.expectmorechildcare.com/images/child-laughing-at-day-care2.jpg");
		ArrayList<Child> thisChldrn = new ArrayList<Child>();
		ArrayList<Child> othrChldrn = new ArrayList<Child>();
		thisChldrn.add(child11);
		thisChldrn.add(child22);
		othrChldrn.add(child33);
		Group thisGroup = new Group("1", "Zajèki", thisChldrn);
		Group othrGroup = new Group("2", "Srnice", othrChldrn);
		groups.add(thisGroup);
		groups.add(othrGroup);
		*/
	}
	
	/*
	 * Set post objects for creating news stream.
	 */
	public void setPostObjects(String response, PostType type) {
		//clear previous entries to prevent double entries
		if (type == PostType.NEWS) {
			posts.clear();
		} else if (type == PostType.GALLERY) {
			photos.clear();
		}
		
		// parse response
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
					post = new Post(postID, text, name, lastName, null, hasImage, null, null); // TODO: namesto null naj bo grupa
				} else {
					hasImage = true;
					post = new Post(postID, text, name, lastName, null, hasImage, photoUrl, thumbUrl);
				}
				
				//Log.i(TAG, text+", "+name+", "+hasImage+", "+photoUrl);
				
				if (type == PostType.NEWS) {
					posts.add(post);
				} else if (type == PostType.GALLERY) {
					photos.add(post);
				}
				
			}
			
			if (type == PostType.NEWS) {
				Collections.reverse(posts); // reverse for the correct date order
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
					
					JSONObject jPhoto;
					String photoUrl = "";
					String thumbUrl = "";
					
					// try catch if Photo parameter is null
					try {
						jPhoto = childJObject.getJSONObject("Photo");
						
						photoUrl = jPhoto.getString("PhotoUrl");
						thumbUrl = jPhoto.getString("ThumbUrl");
					} catch (Exception e) {
						jPhoto = null;
						photoUrl = "";
						thumbUrl = "";
					}
					
					Child child = new Child(childID, childName, childLastName, photoUrl, thumbUrl);
					//child.setImageUrl("http://m5.paperblog.com/i/32/323320/ten-annoying-child-actors-who-have-redeemed-t-L-Lr3kO3.jpeg");
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
	public static ArrayList<Post> getPhotos() {
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public static void setPhotos(ArrayList<Post> photos) {
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
