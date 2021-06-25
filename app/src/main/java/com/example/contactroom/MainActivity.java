package com.example.contactroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.contactroom.data.ContactRepository;
import com.example.contactroom.model.Contact;
import com.example.contactroom.model.ContactViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

  private ContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);


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
               textView.setText(builder.toString());


            }
        });

    }
}