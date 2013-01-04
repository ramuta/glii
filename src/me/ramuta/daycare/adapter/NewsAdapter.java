package me.ramuta.daycare.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import me.ramuta.daycare.R;
import me.ramuta.daycare.object.Post;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<Post> {
	private static final String TAG = "ItemsAdapter";
	private LayoutInflater inflater;
	private ArrayList<Post> posts = new ArrayList<Post>();
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private int textViewResourceId;

	public NewsAdapter(Context context, int textViewResourceId, ArrayList<Post> posts) {
		super(context, textViewResourceId, posts);

		inflater = LayoutInflater.from(context);
		this.posts = posts;
		this.textViewResourceId = textViewResourceId;
		
		imageLoader = ImageLoader.getInstance();
 		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
 		
 		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        .defaultDisplayImageOptions(options)
        .memoryCache(new WeakMemoryCache())
        .build();
 		imageLoader.init(config);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(textViewResourceId, null);
		}
		
		Post post = posts.get(position);
		
		TextView author = (TextView)view.findViewById(R.id.news_author);
		TextView text = (TextView)view.findViewById(R.id.news_text);
		ImageView image = (ImageView)view.findViewById(R.id.news_image);
		
		author.setText(post.getAuthorFirstName()+" "+post.getAuthorLastName());
		text.setText(post.getText());
		
		boolean hasImage = post.hasImage();
		
		if (hasImage) {
			imageLoader.displayImage(post.getThumbUrl(), image, options);
		}
		
		return view;
	}
}
