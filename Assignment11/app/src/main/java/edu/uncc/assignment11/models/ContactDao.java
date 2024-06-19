package edu.uncc.assignment11.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * from contacts")
    List<Contact> getAll();

    @Query("SELECT * from contacts WHERE id = :id limit 1")
    Contact getFromId(long id);

    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contacts")
    void deleteAll();

    @Update
    void update(Contact contact);
}
