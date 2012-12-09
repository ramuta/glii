package me.ramuta.daycare.data;

import java.util.ArrayList;

import android.util.Log;

import me.ramuta.daycare.object.Child;
import me.ramuta.daycare.object.Event;
import me.ramuta.daycare.object.Group;
import me.ramuta.daycare.object.Photo;
import me.ramuta.daycare.object.Post;

public class DataHolder {
	private static final String TAG = "DataHolder";
	
	private static ArrayList<Post> posts = new ArrayList<Post>();
	private static ArrayList<Child> children = new ArrayList<Child>();
	private static ArrayList<String> groups = new ArrayList<String>();
	private static ArrayList<Photo> photos = new ArrayList<Photo>();
	private static ArrayList<Event> events = new ArrayList<Event>();
	
	public DataHolder() {
		super();
	}
	
	// initialize some dummy data
	public void init() {
		// posts
		Post post1 = new Post("1", "Odpravljamo se na sprehod.", "Maja", "Novak", false);
		Post post2 = new Post("2", "Obiskal nas je Božièek.", "Tina", "Bambina", false);
		Post post3 = new Post("3", "Danes imamo risarsko delavnico", "Maja", "Novak", false);		
		posts.add(post1);
		posts.add(post2);
		posts.add(post3);
		
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
		
		// photos
		Photo photo1 = new Photo("http://www.coastal.ca.gov/publiced/directory/masks.jpg");
		Photo photo2 = new Photo("http://img.ehowcdn.com/article-new/ehow/images/a07/g2/9l/daysoftheweek-kindergarten-activities-800x800.jpg");
		Photo photo3 = new Photo("http://www.colourbox.com/preview/1197822-242755-art-and-craft-activity-in-the-kindergarten.jpg");
		Photo photo4 = new Photo("http://www.timescolonist.com/news/7321573.bin?size=620x400s");
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
	
	/**
	 * Insert group name and you'll get an arraylist of children objects in that group.
	 *  */
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

	}

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
	 * @return the children
	 */
	public static ArrayList<Child> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public static void setChildren(ArrayList<Child> children) {
		DataHolder.children = children;
	}

	/**
	 * @return the groups
	 */
	public static ArrayList<String> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public static void setGroups(ArrayList<String> groups) {
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
