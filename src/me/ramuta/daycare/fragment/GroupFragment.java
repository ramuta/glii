package me.ramuta.daycare.fragment;

import me.ramuta.daycare.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;

public class GroupFragment extends SherlockFragment {
	private static final String TAG = "GroupFragment";
	
	private Spinner groupChooser;
	String[] groups = {"Vsi", "Zajèki", "Srnice", "Medvedki"};

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group, container, false);

		ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, groups);
		groupChooser = (Spinner)view.findViewById(R.id.group_choose);
		groupChooser.setAdapter(groupAdapter);
	
        // Inflate the layout for this fragment
        return view;
    }
	
	@Override
	public void onStart() {
		super.onStart();
	}
}
