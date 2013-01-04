package me.ramuta.daycare.activity;

import java.util.Map;
import java.util.Set;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import me.ramuta.daycare.R;
import me.ramuta.daycare.activity.LoginActivity.ResponseReceiver;
import me.ramuta.daycare.adapter.TabsAdapter;
import me.ramuta.daycare.data.CameraHelper;
import me.ramuta.daycare.data.DataHolder;
import me.ramuta.daycare.data.GalleryHelper;
import me.ramuta.daycare.fragment.AgendaFragment;
import me.ramuta.daycare.fragment.AppleFragment;
import me.ramuta.daycare.fragment.GalleryFragment;
import me.ramuta.daycare.fragment.GroupFragment;
import me.ramuta.daycare.fragment.NewsFragment;
import me.ramuta.daycare.service.MainService;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity {
	private static final String TAG = "MainActivity";
	
	// actionbar in tabi
	private ActionBar actionBar;
	private TabHost mTabHost;
    private ViewPager  mViewPager;
    private TabsAdapter mTabsAdapter;
    
    // galerry/camera
 	private static int IMAGE_FLAG; // gallery: 1, camera: 2
 	private Uri fileUri;
 	private final static int GALLERY_REQUEST_CODE = 111;
 	private final static int CAMERA_REQUEST_CODE = 222;
 	public static final String PIC_PATH = "getpicpath";
 	
 	//
 	private MainResponseReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i(TAG, "MainActivity started");
		
		IntentFilter filter = new IntentFilter(MainResponseReceiver.MAIN_ACTION_RESP);
  		filter.addCategory(Intent.ACTION_DEFAULT);
  		receiver = new MainResponseReceiver();
  		registerReceiver(receiver, filter);
		
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
        		GalleryFragment.class, null);
        mTabsAdapter.addTab(actionBar.newTab().setText(this.getString(R.string.tab_agenda_title)),
        		AgendaFragment.class, null);
        /*
        mTabsAdapter.addTab(actionBar.newTab().setText(this.getString(R.string.tab_menu_title)),
        		AppleFragment.class, null);
		*/
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        
	}
	
	public class MainResponseReceiver extends BroadcastReceiver {
		public static final String MAIN_ACTION_RESP = "daycare.ramuta.intent.action.MESSAGE_PROCESSED";

		@Override
		public void onReceive(Context context, Intent intent) {
			
			//mTabHost.invalidate();
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
		/*
		case R.id.menu_settings:
			Log.i(TAG, "Settings");
			return true;
		*/
		case R.id.menu_logout:
			logoutAlertDialog();
			return true;
		case R.id.menu_add_news:
			Intent addNewsIntent = new Intent(MainActivity.this, AddNewsActivity.class);
			startActivity(addNewsIntent);
			return true;
		case R.id.menu_add_photo:
			openGetImageDialogBox(); // open dialog box to choose between camera and gallery
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/** AlertDialog that asks you if you really want to logout. */
	private void logoutAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		AlertDialog dialog;
		builder.setTitle(R.string.logout_dialog_title);
		builder.setMessage(R.string.logout_dialog_text);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   logout();
		           }
		       });
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               dialog.dismiss();
		           }
		       });

		// Create the AlertDialog
		dialog = builder.create();
		dialog.show();
	}

	/** logout by deleting saved email and password and closing app. */
	private void logout() {
		SharedPreferences loginPrefs = getSharedPreferences("LoginActivity", 0);
		loginPrefs.edit().clear().commit();
		MainActivity.this.finish();
	}
	
	@Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {   	
		try {
			if (reqCode == GALLERY_REQUEST_CODE) {
				Uri getData = data.getData(); 
				String picPath = GalleryHelper.getRealPathFromURI(getApplicationContext(), getData);
				GalleryHelper.setImagePath(picPath);
				goToAddItemActivity(picPath);				
			} else if (reqCode == CAMERA_REQUEST_CODE) {
				String photoPath = CameraHelper.getPhotoPath();
				if (resultCode != 0) {
					goToAddItemActivity(photoPath);
				} else {
					Toast.makeText(MainActivity.this, R.string.result_camera_none, Toast.LENGTH_SHORT).show();
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "No item chosen.");
		}
	}
    
    /** Go to AddPhotoActivity. */
    private void goToAddItemActivity(String picPath) {
    	Intent addPhotoIntent = new Intent(this, AddPhotoActivity.class);
    	addPhotoIntent.putExtra(PIC_PATH, picPath);
    	startActivity(addPhotoIntent);
    }
    
    /** Opens a dialog box to choose between Camera and Gallery. */
    private void openGetImageDialogBox() {
    	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
		alert.setTitle(R.string.image_box_title);
		alert.setMessage(R.string.image_box_message);
		alert.setNegativeButton(R.string.image_box_gallery, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				IMAGE_FLAG = 1;
				Log.i("TAG", "Gremo v gallery");
				goToGallery();
			}});
		alert.setPositiveButton(R.string.image_box_camera, new DialogInterface.OnClickListener() {				
			public void onClick(DialogInterface dialog, int which) {
				IMAGE_FLAG = 2;
				Log.i("TAG", "Odpri fotoaparat");
				//goToIntentCamera();
				goToCameraActivity();
			}
		}).show();
    }
    
    /** Intent to open the gallery. */
    private void goToGallery() {
    	Intent intentGallery = new Intent();
    	intentGallery.setType("image/*");
    	intentGallery.setAction(Intent.ACTION_GET_CONTENT);
    	startActivityForResult(Intent.createChooser(intentGallery, "Select image"), GALLERY_REQUEST_CODE);
    }
    
    /** Intent to open original Android Camera app. */
    private void goToIntentCamera() {
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    	
    	fileUri = CameraHelper.getOutputMediaFileUri(CameraHelper.MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
    	startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }
    
    /** Intent to open custom made camera. */
    private void goToCameraActivity() {
    	Intent cameraIntent = new Intent(this, CameraActivity.class);
    	startActivity(cameraIntent);
    }
    
    @Override
    public void onDestroy() {
    	this.unregisterReceiver(receiver);
    	super.onDestroy();
    }
}
