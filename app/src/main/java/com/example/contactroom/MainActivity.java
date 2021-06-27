package com.example.contactroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.contactroom.model.Contact;
import com.example.contactroom.model.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private FloatingActionButton floatingActionButton;
    private ContactViewModel viewModel;
    private static final int REQUEST_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        floatingActionButton = findViewById(R.id.floatingActionButton);


        viewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(ContactViewModel.class);

        viewModel.getAllContact().observe(this, new Observer<List<Contact>>() {

            StringBuilder builder = new StringBuilder();
            @Override
            public void onChanged(List<Contact> contacts) {
               for(Contact contact : contacts){
                   builder.append("ID : ").append(contact.getId()).append(", Name : ").append(contact.getName()).append(", Occupation : ").append(contact.getOccupation()).append("\n");
                   Log.d("TAG", "onChanged: "+contact.getName());
               }

               textView.setText(builder);


            }
        });

        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,NewContact.class);
            startActivityForResult(intent,REQUEST_CODE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK){
               String name =  data.getStringExtra(NewContact.NAME);
               String occupation = data.getStringExtra(NewContact.OCCUPATION);
               Contact contact = new Contact(name,occupation);
               ContactViewModel.insert(contact);
            }

        }
    }
}