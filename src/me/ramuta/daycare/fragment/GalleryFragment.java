package me.ramuta.daycare.fragment;

import java.util.ArrayList;

import me.ramuta.daycare.R;
import me.ramuta.daycare.activity.NewsDetailsActivity;
import me.ramuta.daycare.adapter.GalleryAdapter;
import me.ramuta.daycare.adapter.GroupAdapter;
import me.ramuta.daycare.data.DataHolder;
import me.ramuta.daycare.object.Photo;
import me.ramuta.daycare.object.Post;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragment;

public class GalleryFragment extends SherlockFragment {
	private static final String TAG = "GalleryFragment";
	
	// UI
	private GridView grid;
	private GalleryAdapter galleryAdapter;
	
	// data
	private DataHolder dataHolder = new DataHolder();
	private ArrayList<Post> photos = new ArrayList<Post>();

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gallery, container, false);
		
		grid = (GridView)view.findViewById(R.id.gallery_grid);
		
		return view;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		photos = dataHolder.getPhotos();
		galleryAdapter = new GalleryAdapter(getActivity(), photos);
		grid.setAdapter(galleryAdapter);
		grid.setOnItemClickListener(listener);
	}
	
	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent detailsIntent = new Intent(getActivity(), NewsDetailsActivity.class);
			detailsIntent.putExtra(NewsFragment.POSITION, position);
			detailsIntent.putExtra(NewsFragment.POST_TYPE, 654);
			startActivity(detailsIntent);
		}
	};
}
