package com.mieicfeup.df.footpoly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by fabio on 02/05/2016.
 */
public class BuyStadiumDialog extends DialogFragment
{
    private Stadium stadium;
    private Token newOwner;
    public BuyStadiumDialog()
    {

    }

    public void setData(Stadium stadium, Token newOwner)
    {
        this.stadium = stadium;
        this.newOwner = newOwner;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message = "Would you like to buy " + stadium.getName() + "?";
        builder.setMessage(message)
                .setPositiveButton(R.string.dialogBuy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stadium.setOwner(newOwner);
                        newOwner.decBalance(stadium.getCost());
                    }
                })
                .setNegativeButton(R.string.dialogNoBuy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
