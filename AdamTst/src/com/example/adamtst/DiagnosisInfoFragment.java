package com.example.adamtst;

import android.app.ActionBar;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DiagnosisInfoFragment extends Fragment {
	

	private TextView diagnosis;
	private TextView notes;
	private Cursor cursor;
	private DBHelper dbhelper;		
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.fragment_diagnosis_info, container, false);
	         
	   	 
	        dbhelper = new DBHelper(getActivity().getApplicationContext()); 
			//List view data
			cursor = dbhelper.fetchDiagnosisById(getActivity().getIntent().getStringExtra("contactId"));
			
			ActionBar actionBar = getActivity().getActionBar();
		    actionBar.setTitle(cursor.getString(1));
			
			diagnosis = (TextView)  rootView.findViewById(R.id.Diagnosis);
			diagnosis.setText(cursor.getString(2));
			
			notes = (TextView)  rootView.findViewById(R.id.Notes);
			notes.setText(cursor.getString(3));		 
	        
			cursor.close();
			
	        
	        return rootView;
	    }	
}
