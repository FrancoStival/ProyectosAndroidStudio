package com.example.pt7_1_room_bbdd.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.pt7_1_room_bbdd.model.Tasca;
import com.example.pt7_1_room_bbdd.model.TascaTag;
import com.example.pt7_1_room_bbdd.model.TascaWithTags;

import java.util.List;

@Dao
public interface TascaDao {
    @Insert
    long insertTasca(Tasca tasca);

    @Insert
    void insertTascaTag(TascaTag tascaTag);

    @Transaction
    @Query("SELECT * FROM tasques")
    List<TascaWithTags> getAllTasquesWithTags();

    @Transaction
    @Query("SELECT * FROM tasques WHERE id IN (SELECT tascaId FROM tasca_tag WHERE tagId = :tagId)")
    List<TascaWithTags> getTasquesByTag(int tagId);
}
