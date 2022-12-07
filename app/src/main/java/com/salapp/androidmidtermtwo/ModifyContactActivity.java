package com.salapp.androidmidtermtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.salapp.androidmidtermtwo.db.ContactDB;
import com.salapp.androidmidtermtwo.model.Contact;

public class ModifyContactActivity extends AppCompatActivity {

    private Contact contact;
    private EditText editTextName;
    private EditText editTextPhone;

    private EditText editTextEmail;

    private EditText editTextAddress;
    private Button editButton;
    private Button deleteButton;
    private int contactId;

    private String name;
    private String phone;
    private String email;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_contact);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextName = findViewById(R.id.editNameTxt);
        editTextPhone = findViewById(R.id.editPhoneTxt);
        editTextEmail = findViewById(R.id.editEmailTxt);
        editTextAddress = findViewById(R.id.editAddressTxt);
        editButton = findViewById(R.id.editBtn);
        deleteButton = findViewById(R.id.deleteBtn);


        Intent contactIntent = getIntent();
        contactId = contactIntent.getIntExtra("id", 0);
        name = contactIntent.getStringExtra("name");
        phone = contactIntent.getStringExtra("phone");
        email = contactIntent.getStringExtra("email");
        address = contactIntent.getStringExtra("address");

        editTextName.setText(name);
        editTextPhone.setText(phone);
        editTextEmail.setText(email);
        editTextAddress.setText(address);

        editButton.setOnClickListener(this::updateContactAction);
        deleteButton.setOnClickListener(this::deleteContactAction);
    }

    public void updateContactAction(View view) {
        Snackbar.make(view, "Update contact", Snackbar.LENGTH_LONG)
                .setAction("Confirm", listener -> {

                    updateContact();
                }).show();
    }

    private void updateContact() {
        ContactDB contactDB = new ContactDB(getApplicationContext());

        contact = new Contact();
        contact.setId(contactId);
        contact.setName(editTextName.getText().toString().equals("") ? name : editTextName.getText().toString());
        contact.setPhoneNumber(editTextPhone.getText().toString().equals("") ? phone : editTextPhone.getText().toString());
        contact.setEmail(editTextEmail.getText().toString().equals("") ? email : editTextEmail.getText().toString());
        contact.setAddress(editTextAddress.getText().toString().equals("") ? address : editTextAddress.getText().toString());

        contactDB.updateContact(contact);
        Intent result = new Intent();
        result.putExtra("result", "OK");
        setResult(RESULT_OK, result);
        Toast.makeText(this, "Contact has been updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void deleteContactAction(View view) {
        Snackbar.make(view, "Delete contact", Snackbar.LENGTH_LONG)
                .setAction("Confirm", listener -> {
                    deleteContact(contactId);
                }).show();
    }

    private void deleteContact(int id) {
        ContactDB contactDB = new ContactDB(getApplicationContext());
        contactDB.deleteContact(id);
        Intent result = new Intent();
        result.putExtra("result", "OK");
        setResult(RESULT_OK, result);
        Toast.makeText(this, "Contact has been deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}