package me.ramuta.daycare.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import me.ramuta.daycare.R;
import me.ramuta.daycare.object.Child;
import me.ramuta.daycare.object.Post;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupAdapter extends BaseAdapter {
	private static final String TAG = "GroupAdapter";
	
	private Context mContext;
	
	private ArrayList<Child> children;
	private LayoutInflater li;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	public GroupAdapter(Context c, ArrayList<Child> children) {
		mContext = c;
		li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.children = children;
		
		imageLoader = ImageLoader.getInstance();
		
 		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
 		
 		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c)
        .defaultDisplayImageOptions(options)
        //.memoryCache(new WeakMemoryCache())
        .build();
 		imageLoader.init(config);
	}
	
	public int getCount() {
        return children.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view==null) {
			view = li.inflate(R.layout.fragment_group_item, null);
		}
		
		Child child = children.get(position);
		
		ImageView image = (ImageView)view.findViewById(R.id.group_image);
		
		imageLoader.displayImage(child.getThumbUrl(), image, options);
		
		return view;
	}
}