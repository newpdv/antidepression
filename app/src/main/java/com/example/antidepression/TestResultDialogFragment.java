package com.example.antidepression;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class TestResultDialogFragment extends DialogFragment {

    private String title;
    private String text;

    public TestResultDialogFragment(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton("OK", null)
                .create();
    }
}