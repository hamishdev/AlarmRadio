package com.example.goodmorninggamers.Helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class UrlPickerFragment extends DialogFragment {

    UrlPickerFragment.ONURLPASS dataPasser;
    Context mcontext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (UrlPickerFragment.ONURLPASS) context;
        mcontext = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final EditText input = new EditText(mcontext);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mcontext);
        alertDialogBuilder.setTitle("First Dialog");
        alertDialogBuilder
                .setMessage("Click yes to Open Second Dialog!")
                .setCancelable(false)
                .setView(input)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onURLSet(input.getText().toString());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();}});

        AlertDialog alertDialog = alertDialogBuilder.create();
        return alertDialog;
    }

    public void onURLSet(String URL){
        dataPass(URL);
    }

    public void dataPass(String data){
        dataPasser.onUrlPass(data);
    }

    public interface ONURLPASS {
        public void onUrlPass(String data);
    }

}
