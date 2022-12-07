package com.salapp.androidmidtermtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.salapp.androidmidtermtwo.db.ContactDB;
import com.salapp.androidmidtermtwo.model.Contact;

public class MainActivity extends AppCompatActivity {

    private EditText nameTxt;
    private EditText phoneTxt;
    private EditText emailTxt;
    private EditText addressTxt;

    private Button addButton;
    private Button listButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTxt = findViewById(R.id.editContactNameTxt);
        phoneTxt = findViewById(R.id.editTextPhone);
        emailTxt = findViewById(R.id.editTextTextEmailAddress);
        addressTxt = findViewById(R.id.editContactAddressTxt);

        addButton = findViewById(R.id.addBtn);
        listButton = findViewById(R.id.allContactBtn);

        addButton.setOnClickListener(this::addContact);
        listButton.setOnClickListener(this::listContact);
    }

    public void addContact(View view) {

        if (validateEmptyField()) {
            ContactDB contactDB = new ContactDB(getApplicationContext());
            Contact contact = new Contact();
            contact.setName(nameTxt.getText().toString());
            contact.setPhoneNumber(phoneTxt.getText().toString());
            contact.setEmail(emailTxt.getText().toString());
            contact.setAddress(addressTxt.getText().toString());

            contactDB.addContact(contact);
            this.clearField();
            Toast.makeText(this, "Contact has been added", Toast.LENGTH_SHORT).show();
        }
    }

    public void listContact(View view) {
        Intent contactListActivity = new Intent(this, ContactListActivity.class);
        startActivity(contactListActivity);
    }

    private void clearField() {
        nameTxt.setText("");
        phoneTxt.setText("");
        emailTxt.setText("");
        addressTxt.setText("");
    }

    public boolean validateEmptyField() {
        if (nameTxt.getText().toString().equals("")
                || phoneTxt.getText().toString().equals("")
                || emailTxt.getText().toString().equals("")
                || addressTxt.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "There are some empty field", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


}