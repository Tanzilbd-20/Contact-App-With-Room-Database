package com.example.contactroom.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.contactroom.model.Contact;
import com.example.contactroom.util.ContactRoomDatabase;

import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContact;

    public ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        contactDao = db.contactDao();
        allContact = contactDao.getAllContact();

    }
    public void insert(Contact contact){
        ContactRoomDatabase.databaseWriteExecutor.execute(()->{
            contactDao.insert(contact);

        });
    }
    public void update(Contact contact){
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.updateContact(contact);
        });
    }
    public LiveData<Contact> getSingleContact(int id){
        return contactDao.getSingleContact(id);
    }

    public void delete(Contact contact){
        ContactRoomDatabase.databaseWriteExecutor.execute(()->{
            contactDao.delete(contact);
        });
    }
    public LiveData<List<Contact>> getAllData(){
        return allContact;
    }
    public void deleteAll(){
        contactDao.deleteAllContact();
    }
}
