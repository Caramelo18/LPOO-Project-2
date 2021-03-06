package com.mieicfeup.df.footpoly.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.mieicfeup.df.footpoly.R;
import com.mieicfeup.df.footpoly.controller.GameController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Diogo on 07/06/2016.
 */
public class MenuDialog {
    private Context context;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private GameController gameController;

    public MenuDialog()
    {

    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void showDialog () {
        builder = new AlertDialog.Builder(context);

        final String[] optionList = new String[4];

        optionList[0] = "Resume Game";
        optionList[1] = "Instructions";
        optionList[2] = "Save Game";
        optionList[3] = "Main Menu";


        builder.setTitle("Menu")
                .setItems(optionList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:
                                dialog.dismiss();
                            case 1:
                                showInstructions();
                                break;
                            case 2:
                                saveGame();
                                break;
                            case 3:
                                backToMenu();
                                break;
                        }
                    }
                });

        dialog = builder.create();
        dialog.show();
    }

    private void showInstructions()
    {
        dialog.dismiss();
        builder = new AlertDialog.Builder(context);

        String message = context.getResources().getString(R.string.instructionsText);

        builder.setMessage(message);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }

    });
        dialog = builder.create();
        dialog.show();
    }

    private void saveGame() {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("savedGame", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameController.getGame());
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backToMenu()
    {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

}

