package com.mieicfeup.df.footpoly.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mieicfeup.df.footpoly.controller.PlayerController;

/**
 * Created by fabio on 06/06/2016.
 */
public class LuckDialog extends DialogFragment
{
    private int amount;
    private PlayerController player;

    public void setData(int amount, PlayerController player)
    {
        this.amount = amount;
        this.player = player;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Luck");

        if(amount > 0)
            builder.setMessage("You received " + amount + " for a previous mistake in court.");
        else
            builder.setMessage("You paid " + Math.abs(amount) + " for supporters bad behaviour.");

        Dialog dialog = builder.create();

        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
        player.updateText();
    }

}
