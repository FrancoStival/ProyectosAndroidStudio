package com.example.pt7_1_room_bbdd.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pt7_1_room_bbdd.model.Tag;
import com.example.pt7_1_room_bbdd.model.Tasca;
import com.example.pt7_1_room_bbdd.model.TascaTag;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Tasca.class, Tag.class, TascaTag.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TascaDao tascaDao();
    public abstract TagDao tagDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "tasques_db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                TagDao dao = INSTANCE.tagDao();
                dao.insertTag(new Tag("casa"));
                dao.insertTag(new Tag("feina"));
            });
        }
    };
}
