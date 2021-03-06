package com.rooztr.rooztr_android;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rooztr.model.Contact;
import com.rooztr.rooztr_android.rooztr.adapter.TabsPagerAdapter;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {
    ArrayList<String> names = new ArrayList<>();

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;

    private String[] tabs = { "Contacts", "Request", "Waiting" };
    //HashMap<String,String> namesMap = new HashMap<>();
    //HashMap<String,Set<String>> phoneNumbersMap = new HashMap<>();

    public static HashMap<String,Contact> contactMap;
    public static List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContacts();
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        viewPager= (ViewPager) findViewById(R.id.pager);

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        /*
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d("selected", String.valueOf(position));
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getContacts() {
        contactMap = new HashMap<>();
        contacts = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        if(cur.getCount() > 0) {
            while(cur.moveToNext()) {
                String name  = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if(name != null) {
                    Contact contact = new Contact();
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    contact.setName(name);
                    contact.setId(id);
                    if(Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                        Cursor pcur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +"= ?",
                                new String[] {id},null
                        );
                        while (pcur.moveToNext()) {
                            String phoneNo = pcur.getString(pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contact.getNumbers().add(phoneNo);

                            Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID +"= ?",
                                    new String[] {id},null);
                            while(emailCur.moveToNext()) {
                                String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                contact.getEmail().add(email);
                            }
                        }
                        pcur.close();
                    }
                    contacts.add(contact);
                    contactMap.put(id, contact);
                }
            }
        }
        cur.close();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("Selected", String.valueOf(tab.getPosition()));
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

}
