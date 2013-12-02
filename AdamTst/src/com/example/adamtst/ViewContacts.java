package com.example.adamtst;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class ViewContacts extends FragmentActivity implements
		ActionBar.TabListener {
	// Tabs vars
	private String[] tabs = { "Contact", "Diagnosis" };
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	// Activity vars
	private DBHelper dbhelper = new DBHelper(this);
	public String contactId;

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_contact);

		// set Intent Data
		contactId = getIntent().getStringExtra("contactId");

		// enable the home button
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		initTabs(actionBar);

		/**
		 * on swiping the view pager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	private void initTabs(ActionBar actionBar) {

		viewPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) { 
		getMenuInflater().inflate(R.menu.view_contacts, menu); 
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
		switch (item.getItemId()) {
		case R.id.action_edit:
	
			if(viewPager.getCurrentItem() == 0){
				Intent intent = new Intent(ViewContacts.this,EditContactInfoActivity.class);
				intent.putExtra("contactId", contactId);
				startActivity(intent);						
			} else {
				Intent intent = new Intent(ViewContacts.this,EditDiagnosis.class);
				intent.putExtra("contactId", contactId);
				startActivity(intent);
			}
			return true;
		case R.id.action_delete:
			deleteContact(contactId);
			finish(); 
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void deleteContact(String contactId) {
		dbhelper.deleteContact(contactId);
	}

	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

}
