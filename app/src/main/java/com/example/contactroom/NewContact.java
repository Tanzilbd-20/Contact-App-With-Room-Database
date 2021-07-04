package com.example.contactroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactroom.model.Contact;
import com.example.contactroom.model.ContactViewModel;
import com.google.android.material.snackbar.Snackbar;

public class NewContact extends AppCompatActivity {

    public static final String NAME = "name";
    public static final String OCCUPATION = "occupation";
    private EditText name_edit_text, occupation_edit_text;
    private Button save_button,update_button,delete_button;
    private ContactViewModel model;
    private int contactId = 0;
    private Boolean isEdited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        name_edit_text = findViewById(R.id.name_edit_text);
        occupation_edit_text = findViewById(R.id.occupation_edit_text);
        save_button = findViewById(R.id.button);
        update_button = findViewById(R.id.update);
        delete_button = findViewById(R.id.delete);


        model = new ViewModelProvider.AndroidViewModelFactory(NewContact.this.getApplication())
                .create(ContactViewModel.class);


        //Getting old data
        Bundle data = getIntent().getExtras();
        if(getIntent().hasExtra(MainActivity.CONTACT_ID)){
            contactId = getIntent().getIntExtra(MainActivity.CONTACT_ID,0);

                ContactViewModel.getSingleContact(contactId).observe(this, contact -> {
                    if(contact != null){
                        name_edit_text.setText(contact.getName());
                        occupation_edit_text.setText(contact.getOccupation());
                    }
                });
                isEdited = true;
        }





        //Saving new Contact
        save_button.setOnClickListener(view -> {

            Intent sendInfo = new Intent();
            if( ! TextUtils.isEmpty(name_edit_text.getText()) && ! TextUtils.isEmpty(occupation_edit_text.getText())) {
               String name = name_edit_text.getText().toString();
               String occupation = occupation_edit_text.getText().toString();
               sendInfo.putExtra(NAME,name);
               sendInfo.putExtra(OCCUPATION,occupation);
               setResult(RESULT_OK,sendInfo);
            }else {
                setResult(RESULT_CANCELED,sendInfo);
            }
            finish();
        });

        //Updating Contact
        update_button.setOnClickListener(view -> {

            String name = name_edit_text.getText().toString();
            String occupation = occupation_edit_text.getText().toString();
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)){
                Snackbar.make(name_edit_text,R.string.empty,Snackbar.LENGTH_SHORT).show();
            }else {
                Contact contact = new Contact();
                contact.setId(contactId);
                contact.setName(name);
                contact.setOccupation(occupation);
                ContactViewModel.update(contact);
                finish();
            }

        });

        delete_button.setOnClickListener(view -> {
            String name = name_edit_text.getText().toString();
            String occupation = occupation_edit_text.getText().toString();
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)){
                Snackbar.make(name_edit_text,R.string.empty,Snackbar.LENGTH_SHORT).show();
            }else {
                Contact contact = new Contact();
                contact.setId(contactId);
                ContactViewModel.deleteSingleContact(contact);
                finish();
            }

        });

        //Setting logic to hide button based on operation
        if(isEdited){
            save_button.setVisibility(View.GONE);
        }else {
            update_button.setVisibility(View.GONE);
            delete_button.setVisibility(View.GONE);
        }

    }
}