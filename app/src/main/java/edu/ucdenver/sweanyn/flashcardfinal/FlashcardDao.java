package edu.ucdenver.sweanyn.flashcardfinal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FlashcardDao {

    @Query("SELECT * FROM flashcards")
    List<Flashcard> getAll();

    @Query("SELECT * FROM flashcards WHERE book_id = :bookId")
    List<Flashcard> getByBookId(int bookId);

    @Insert
    void insert(Flashcard flashcard);

    @Delete
    void delete(Flashcard flashcard);

    @Update
    void update(Flashcard flashcard);
}
