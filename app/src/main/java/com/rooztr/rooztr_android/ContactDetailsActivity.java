package com.rooztr.rooztr_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class ContactDetailsActivity extends Activity {

    public ContactDetailsActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String id = intent.getStringExtra("data");
        Log.d("into activity",id);
        Bundle bundle = new Bundle();
        bundle.putString("id",id);

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.detailscontainer,contactDetails).commit();
    }


}
