package edu.ucdenver.sweanyn.flashcardfinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.DialogFragment;

import edu.ucdenver.sweanyn.flashcardfinal.databinding.DialogAddCardBinding;

public class AddCardDialog extends DialogFragment {

    private DialogAddCardBinding binding;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        binding = DialogAddCardBinding.inflate(LayoutInflater.from(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
        //Need a method in MainActivity for adding card (just like onBookAdded) so that we can link menu_book to it

        return builder.create();
    }
}
