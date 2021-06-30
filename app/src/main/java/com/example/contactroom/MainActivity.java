package com.example.contactroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactroom.adapter.RecyclerViewAdapter;
import com.example.contactroom.model.Contact;
import com.example.contactroom.model.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private FloatingActionButton floatingActionButton;
    private ContactViewModel viewModel;
    private static final int REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LiveData<List<Contact>> contactList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        viewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(ContactViewModel.class);

        viewModel.getAllContact().observe(this,  contacts ->  {

            //Set up adapter
            recyclerViewAdapter = new RecyclerViewAdapter(contacts,MainActivity.this);

            //Setting adapter with recycler view
            recyclerView.setAdapter(recyclerViewAdapter);
        });



        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,NewContact.class);
            startActivityForResult(intent,REQUEST_CODE);
        });

    }


    //Receiving data from addNewContact activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK){
               String name =  data.getStringExtra(NewContact.NAME);
               String occupation = data.getStringExtra(NewContact.OCCUPATION);
               Contact contact = new Contact(name,occupation);
               ContactViewModel.insert(contact);
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }
}