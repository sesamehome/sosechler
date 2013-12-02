package com.example.adamtst;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ContactInfoFragment extends Fragment {
	
	private TextView lblPhone;
	private View viewPhone;
	
	private TextView mobilePhone;
	private TextView lblMobile;
	private View viewMobile;
	
	private TextView homePhone;
	private TextView lblHome;
	private View viewHomePhone;
	
	private TextView ofcPhone;
	private TextView lblOfc;
	
	private TextView lblEmail;
	private View viewEmail;
	private TextView email;
	private View viewEmail2;
	
	private TextView lblAddress;
	private View viewAddress;
	private TextView address; 
	
	private String valName;
	private String valHomePhone;
	private String valMobilePhone;
	private String valOfcPhone;
	private String valEmail;
	private String valAddress;
	
	private Cursor cursor;
	private DBHelper dbhelper;		
	
	private int hide = View.INVISIBLE;
	private int gone = View.GONE;
	

	
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 
	        View rootView = inflater.inflate(R.layout.fragment_contact_info, container, false);
	        
	        assignViewObjects(rootView);
	        assignValues(getActivity().getIntent().getStringExtra("contactId"));
	        
			ActionBar actionBar = getActivity().getActionBar();
		    actionBar.setTitle(valName); 
		    
		    validatePhoneDetails();
		    validateEmail();
		    validateAddress();
            
			homePhone.setText(valHomePhone);
			mobilePhone.setText(valMobilePhone);
  			ofcPhone.setText(valOfcPhone);
			email.setText(valEmail);
			address.setText(valAddress);
			
			//handle long press for phone
			handleLongPress(mobilePhone);
			handleLongPress(ofcPhone);
			handleLongPress(homePhone);

	        return rootView;
	    }	
	 
	    public void handleLongPress(final TextView phone){
	    	phone.setOnLongClickListener(new OnLongClickListener() {
	        @Override
	    		public boolean onLongClick(View v) {	    		 
	    		Toast.makeText(getActivity().getApplicationContext(),"You are now calling the number pressed :"  + phone.getText().toString(), 2000).show();
	    		Intent callIntent = new Intent(Intent.ACTION_CALL);
	    		callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
	    		startActivity(callIntent);
	    		return true;
	    		}
	    	});
	    }
	 
	    private void validateEmail(){
	    	if(valEmail.isEmpty()){ 
	    		lblEmail.setVisibility(gone);
	    		viewEmail.setVisibility(gone);
	    		email.setVisibility(gone);
	    		viewEmail2.setVisibility(gone);
	    	}  
	    }
	    
	    private void validateAddress(){
	    	if(valAddress.isEmpty()){
	    		lblAddress.setVisibility(gone);
	    		viewAddress.setVisibility(gone);
	    		address.setVisibility(gone); 
	    	} 
	    }	    
	 
	    private void assignValues(String contactId){ 
	    	dbhelper = new DBHelper(getActivity().getApplicationContext());
			cursor = dbhelper.fetchContactsById(contactId);
			valName = cursor.getString(1);
			valHomePhone = formatPhone(cursor.getString(3));
			valMobilePhone = formatPhone(cursor.getString(4));
			valOfcPhone = formatPhone(cursor.getString(5));
			valEmail = cursor.getString(6);
			valAddress = cursor.getString(2);			
			cursor.close();
			dbhelper.close();
	    }
	    
	    private void assignViewObjects(View rootView){
	    	
	    	
	    	lblPhone = (TextView) rootView.findViewById(R.id.Phone);
	    	viewPhone = (View) rootView.findViewById(R.id.viewPhone);
	    	
	    	mobilePhone = (TextView) rootView.findViewById(R.id.MobilePhone);
	    	lblMobile = (TextView) rootView.findViewById(R.id.TextMobile);
	    	viewMobile = (View) rootView.findViewById(R.id.viewMobile);
	    	
	    	homePhone = (TextView) rootView.findViewById(R.id.HomePhone);
	    	lblHome = (TextView) rootView.findViewById(R.id.TextHome);
	    	viewHomePhone = (View) rootView.findViewById(R.id.viewHomePhone);
	    	
	    	ofcPhone = (TextView) rootView.findViewById(R.id.OfficePhone);
	    	lblOfc = (TextView) rootView.findViewById(R.id.TextOfc);
	    	
	    	lblEmail = (TextView) rootView.findViewById(R.id.TextEmail);
	    	viewEmail = (View) rootView.findViewById(R.id.viewEmail);
	    	email = (TextView) rootView.findViewById(R.id.Email);
	    	viewEmail2 = (View) rootView.findViewById(R.id.viewEmail2);
	    	
	    	lblAddress = (TextView) rootView.findViewById(R.id.TextAddress);
	    	viewAddress = (View) rootView.findViewById(R.id.viewAddress);
	    	address = (TextView) rootView.findViewById(R.id.Address);	    	
	    }

		private void hidePhoneDetails(){
			lblPhone.setVisibility(gone);
	    	viewPhone.setVisibility(gone);
	    	
	    	mobilePhone.setVisibility(gone);
	    	lblMobile.setVisibility(gone);
	    	viewMobile.setVisibility(gone);
	    	
	    	homePhone.setVisibility(gone);
	    	lblHome.setVisibility(gone);
	    	viewHomePhone.setVisibility(gone);
	    	
	    	ofcPhone.setVisibility(gone);
	    	lblOfc.setVisibility(gone);
			
		}
		
		
		private void validatePhoneDetails(){
		    if(valHomePhone.isEmpty() && valMobilePhone.isEmpty() && valOfcPhone.isEmpty()){
		    	hidePhoneDetails();		    	
		    } else if (valHomePhone.isEmpty()) {
		    	homePhone.setVisibility(gone);
		    	lblHome.setVisibility(gone);
		    	viewHomePhone.setVisibility(gone);
		    } else if (valOfcPhone.isEmpty()){
		    	viewHomePhone.setVisibility(gone);		    	
		    	ofcPhone.setVisibility(gone);
		    	lblOfc.setVisibility(gone);
		    } else {
		    	mobilePhone.setVisibility(gone);
		    	lblMobile.setVisibility(gone);
		    	viewMobile.setVisibility(gone);
		    }
		}
		
		private String formatPhone(String phoneNo){
			return PhoneNumberUtils.formatNumber(phoneNo); 
		}	 
	 
}
