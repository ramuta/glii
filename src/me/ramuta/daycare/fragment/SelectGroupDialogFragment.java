package me.ramuta.daycare.fragment;

import java.util.ArrayList;

import me.ramuta.daycare.R;
import me.ramuta.daycare.activity.AddNewsActivity;
import me.ramuta.daycare.data.DataHolder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.style.BulletSpan;
import android.util.Log;
import android.widget.Toast;

public class SelectGroupDialogFragment extends DialogFragment {
	private static final String TAG = "SelectGroupDialogFragment";
	private String[] groupsList = {"Zajèki", "Medvedki", "Srnice"};
	private DataHolder dataHolder = new DataHolder();
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.select_group_title)
		.setMultiChoiceItems(groupsList, null, new DialogInterface.OnMultiChoiceClickListener() {		
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				String thisGroup = groupsList[which];
				if (isChecked) {
					dataHolder.getGroups().add(thisGroup);
					//Log.i(TAG, "izbran: "+groupsList[which]);
				} else if (dataHolder.getGroups().contains(thisGroup)) {
					dataHolder.getGroups().remove(thisGroup);
				};
				Log.i(TAG, "izbrani: "+dataHolder.getGroups());
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
				dataHolder.getGroups().clear();
				dismiss();
			}
		});
		return builder.create();
	}
	
	private void sendPost() {
		Toast.makeText(getActivity(), "Post sent", Toast.LENGTH_SHORT).show();
		getActivity().finish();
	}
}
