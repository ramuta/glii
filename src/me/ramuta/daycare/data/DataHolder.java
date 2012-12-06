package me.ramuta.daycare.data;

import java.util.ArrayList;

import me.ramuta.daycare.object.Post;

public class DataHolder {
	private static ArrayList<Post> posts = new ArrayList<Post>();
	
	public DataHolder() {
		super();
	}
	
	// initialize some dummy data
	public void init() {
		Post post1 = new Post("1", "Odpravljamo se na sprehod.", "Maja", "Novak", false);
		Post post2 = new Post("2", "Obiskal nas je Božièek.", "Tina", "Bambina", false);
		Post post3 = new Post("3", "Danes imamo risarsko delavnico", "Maja", "Novak", false);
		
		posts.add(post1);
		posts.add(post2);
		posts.add(post3);
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
}
