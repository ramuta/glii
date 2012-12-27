package me.ramuta.daycare.fragment;

import java.util.ArrayList;

import me.ramuta.daycare.R;
import me.ramuta.daycare.adapter.GroupAdapter;
import me.ramuta.daycare.data.DataHolder;
import me.ramuta.daycare.object.Child;
import me.ramuta.daycare.object.Group;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;

public class GroupFragment extends SherlockFragment {
	private static final String TAG = "GroupFragment";
	
	// data
	private DataHolder dataHolder = new DataHolder();	
	ArrayList<Child> groupChildren = new ArrayList<Child>();
	
	// spinner
	ArrayAdapter<String> groupAdapter;
	private Spinner groupChooser;
	private ArrayList<String> groupNames = new ArrayList<String>();
	
	// images
	private GroupAdapter imageAdapter;
	private GridView grid;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group, container, false);

		groupChooser = (Spinner)view.findViewById(R.id.group_choose);
		grid = (GridView)view.findViewById(R.id.group_grid);
		
		
		
		// images
		
	
        return view;
    }
	
	@Override
	public void onStart() {
		super.onStart();
		// spinner
		
		groupChooser.setOnItemSelectedListener(listener);
		
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//groupNames.add("All");
		
		for (int i = 0; i < dataHolder.getGroups().size(); i++) {
			Group group = dataHolder.getGroups().get(i);
			String groupName = group.getName();
			groupNames.add(groupName);
		}
		
		groupAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, groupNames);
		groupChooser.setAdapter(groupAdapter);
		
		// set click listener for spinner
		groupChildren = dataHolder.getAllChildren();
		imageAdapter = new GroupAdapter(getActivity(), groupChildren);
		
		grid.setAdapter(imageAdapter);
	}
	
	// click listener for spinner
	private OnItemSelectedListener listener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			String group = parent.getItemAtPosition(position).toString();
			Log.i(TAG, "group: "+group);
			
			for (int x = 0; x < dataHolder.getGroups().size(); x++) {
				Group sGroup = dataHolder.getGroups().get(x);
				if (sGroup.getName().equals(group)) {
					Log.i(TAG, "JE enako ime");
					groupChildren = sGroup.getChildren();
					//imageAdapter = new GroupAdapter(getActivity(), groupChildren);
				} else if (group.equals("All")) {
					Log.i(TAG, "Vsi");
					groupChildren = dataHolder.getAllChildren();
				}
			}

			//groupChildren = dataHolder.getChildrenByGroup(group); // TODO: pošlji groupChildren v adapter
			
			imageAdapter = new GroupAdapter(getActivity(), groupChildren);
			
			grid.setAdapter(imageAdapter);
			
			for (int y = 0; y < groupChildren.size(); y++) {
				Child theChild = groupChildren.get(y);				
				Log.i(TAG, "child: "+theChild.getFirstName());
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {}
	};
}
