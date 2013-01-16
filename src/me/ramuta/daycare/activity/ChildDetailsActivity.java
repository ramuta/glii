package me.ramuta.daycare.activity;

import me.ramuta.daycare.R;
import me.ramuta.daycare.data.DataHolder;
import me.ramuta.daycare.fragment.GroupFragment;
import me.ramuta.daycare.object.Child;
import me.ramuta.daycare.object.Group;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ChildDetailsActivity extends SherlockActivity {

private static final String TAG = "NewsDetailsActivity";
	
	// actionbar
	private ActionBar actionBar;
	
	// imageloader
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	//
	private ImageButton callButton;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_details);
        
        // actionbar
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        // imageloader
        imageLoader = ImageLoader.getInstance();
		
 		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
 		
 		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ChildDetailsActivity.this)
        .defaultDisplayImageOptions(options)
        //.memoryCache(new WeakMemoryCache())
        .build();
 		imageLoader.init(config);
        
        // get intent
        Intent intent = getIntent();
        int position = intent.getIntExtra(GroupFragment.POSITION_STRING, 0);
        int groupFlag = intent.getIntExtra(GroupFragment.GROUP_FLAG_STRING, 0);
        
        Child child = null;
        Group group = null;
		try {
			group = DataHolder.getGroups().get(groupFlag);
			child = group.getChildren().get(position);
		} catch (Exception e) {
			// TODO Problem
			e.printStackTrace();
		}
        
        Log.i(TAG, "child name: "+child.getFirstName());
        
        String childName = child.getFirstName()+" "+child.getLastName();
        
        actionBar.setTitle(childName);
        
        TextView name = (TextView)findViewById(R.id.child_name);
        ImageView image = (ImageView)findViewById(R.id.child_image);
        callButton = (ImageButton)findViewById(R.id.child_contact);
        
        name.setText(childName);
        imageLoader.displayImage(child.getImageUrl(), image, options);
        
        callButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+"040666317"));
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(callIntent);
			}
		});
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getSupportMenuInflater().inflate(R.menu.activity_news_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			ChildDetailsActivity.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
