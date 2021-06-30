package com.example.contactroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactroom.R;
import com.example.contactroom.model.Contact;

import java.util.List;
import java.util.Objects;


//This is our Recycler View Class
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Contact> contactList;
    private final Context context;

    //Constructor for list of contact and context
    public RecyclerViewAdapter(List<Contact> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    //Initialising view with recycler contact view.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext())
                .inflate(R.layout.contact_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    //Setting text for name and contact.
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Contact contact = Objects.requireNonNull(contactList.get(position));
        holder.name_txt.setText(contact.getName());
        holder.occupation_txt.setText(contact.getOccupation());

    }

    @Override
    //returning total item count,
    public int getItemCount() {
        return contactList.size();
    }


    //Connecting with our view,
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name_txt, occupation_txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.row_name_text_view);
            occupation_txt = itemView.findViewById(R.id.row_occupation_text_view);


        }
    }
}
