package me.ramuta.daycare.activity;

import me.ramuta.daycare.R;
import me.ramuta.daycare.service.AddNewsService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class AddPhotoActivity extends SherlockActivity {
	private static final String TAG = "AddPhotoActivity";
	
	public static final String PHOTO_PATH = "photopath";
	
	// ImageLoader
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	// actionbar
	private ActionBar actionBar;
	
	// data
	private String imagePath;
	
	// UI
	private ImageView image;
	private Button sendPhoto;
	private EditText commentBox;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        
        // instantiate image loader
  		imageLoader = ImageLoader.getInstance();
  		options = new DisplayImageOptions.Builder()
 		.showStubImage(R.drawable.ic_launcher)
 		.cacheInMemory()
 		.cacheOnDisc()
 		.build();
  		imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
  		
  		// actionbar
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
  		
  		// UI initialization
  		image = (ImageView)findViewById(R.id.add_photo_image);
  		sendPhoto = (Button)findViewById(R.id.add_photo_send);
  		commentBox = (EditText)findViewById(R.id.add_photo_commentbox);
  		
  		// get Intent extras
  		Intent intent = getIntent();
		imagePath = intent.getStringExtra(MainActivity.PIC_PATH);
		Log.i(TAG, "imagePath: "+imagePath);
		//imageLoader.displayImage("file://"+imagePath, image, options);
		
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
		
		image.setImageBitmap(bitmap);
		
		// photo onclick
		sendPhoto.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent sendServiceIntent = new Intent(AddPhotoActivity.this, AddNewsService.class);
				sendServiceIntent.putExtra(AddNewsActivity.WITH_PHOTO, true);
				sendServiceIntent.putExtra(AddNewsActivity.COMMENT, commentBox.getText().toString());
				sendServiceIntent.putExtra(PHOTO_PATH, imagePath);
				startService(sendServiceIntent);
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_add_news, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_send_news:
			return true;
		case android.R.id.home:
			AddPhotoActivity.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
