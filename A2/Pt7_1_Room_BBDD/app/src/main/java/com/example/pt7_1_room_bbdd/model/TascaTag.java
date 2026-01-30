package com.example.pt7_1_room_bbdd.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "tasca_tag",
        primaryKeys = {"tascaId", "tagId"},
        foreignKeys = {
                @ForeignKey(entity = Tasca.class,
                        parentColumns = "id",
                        childColumns = "tascaId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Tag.class,
                        parentColumns = "id",
                        childColumns = "tagId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("tagId")})
public class TascaTag {
    public int tascaId;
    public int tagId;

    public TascaTag(int tascaId, int tagId) {
        this.tascaId = tascaId;
        this.tagId = tagId;
    }
}
