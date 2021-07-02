package com.example.contactroom.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactroom.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAllContact();
    @Query("SELECT * FROM contact_table WHERE contact_table.id == :id")
    LiveData<Contact> getSingleContact(int id);

    @Query("SELECT * FROM contact_table ORDER BY id ASC")
    LiveData<List<Contact>> getAllContact();

    @Update
    void updateContact(Contact contact);
}
