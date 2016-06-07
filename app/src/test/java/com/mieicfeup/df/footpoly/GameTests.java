package com.mieicfeup.df.footpoly;

import android.util.SparseBooleanArray;

import com.mieicfeup.df.footpoly.controller.PlayerController;
import com.mieicfeup.df.footpoly.model.Dice;
import com.mieicfeup.df.footpoly.model.Game;
import com.mieicfeup.df.footpoly.model.Luck;
import com.mieicfeup.df.footpoly.model.Player;
import com.mieicfeup.df.footpoly.model.Stadium;
import com.mieicfeup.df.footpoly.model.StartingPoint;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by fabio on 06/06/2016.
 */
public class GameTests
{
    @Test
    public void testMovement() throws Exception
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        SparseBooleanArray humans = new SparseBooleanArray();
        humans.append(0, true);
        humans.append(1, true);
        Game game = new Game(names, humans);
        Dice dice = Dice.getDice();

        assertEquals(game.getPlayers().get(0).getIndex(), 0);
        game.getPlayers().get(0).incrementIndex(dice.rollDice());
        assertNotEquals(game.getPlayers().get(0).getIndex(), 0);
    }

    @Test
    public void testJail() throws Exception
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        SparseBooleanArray humans = new SparseBooleanArray();
        humans.append(0, true);
        humans.append(1, true);
        Game game = new Game(names, humans);

        Player p1 = game.getPlayers().get(0);
        assertEquals(p1.getIndex(), 0);
        assertEquals(game.getTable().getJail().atJail(p1), false);
        p1.incrementIndex(15);
        game.getTable().getPlace(p1.getIndex()).trigger(p1);
        assertEquals(game.getTable().getJail().atJail(p1), true);
    }

    @Test
    public void testPayRent() throws Exception
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        SparseBooleanArray humans = new SparseBooleanArray();
        humans.append(0, true);
        humans.append(1, true);
        Game game = new Game(names, humans);

        Player p1 = game.getPlayers().get(0);
        assertEquals(p1.getIndex(), 0);
        assertEquals(p1.getBalance(), 5000);

        Stadium s = (Stadium) game.getTable().getPlace(2);
        s.setOwner(game.getPlayers().get(1));

        p1.incrementIndex(2);
        s.trigger(p1);

        assertNotEquals(p1.getBalance(), 5000);
    }

    @Test
    public void testStartingPoint() throws Exception
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        SparseBooleanArray humans = new SparseBooleanArray();
        humans.append(0, true);
        humans.append(1, true);
        Game game = new Game(names, humans);

        Player p1 = game.getPlayers().get(0);
        assertEquals(p1.getIndex(), 0);
        assertEquals(p1.getBalance(), 5000);

        StartingPoint sp = (StartingPoint) game.getTable().getPlace(0);
        sp.trigger(p1);

        assertNotEquals(p1.getBalance(), 5000);
    }

    @Test
    public void testLuck() throws Exception
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        SparseBooleanArray humans = new SparseBooleanArray();
        humans.append(0, true);
        humans.append(1, true);
        Game game = new Game(names, humans);

        Player p1 = game.getPlayers().get(0);
        assertEquals(p1.getIndex(), 0);
        assertEquals(p1.getBalance(), 5000);

        p1.incrementIndex(7);
        assertEquals(p1.getIndex(), 7);

        Luck l = (Luck) game.getTable().getPlace(7);
        l.trigger(p1);

        assertNotEquals(p1.getBalance(), 5000);
    }

    @Test
    public void testMortgage() throws Exception
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        SparseBooleanArray humans = new SparseBooleanArray();
        humans.append(0, true);
        humans.append(1, true);
        Game game = new Game(names, humans);

        Player p1 = game.getPlayers().get(0);

        Stadium s = (Stadium) game.getTable().getPlace(4);
        s.setOwner(p1);
        assertNotEquals(s.getRent(), 0);
        s.setMortgaged(true);
        assertEquals(s.getRent(), 0);

        assertEquals((p1.getBalance() > 5000), true);

        s.setMortgaged(false);
        assertNotEquals(s.getRent(), 0);

        assertEquals(p1.getBalance(), 5000);
    }

    @Test
    public void testUpgrade()
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        SparseBooleanArray humans = new SparseBooleanArray();
        humans.append(0, true);
        humans.append(1, true);
        Game game = new Game(names, humans);

        Player p1 = game.getPlayers().get(0);

        Stadium s = (Stadium) game.getTable().getPlace(4);
        s.setOwner(p1);
        int rent = s.getRent();
        assertEquals(p1.getBalance(), 5000);
        s.upgradeStadium(2);
        assertEquals((p1.getBalance() < 5000), true);
        assertEquals((s.getRent() > rent), true);
    }

    @Test
    public void testStadiumsOwnedBy() throws Exception
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        SparseBooleanArray humans = new SparseBooleanArray();
        humans.append(0, true);
        humans.append(1, true);
        Game game = new Game(names, humans);

        Player p1 = game.getPlayers().get(0);

        Stadium s = (Stadium) game.getTable().getPlace(4);
        s.setOwner(p1);

        ArrayList<Stadium> returned = game.stadiumsOwnedBy(p1);
        ArrayList<Stadium> owned = new ArrayList<>();
        owned.add(s);

        assertEquals(returned, owned);
    }

    @Test
    public void testStadiumsNotOwnedBy() throws Exception
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        SparseBooleanArray humans = new SparseBooleanArray();
        humans.append(0, true);
        humans.append(1, true);
        Game game = new Game(names, humans);

        Player p1 = game.getPlayers().get(0);
        Player p2 = game.getPlayers().get(1);

        Stadium s = (Stadium) game.getTable().getPlace(4);
        s.setOwner(p2);

        ArrayList<Stadium> returned = game.stadiumsNotOwnedBy(p1);
        ArrayList<Stadium> notOwned = new ArrayList<>();
        notOwned.add(s);

        assertEquals(returned, notOwned);
    }

    @Test
    public void testGameEnd() throws Exception
    {
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        SparseBooleanArray humans = new SparseBooleanArray();
        humans.append(0, true);
        humans.append(1, true);
        Game game = new Game(names, humans);

        Player p1 = game.getPlayers().get(0);

        assertEquals(game.getGameEnded(), false);

        Stadium s = (Stadium) game.getTable().getPlace(4);
        s.setOwner(p1);
        s = (Stadium) game.getTable().getPlace(3);
        s.setOwner(p1);

        p1.incBalance(-5100);
        assertEquals((p1.getBalance() < 0), true);

        game.updateGameStatus();
        assertEquals(game.getGameEnded(), true);

        assertEquals(game.stadiumsOwnedBy(p1).size(), 0);

    }

    @Test
    public void testBuyStadium()  {
        Player player = new Player("Jogador", false);
        PlayerController playerController = new PlayerController(player);

        Stadium stadium = new Stadium("Estadio", "Pais", player.getBalance() - 1, 100);

        assertEquals(playerController.buyStadium(stadium), true);
        assertEquals(stadium.getOwner(), player);

        Stadium stadium2 = new Stadium("Estadio 2", "Pais", player.getBalance() + 1, 100);

        assertEquals(playerController.buyStadium(stadium2), false);
        assertNotEquals(stadium2.getOwner(), player);
    }

}
