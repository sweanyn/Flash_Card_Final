package edu.ucdenver.sweanyn.flashcardfinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> flashcardBooks;

    public void addBook(Book book) {
        this.flashcardBooks.add(book);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView flashcardBookName;
        public ImageView editIcon;

        public ViewHolder(View view) {
            super(view);
            flashcardBookName = view.findViewById(R.id.flashcard_book_name);
            editIcon = view.findViewById(R.id.edit_icon);
        }
    }

    public BookAdapter(List<Book> flashcardBooks) {
        this.flashcardBooks = flashcardBooks;
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

