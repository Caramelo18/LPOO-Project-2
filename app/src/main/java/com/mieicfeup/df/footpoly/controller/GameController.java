package com.mieicfeup.df.footpoly.controller;

import com.mieicfeup.df.footpoly.model.Game;
import com.mieicfeup.df.footpoly.model.Player;

import java.util.ArrayList;

/**
 * Created by Diogo on 01/06/2016.
 */
public class GameController {
    int currentPlayer;
    ArrayList<PlayerController> playerList ;
    Game game;

    /**
     * Game controller constructor
     * @param game game object
     * @param players player list
     */
    public GameController(Game game, ArrayList<Player> players) {
        this.game = game;
        this.currentPlayer = 0;
        this.playerList = new ArrayList<PlayerController>();

        for (int i = 0; i < players.size(); i++) {
            playerList.set(i, new PlayerController(players.get(i)));
        }
    }
}
