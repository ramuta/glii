package me.ramuta.daycare.fragment;

import java.util.ArrayList;

import me.ramuta.daycare.R;
import me.ramuta.daycare.activity.AddNewsActivity;
import me.ramuta.daycare.activity.AddPhotoActivity;
import me.ramuta.daycare.data.DataHolder;
import me.ramuta.daycare.object.Group;
import me.ramuta.daycare.service.AddNewsService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.style.BulletSpan;
import android.util.Log;
import android.widget.Toast;

public class SelectGroupDialogFragment extends DialogFragment {
	private static final String TAG = "SelectGroupDialogFragment";
	private String[] groupsList;
	private DataHolder dataHolder = new DataHolder();
	
	private ArrayList<Group> groups = new ArrayList<Group>();
	
	private ArrayList<String> groupNames = new ArrayList<String>();
	
	private ArrayList<Group> selectedGroups = new ArrayList<Group>();
	
	String comment;
	boolean withPhoto;
	String photoPath;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//comment = savedInstanceState.get(AddNewsActivity.COMMENT).toString();
		
		comment = getArguments().getString(AddNewsActivity.COMMENT);
		Log.i(TAG, "comment: "+comment);
		withPhoto = getArguments().getBoolean(AddNewsActivity.WITH_PHOTO);
		
		if(withPhoto) {
			photoPath = getArguments().getString(AddPhotoActivity.PHOTO_PATH);
		}
		
		groups = dataHolder.getGroups();
		
		for (int y = 0; y < groups.size(); y++) {
			String groupNameGet = groups.get(y).getName();
			groupNames.add(groupNameGet);
			Log.i(TAG, "groupsNames: "+groupNames);
		}
		
		groupsList = convertToStringArray(groupNames);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.select_group_title)
		.setMultiChoiceItems(groupsList, null, new DialogInterface.OnMultiChoiceClickListener() {		
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				Group thisGroup = groups.get(which);
				if (isChecked) {
					selectedGroups.add(thisGroup);
					Log.i(TAG, "izbran: "+thisGroup);
				} else if (selectedGroups.contains(thisGroup)) {
					selectedGroups.remove(thisGroup);
				};
				Log.i(TAG, "izbrani: "+selectedGroups);
			}
		})
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendPost();
				dismiss();
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				selectedGroups.clear();
				dismiss();
			}
		});
		return builder.create();
	}
	
	/** Calls AddNewsService to send the news */
	private void callAddNewsService() {
		String group = getGroups(selectedGroups);
		
		Intent addPostIntent = new Intent(getActivity(), AddNewsService.class);
		addPostIntent.putExtra(AddNewsActivity.WITH_PHOTO, withPhoto);
		addPostIntent.putExtra(AddNewsActivity.COMMENT, comment);
		addPostIntent.putExtra(AddNewsActivity.GROUP, group);
		if(withPhoto) {
			addPostIntent.putExtra(AddPhotoActivity.PHOTO_PATH, photoPath);
		}
		getActivity().startService(addPostIntent);
	}
	
	/** Sends news through AddNewsService, makes Toast about sent news and finishes activity. */
	private void sendPost() {
		Toast.makeText(getActivity(), "Post sent", Toast.LENGTH_SHORT).show();
		callAddNewsService();
		getActivity().finish();
	}
	
	/** Builds a string out of selected groups that are stored in ArrayList. Groups are divided by ; so that web api can differenciate between them. */
	private String getGroups(ArrayList<Group> groupsArray) {
		StringBuilder sb = new StringBuilder();
		
		for(int g = 0; g < groupsArray.size(); g++) {
			String oneGroup = groupsArray.get(g).getID();
			sb.append(oneGroup+";");
		}
		
		String almostFinal = sb.toString();
		int lenghtAlmostFinal = almostFinal.length();
		
		String finalString = almostFinal.substring(0, lenghtAlmostFinal-1);
		Log.i(TAG, "finalString: "+finalString);
		
		return finalString;
	}
	
	/** ArrayList<String> to String[] */
	private String[] convertToStringArray(ArrayList<String> names) {
		String[] converted = new String[names.size()];
		converted = names.toArray(converted);
		return converted;
	}
}
