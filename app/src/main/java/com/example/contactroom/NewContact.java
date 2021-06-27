package com.example.contactroom;

import androidx.appcompat.app.AppCompatActivity;
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

public class NewContact extends AppCompatActivity {

    public static final String NAME = "name";
    public static final String OCCUPATION = "occupation";
    private EditText name_edit_text, occupation_edit_text;
    private Button save_button;
    private ContactViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        model = new ViewModelProvider.AndroidViewModelFactory(NewContact.this.getApplication())
                .create(ContactViewModel.class);

        name_edit_text = findViewById(R.id.name_edit_text);
        occupation_edit_text = findViewById(R.id.occupation_edit_text);
        save_button = findViewById(R.id.button);



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

    }
}