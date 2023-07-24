package edu.ucdenver.sweanyn.flashcardfinal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM books")
    List<Book> getAll();

    @Query("SELECT * FROM books WHERE id = :bookId")
    Book getById(long bookId);

    @Query("SELECT * FROM books WHERE book_name = :bookName LIMIT 1")
    Book findByName(String bookName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Book book);

    @Delete
    void delete(Book book);

    @Update
    void update(Book book);
}
