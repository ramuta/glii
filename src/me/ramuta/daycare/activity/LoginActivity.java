package me.ramuta.daycare.activity;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;

import me.ramuta.daycare.R;
import me.ramuta.daycare.R.id;
import me.ramuta.daycare.R.layout;
import me.ramuta.daycare.R.menu;
import me.ramuta.daycare.R.string;
import me.ramuta.daycare.object.Login;
import me.ramuta.daycare.service.AuthService;
import me.ramuta.daycare.service.MainService;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends SherlockFragmentActivity {
	private static final String TAG = "LoginActivity";
	
	// shared prefs
	public static final String EMAIL = "useremail";
	public static final String PASSWORD = "userpassword";

	// service broadcast receiver
	public ResponseReceiver receiver;

	// The default email to populate the email field with.
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	// Values for email and password at the time of the login attempt.
	private String mEmail = null;
	private String mPassword = null;
	Login login;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		
		Log.i(TAG, "LoginScreen odprt");
		
		// instanciiraj in registriraj response receiver
  		IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
  		filter.addCategory(Intent.ACTION_DEFAULT);
  		receiver = new ResponseReceiver();
  		registerReceiver(receiver, filter);
  		
  		mEmailView = (EditText) findViewById(R.id.email);
  		mPasswordView = (EditText) findViewById(R.id.password);
  		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		loginButton = (Button)findViewById(R.id.sign_in_button);
  		
  		try {
			// get shared prefs data if any (email, password)
			SharedPreferences userData = getSharedPreferences("LoginActivity", Activity.MODE_PRIVATE);
			mEmail = userData.getString(EMAIL, null);
			mPassword = userData.getString(PASSWORD, null);
			login = new Login(mEmail, mPassword); // Save email and password to Login object.
		} catch (Exception e) {
			Log.i(TAG, "error shared prefs");
		}

  		if (!(mEmail == null)) { // if the previous login is saved, start auth service
  			showProgress(true);
  			startAuthService();
  		} else { // otherwise ask for login
  		
			// Set up the login form.
			mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
			
			mEmailView.setText(mEmail);
	
			
			mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
							if (id == R.id.login || id == EditorInfo.IME_NULL) {
								attemptLogin();
								return true;
							}
							return false;
						}
					});
	
			
	
			loginButton.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							attemptLogin();
						}
					});
  		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	/** Response receiver for ImageService. */
    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP = "glii.ramuta.intent.action.MESSAGE_PROCESSED";
                
        @Override
        public void onReceive(Context context, Intent intent) {      
            try {
				showProgress(false);
				
				boolean success = intent.getBooleanExtra(AuthService.SUCCESS_RESPONSE, false);			

				if (success) {
					Intent intentMain = new Intent(LoginActivity.this, MainActivity.class); // start main activity
					startActivity(intentMain);
					LoginActivity.this.finish();
				} else {
					mPasswordView.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();
				}
				
            } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
        }
    }

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt. TODO: shrani v shared prefs!
		mEmail = mEmailView.getText().toString();		
		mPassword = mPasswordView.getText().toString();
		login = new Login(mEmail, mPassword); // Save email and password to Login object.
		
		saveToSharedPrefs(mEmail, mPassword);

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true); 
			
			startAuthService();
		}
	}

	private void startAuthService() {
		//start AuthService
		Intent intentAuthService = new Intent(LoginActivity.this, AuthService.class);
		startService(intentAuthService);
	}
	
	private void saveToSharedPrefs(String email, String password) {
		SharedPreferences saveMailPass = getSharedPreferences("LoginActivity", 0);
		SharedPreferences.Editor editor = saveMailPass.edit();
		editor.putString(EMAIL, email);
		editor.putString(PASSWORD, password);
		editor.commit();
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	@Override
    public void onDestroy() {
    	this.unregisterReceiver(receiver);
    	super.onDestroy();
    }
}
