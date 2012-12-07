package me.ramuta.daycare.activity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import me.ramuta.daycare.R;
import me.ramuta.daycare.adapter.TabsAdapter;
import me.ramuta.daycare.data.DataHolder;
import me.ramuta.daycare.fragment.AppleFragment;
import me.ramuta.daycare.fragment.GroupFragment;
import me.ramuta.daycare.fragment.NewsFragment;
import me.ramuta.daycare.service.MainService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TabHost;

public class MainActivity extends SherlockFragmentActivity {
	private static final String TAG = "MainActivity";
	
	// actionbar in tabi
	private ActionBar actionBar;
	private TabHost mTabHost;
    private ViewPager  mViewPager;
    private TabsAdapter mTabsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i(TAG, "MainActivity started");
		
		// TODO: to se bo klicalo po avtentikaciji
		Intent intent = new Intent(MainActivity.this, MainService.class);
		startService(intent);
		Log.i(TAG, "intent for service sent");
		
		// data holder z nekaj dummy objekti
		DataHolder dataHolder = new DataHolder();
		dataHolder.init();	
		
		// actionbar
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
        // viewpager
        mViewPager = (ViewPager)findViewById(R.id.pager);

        // tabs
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mTabsAdapter = new TabsAdapter(this, mViewPager);

        mTabsAdapter.addTab(actionBar.newTab().setText(this.getString(R.string.tab_news_title)),
        		NewsFragment.class, null);
        mTabsAdapter.addTab(actionBar.newTab().setText(this.getString(R.string.tab_group_title)),
        		GroupFragment.class, null);
        mTabsAdapter.addTab(actionBar.newTab().setText(this.getString(R.string.tab_gallery_title)),
        		AppleFragment.class, null);
        mTabsAdapter.addTab(actionBar.newTab().setText(this.getString(R.string.tab_agenda_title)),
        		AppleFragment.class, null);
        mTabsAdapter.addTab(actionBar.newTab().setText(this.getString(R.string.tab_menu_title)),
        		AppleFragment.class, null);

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Log.i(TAG, "Settings");
			return true;
		case R.id.menu_add_news:
			Intent addNewsIntent = new Intent(MainActivity.this, AddNewsActivity.class);
			startActivity(addNewsIntent); // lahko je tudi Theme Sherlock DIALOG
			return true;
		case R.id.menu_add_photo:
			// TODO: photo intent
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
