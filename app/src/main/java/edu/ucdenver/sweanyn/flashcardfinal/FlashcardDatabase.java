package edu.ucdenver.sweanyn.flashcardfinal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
//import androidx.room.vo.Database;

@Database(entities = {Flashcard.class, Book.class}, version = 1)
public abstract class FlashcardDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "flashcard.db";
    private static FlashcardDatabase flashcardDatabase;

    public static FlashcardDatabase getInstance(Context context) {
        if (flashcardDatabase == null) {
            flashcardDatabase = Room.databaseBuilder(context, FlashcardDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return flashcardDatabase;
    }

    public abstract FlashcardDao flashcardDao();
    public abstract BookDao bookDao();  // The DAO for Book entity
}


