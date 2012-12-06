package me.ramuta.daycare.data;

import java.util.ArrayList;

import android.util.Log;

import me.ramuta.daycare.object.Child;
import me.ramuta.daycare.object.Group;
import me.ramuta.daycare.object.Post;

public class DataHolder {
	private static final String TAG = "DataHolder";
	
	private static ArrayList<Post> posts = new ArrayList<Post>();
	private static ArrayList<Child> children = new ArrayList<Child>();
	
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
}
