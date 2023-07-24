package edu.ucdenver.sweanyn.flashcardfinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import java.util.concurrent.Executors;

import edu.ucdenver.sweanyn.flashcardfinal.databinding.DialogAddCardBinding;

public class AddCardDialog extends DialogFragment {

    // Define the DAOs here
    private FlashcardDao flashcardDao;
    private DialogAddCardBinding binding;

    //Defining the listener interface
    public interface Listener {
        void onCardAdded(Flashcard newFlashcard);
    }
    private Listener listener;
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private long bookId;  //The ID of the book to which the card will be added

    public AddCardDialog(long bookId) {
        this.bookId = bookId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogAddCardBinding.inflate(LayoutInflater.from(getContext()));

        // Get the instance of FlashcardDatabase and then get the DAOs
        FlashcardDatabase flashcardDatabase = FlashcardDatabase.getInstance(getContext());
        flashcardDao = flashcardDatabase.flashcardDao();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        binding.buttonClose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();

                    }
                }
        );

        binding.buttonClear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.textInputQuestion.setText("");
                        binding.textInputAnswer.setText("");
                    }
                }
        );

        binding.buttonSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Fetch the user's inputs
                        String question = binding.textInputQuestion.getText().toString();
                        String answer = binding.textInputAnswer.getText().toString();

                        // Running the Database query in a second thread
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                // Create a new flashcard
                                Flashcard newFlashcard = new Flashcard();
                                newFlashcard.setQuestion(question);
                                newFlashcard.setAnswer(answer);
                                newFlashcard.setBookId(bookId);
                                flashcardDao.insert(newFlashcard);

                                if (listener != null) {
                                    // This is running on a separate thread, need to run on UI thread
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            listener.onCardAdded(newFlashcard);
                                        }
                                    });
                                }
                            }
                        });

                        dismiss();
                    }
                }
        );

        return builder.create();
    }
}

