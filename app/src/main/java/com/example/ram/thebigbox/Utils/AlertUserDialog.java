package com.example.ram.thebigbox.Utils;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.priya.thebigbox.R;

import java.util.Objects;

/*
 * Created by SaiShreeRam on 07/02/2018.
 */

public class AlertUserDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Oops...");
        builder.setMessage(getString(R.string.error_dialog));
        builder.setPositiveButton("OK", null);
        return builder.create();
    }

}
