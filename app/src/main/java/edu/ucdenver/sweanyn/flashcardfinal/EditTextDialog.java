package edu.ucdenver.sweanyn.flashcardfinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.DialogFragment;

import edu.ucdenver.sweanyn.flashcardfinal.databinding.DialogAddBookBinding;
import edu.ucdenver.sweanyn.flashcardfinal.databinding.DialogEditTextBinding;


public class EditTextDialog extends DialogFragment {

    private DialogEditTextBinding binding;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogEditTextBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        return builder.create();
    }
}
