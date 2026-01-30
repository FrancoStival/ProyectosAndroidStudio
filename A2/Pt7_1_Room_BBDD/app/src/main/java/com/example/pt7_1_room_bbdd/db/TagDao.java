package com.example.pt7_1_room_bbdd.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pt7_1_room_bbdd.model.Tag;

import java.util.List;

@Dao
public interface TagDao {
    @Insert
    void insertTag(Tag tag);

    @Query("SELECT * FROM tags")
    List<Tag> getAllTags();

    @Query("SELECT COUNT(*) FROM tags")
    int getTagCount();
}
