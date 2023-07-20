package edu.ucdenver.sweanyn.flashcardfinal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "book_name")
    private String bookName;

    // Getters
    public long getId() { return id; }
    public String getBookName() { return bookName; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setBookName(String bookName) { this.bookName = bookName; }
}

