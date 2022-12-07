package com.salapp.androidmidtermtwo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.salapp.androidmidtermtwo.adapter.ContactAdapter;
import com.salapp.androidmidtermtwo.db.ContactDB;
import com.salapp.androidmidtermtwo.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView contactRecyclerView;
    private List<Contact> contactList = new ArrayList<>();

    private SearchView searchView;

    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        searchView = findViewById(R.id.searchContact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        contactRecyclerView = findViewById(R.id.contactListRecycler);
        contactAdapter = new ContactAdapter(getAllContact(), this);
        contactRecyclerView.setAdapter(contactAdapter);

        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView.setOnQueryTextListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                contactAdapter = new ContactAdapter(getAllContact(), this);
                contactRecyclerView.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();
            }
        }
    }

    public List<Contact> getAllContact() {
        ContactDB contactDB = new ContactDB(this);
        contactList = contactDB.getAllContacts();
        return contactList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        contactAdapter.filter(newText);
        return false;
    }
}