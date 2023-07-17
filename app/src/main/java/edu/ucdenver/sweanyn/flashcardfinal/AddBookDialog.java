package edu.ucdenver.sweanyn.flashcardfinal;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import edu.ucdenver.sweanyn.flashcardfinal.databinding.DialogAddBookBinding;

public class AddBookDialog extends DialogFragment {

    private DialogAddBookBinding binding;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogAddBookBinding.inflate(LayoutInflater.from(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        binding.buttonMainMenu.setOnClickListener(
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
                        binding.textInputBook.setText("");
                        binding.textInputQuestion.setText("");
                        binding.textInputAnswer.setText("");
                    }
                }
        );

        binding.buttonSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // need to add on click for save button
                        dismiss();
                    }
                }
        );

        return builder.create();

    }
}
