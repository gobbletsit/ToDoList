package com.example.android.todolistnikola;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by gobov on 12/10/2017.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getListOfUsers();

    @Query("SELECT * FROM user WHERE userId = :userId")
    User getUserById(int userId);

    @Insert
    void insertAll(User... users);

    @Insert(onConflict = REPLACE)
    void insertSingleUser(User user);

}
