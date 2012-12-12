package me.ramuta.daycare.activity;

import me.ramuta.daycare.R;
import me.ramuta.daycare.data.DataHolder;
import me.ramuta.daycare.fragment.SelectGroupDialogFragment;
import me.ramuta.daycare.service.AddNewsService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class AddNewsActivity extends SherlockFragmentActivity {
	private static final String TAG = "AddNewsActivity";
	
	// actionbar
	private ActionBar actionBar;
	
	// UI
	private EditText commentBox;
	private Button sendButton;
	
	//
	private DataHolder dataHolder = new DataHolder();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        
        // actionbar
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        // initialize UI
        commentBox = (EditText)findViewById(R.id.add_news_comment);
        //selectGroupButton = (Button)findViewById(R.id.add_news_select_group);
        sendButton = (Button)findViewById(R.id.add_news_send);
        
        // send button click listener
        sendButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Send button");
				Intent addPostIntent = new Intent(AddNewsActivity.this, AddNewsService.class);
				startService(addPostIntent);
				showSelectGroupDialog();
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
			showSelectGroupDialog();
			return true;
		case android.R.id.home:
			AddNewsActivity.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void showSelectGroupDialog() {
		FragmentManager fm = getSupportFragmentManager();
		SelectGroupDialogFragment groupDialog = new SelectGroupDialogFragment();
		groupDialog.show(fm, "dialog");
	}
}
