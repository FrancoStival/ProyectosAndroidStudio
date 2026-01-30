package com.example.pt7_1_room_bbdd.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TascaWithTags {
    @Embedded
    public Tasca tasca;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(value = TascaTag.class,
                    parentColumn = "tascaId",
                    entityColumn = "tagId")
    )
    public List<Tag> tags;
}
