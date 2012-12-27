package me.ramuta.daycare.fragment;

import me.ramuta.daycare.R;
import me.ramuta.daycare.activity.LoginActivity.ResponseReceiver;
import me.ramuta.daycare.adapter.NewsAdapter;
import me.ramuta.daycare.data.DataHolder;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class NewsFragment extends SherlockListFragment {
	private static final String TAG = "NewsFragment";
	private NewsAdapter newsAdapter;
	private DataHolder dataHolder = new DataHolder();

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.fragment_news, container, false);
		// instanciiraj in registriraj response receiver
  		
		
		//newsAdapter = new NewsAdapter(getActivity(), R.layout.fragment_news_item, dataHolder.getPosts());
		
		//setListAdapter(newsAdapter);
		
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	@Override
	public void onStart() {
		super.onStart();
	}
	

	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
		newsAdapter = new NewsAdapter(getActivity(), R.layout.fragment_news_item, dataHolder.getPosts());
		
		setListAdapter(newsAdapter);
	}

}
