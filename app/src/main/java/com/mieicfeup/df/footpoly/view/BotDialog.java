package com.mieicfeup.df.footpoly.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mieicfeup.df.footpoly.R;
import com.mieicfeup.df.footpoly.controller.PlayerController;
import com.mieicfeup.df.footpoly.model.Player;

/**
 * Created by fabio on 06/06/2016.
 */
public class BotDialog
{
    private Player player;
    private String message;
    private static Context context;

    public void setContext(Context context)
    {
        BotDialog.context = context;
    }

    public void setData(Player player, String message)
    {
        this.player = player;
        this.message = message;

    }

    public void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(player.getName());

        builder.setMessage(message);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
        }
    });

        builder.create().show();
    }
}
