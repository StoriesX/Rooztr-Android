package com.rooztr.rooztr_android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.rooztr.R;
import com.rooztr.model.Contact;

/**
 * Created by anandsuresh on 7/18/16.
 */


public class ContactFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public ArrayList<Contact> contacts = new ArrayList<>();
    //HashMap<String,String> names = new HashMap<>();
    //HashMap<String,Set<String>> phoneNumbers = new HashMap<>();
    //HashMap<String,Contact> contactHashMap = new HashMap<>();
    Intent intent;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        Contact o = (Contact) contacts.get(position);
        //Contact o = (Contact) parent.getAdapter().getItem(position);
        //String str = (String) o;
        String contactId = o.getId();
        intent = new Intent(getActivity(), ContactDetailsActivity.class);
        intent.putExtra("data", contactId);
        startActivity(intent);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_item,container,false);
        MainActivity mainActivity = (MainActivity) getActivity();
        //contactHashMap = mainActivity.contactMap;
        //names = mainActivity.namesMap;
        //phoneNumbers = mainActivity.phoneNumbersMap;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        for(Map.Entry<String,Contact> entry : MainActivity.contactMap.entrySet()) {
            contacts.add(entry.getValue());
        }
        ArrayAdapter<Contact> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,contacts);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }




}