package edu.uncc.assignment12;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BillDao {
    @Query("SELECT * from bills")
    List<Bill> getAll();

    @Query("SELECT * from bills WHERE id = :id limit 1")
    Bill getFromId(long id);

    @Insert
    void insert(Bill bill);

    @Delete
    void delete(Bill bill);

    @Query("DELETE FROM bills")
    void deleteAll();

    @Update
    void update(Bill bill);
}
