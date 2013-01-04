package me.ramuta.daycare.adapter;

import java.util.ArrayList;

import me.ramuta.daycare.R;
import me.ramuta.daycare.object.Child;
import me.ramuta.daycare.object.Photo;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {
private static final String TAG = "GalleryAdapter";
	
	private Context mContext;
	
	private ArrayList<Photo> photos;
	private LayoutInflater li;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	public GalleryAdapter(Context c, ArrayList<Photo> photos) {
		mContext = c;
		li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.photos = photos;
		
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

	@Override
	public int getCount() {
		
		return photos.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view==null) {
			view = li.inflate(R.layout.fragment_gallery_item, null);
		}
		
		Photo photo = photos.get(position);
		
		ImageView image = (ImageView)view.findViewById(R.id.gallery_item_image);
		
		imageLoader.displayImage(photo.getPhotoUrl(), image, options);
		
		return view;
	}

}
