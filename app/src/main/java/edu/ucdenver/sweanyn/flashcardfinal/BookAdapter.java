package edu.ucdenver.sweanyn.flashcardfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> flashcardBooks;
    private BookDao bookDao;

    public void addBook(Book book) {
        this.flashcardBooks.add(book);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView flashcardBookName;
        public ImageView editIcon;

        public ViewHolder(View view) {
            super(view);
            flashcardBookName = view.findViewById(R.id.flashcard_book_name);
            editIcon = view.findViewById(R.id.edit_icon);
        }

        public void editBookName(Book book) {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            final EditText input = new EditText(itemView.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setTextColor(Color.DKGRAY);
            builder.setView(input);

            builder.setTitle("Edit Book Name")
                    .setMessage("Enter the new name for the book.")
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String newBookName = input.getText().toString();
                            if (!TextUtils.isEmpty(newBookName)) {
                                // Update the book name and save it to the database
                                book.setBookName(newBookName);
                                bookDao.update(book);
                                // Update the book name on the screen
                                flashcardBookName.setText(newBookName);
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            AlertDialog dialog = builder.show();

            // Change the button color
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            positiveButton.setTextColor(Color.DKGRAY);
            negativeButton.setTextColor(Color.DKGRAY);
        }
    }

    public BookAdapter(List<Book> flashcardBooks, BookDao bookDao) {
        this.flashcardBooks = flashcardBooks;
        this.bookDao = bookDao;
    }

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book flashcardBook = flashcardBooks.get(position);

        holder.flashcardBookName.setText(flashcardBook.getBookName());
        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle edit icon click here
                holder.editBookName(flashcardBook);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BookActivity.class);
                intent.putExtra("book_id", flashcardBook.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flashcardBooks.size();
    }
}

