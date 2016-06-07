package com.mieicfeup.df.footpoly.model;

import java.util.Random;

/**
 * Created by fabio on 30/04/2016.
 */
public class Dice
{
    private final static Random rand = new Random();
    private static Dice dice = null;

    /**
     * Private constructor, ensuring it's a Singleton
     */
    private Dice() {}

    /**
     * @return Dice singleton object
     */
    public static Dice getDice() {
        if(dice == null) {
            dice = new Dice();
        }
        return dice;
    }

    /**
     * Rolls the dice and returns a random number between 1 and 6
     * @return the number generated
     */
    public int rollDice()
    {
        return rand.nextInt(6) + 1;
    }
}
