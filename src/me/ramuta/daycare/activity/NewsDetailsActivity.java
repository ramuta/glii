package me.ramuta.daycare.activity;

import me.ramuta.daycare.R;
import me.ramuta.daycare.data.DataHolder;
import me.ramuta.daycare.fragment.NewsFragment;
import me.ramuta.daycare.object.Post;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class NewsDetailsActivity extends SherlockActivity {
	private static final String TAG = "NewsDetailsActivity";
	
	// actionbar
	private ActionBar actionBar;
	
	// imageloader
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        
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
 		
 		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(NewsDetailsActivity.this)
        .defaultDisplayImageOptions(options)
        //.memoryCache(new WeakMemoryCache())
        .build();
 		imageLoader.init(config);
        
        // get intent
        Intent intent = getIntent();
        int position = intent.getIntExtra(NewsFragment.POSITION, 0);
        int type = intent.getIntExtra(NewsFragment.POST_TYPE, 987);
        
        // initialize view elements
        TextView author = (TextView)findViewById(R.id.news_details_author);
        TextView group = (TextView)findViewById(R.id.news_details_group);
        TextView text = (TextView)findViewById(R.id.news_details_text);
        ImageView image = (ImageView)findViewById(R.id.news_details_image);
        
        // get Post object
        Post post = new Post();
        if (type == 987) {
        	post = DataHolder.getPosts().get(position);
        } else if (type == 654) {
        	post = DataHolder.getPhotos().get(position);
        }
        
        author.setText(post.getAuthorFirstName()+" "+post.getAuthorLastName());
        group.setText(post.getGroup());
        text.setText(post.getText());
        
        if (post.hasImage()) {
        	imageLoader.displayImage(post.getPhotoUrl(), image, options);
        }        
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
			NewsDetailsActivity.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}