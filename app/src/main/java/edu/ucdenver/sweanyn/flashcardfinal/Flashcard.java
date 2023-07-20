package edu.ucdenver.sweanyn.flashcardfinal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "flashcards",
        foreignKeys = @ForeignKey(entity = Book.class,
                parentColumns = "id",
                childColumns = "book_id",
                onDelete = ForeignKey.CASCADE))
public class Flashcard {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "answer")
    private String answer;

    @ColumnInfo(name = "book_id")
    private long bookId;

    // Getters
    public long getId() { return id; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public long getBookId() { return bookId; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setQuestion(String question) { this.question = question; }
    public void setAnswer(String answer) { this.answer = answer; }
    public void setBookId(long bookId) { this.bookId = bookId; }
}
