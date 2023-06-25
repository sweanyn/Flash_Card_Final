package edu.ucdenver.sweanyn.flashcardfinal;

import android.content.Intent;
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

        // Here, you would set the actual text
        holder.flashcardBookName.setText(flashcardBook.getName());
        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle edit icon click here
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle item click here
                // For example, you could start a new activity and pass the flashcard book as an extra:
                Intent intent = new Intent(view.getContext(), FlashcardBookActivity.class);
                intent.putExtra("flashcard_book", flashcardBook);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flashcardBooks.size();
    }
}
