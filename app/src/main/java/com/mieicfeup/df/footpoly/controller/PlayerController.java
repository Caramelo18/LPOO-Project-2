package com.mieicfeup.df.footpoly.controller;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.mieicfeup.df.footpoly.model.Player;

/**
 * Created by Diogo on 01/06/2016.
 */
public class PlayerController
{
    Player player;
    ImageView playerImage;
    ScreenInfo screenInfo;

    public PlayerController(Player player) {
        this.player = player;
        this.screenInfo = new ScreenInfo();
    }
    /**
     * @return player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @param playerImage
     */
    public void setImage(ImageView playerImage) {
        this.playerImage = playerImage;
    }

    public void increaseIndex(int inc)
    {
        Log.w("old pos", String.valueOf(this.player.getIndex()));
        Log.w("inc", String.valueOf(inc));
        this.player.incrementIndex(inc);
        if(this.player.getIndex() > 19)
            this.player.incrementIndex(-20);

        Log.w("new pos", String.valueOf(this.player.getIndex()));

        if(this.player.getIndex() >= 0 && this.player.getIndex() <= 5)
            playerImage.setY(screenInfo.convertDpToPixels(64));
        else if(this.player.getIndex() >= 10 && this.player.getIndex() <= 15)
            playerImage.setY(screenInfo.convertDpToPixels(389));
        else if(this.player.getIndex() == 6 || this.player.getIndex() == 19)
            playerImage.setY(screenInfo.convertDpToPixels(125));
        else if(this.player.getIndex() == 7 || this.player.getIndex() == 18)
            playerImage.setY(screenInfo.convertDpToPixels(190));
        else if(this.player.getIndex()== 8 || this.player.getIndex() == 17)
            playerImage.setY(screenInfo.convertDpToPixels(256));
        else if(this.player.getIndex() == 9 || this.player.getIndex() == 16)
            playerImage.setY(screenInfo.convertDpToPixels(322));

        if(this.player.getIndex() == 0 || (this.player.getIndex() >= 15 && this.player.getIndex() <= 19))
            playerImage.setX(screenInfo.convertDpToPixels(0));
        else if(this.player.getIndex() >= 5 && this.player.getIndex() <= 10)
            playerImage.setX(screenInfo.convertDpToPixels(325));
        else if(this.player.getIndex() == 1 || this.player.getIndex() == 14)
            playerImage.setX(screenInfo.convertDpToPixels(60));
        else if(this.player.getIndex() == 2 || this.player.getIndex()== 13)
            playerImage.setX(screenInfo.convertDpToPixels(127));
        else if(this.player.getIndex() == 3 || this.player.getIndex() == 12)
            playerImage.setX(screenInfo.convertDpToPixels(192));
        else if(this.player.getIndex() == 4 || this.player.getIndex() == 11)
            playerImage.setX(screenInfo.convertDpToPixels(259));

    }
}
