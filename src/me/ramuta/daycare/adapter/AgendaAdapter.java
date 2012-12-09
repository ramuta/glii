package me.ramuta.daycare.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import me.ramuta.daycare.R;
import me.ramuta.daycare.object.Event;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AgendaAdapter extends ArrayAdapter<Event> {
	private static final String TAG = "ItemsAdapter";
	private LayoutInflater inflater;
	private ArrayList<Event> events = new ArrayList<Event>();
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private int textViewResourceId;

	public AgendaAdapter(Context context, int textViewResourceId, ArrayList<Event> events) {
		super(context, textViewResourceId, events);

		inflater = LayoutInflater.from(context);
		this.events = events;
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
		
		Event event = events.get(position);
		
		TextView text = (TextView)view.findViewById(R.id.agenda_text);
		
		String combineText = event.getDate() + ": " + event.getName();
		
		text.setText(combineText);
		
		return view;
	}	
}
