package me.ramuta.daycare.fragment;

import me.ramuta.daycare.R;
import me.ramuta.daycare.adapter.AgendaAdapter;
import me.ramuta.daycare.data.DataHolder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

public class AgendaFragment extends SherlockListFragment {
	private static final String TAG = "AgendaFragment";
	
	// UI
	private AgendaAdapter agendaAdapter;
	
	// data
	private DataHolder dataHolder = new DataHolder();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.fragment_news, container, false);
		agendaAdapter = new AgendaAdapter(getActivity(), R.layout.fragment_agenda_item, dataHolder.getEvents());
		setListAdapter(agendaAdapter);
		
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
