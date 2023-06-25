package edu.ucdenver.sweanyn.flashcardfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlashcardBookAdapter extends RecyclerView.Adapter<FlashcardBookAdapter.ViewHolder> {
    private List<FlashcardBook> flashcardBooks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView flashcardBookName;
        public ImageView editIcon;

        public ViewHolder(View view) {
            super(view);
            flashcardBookName = view.findViewById(R.id.flashcard_book_name);
            editIcon = view.findViewById(R.id.edit_icon);
        }
    }


    public FlashcardBookAdapter(List<FlashcardBook> flashcardBooks) {
        this.flashcardBooks = flashcardBooks;
    }

    @Override
    public FlashcardBookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FlashcardBook flashcardBook = flashcardBooks.get(position);

        // Here, you would set the actual image and text
        holder.flashcardBookName.setText(flashcardBook.getName());
        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle edit icon click here
            }
        });
    }

    @Override
    public int getItemCount() {
        return flashcardBooks.size();
    }
}
