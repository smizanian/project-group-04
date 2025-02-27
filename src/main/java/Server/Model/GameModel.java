package Server.Model;

import Client.Database.GameDatabase;
import Client.Client;
import Server.UserDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameModel {

    public static boolean autoSave = false;
    public static boolean isGame = false;

    public void startGame(ArrayList<String> users, boolean isAdmin) throws IOException, InterruptedException {

        ArrayList<Civilization> players = new ArrayList<Civilization>();
        for (String user : users) {
            Civilization civilization = new Civilization(user, Objects.requireNonNull(UserDatabase.getUserByUsername(user)).getNickname());
            players.add(civilization);
            System.err.println(civilization.getUsername());
        }


        if (isAdmin) GameDatabase.setPlayers(players, users.get(0));
        GameDatabase.players = players;
        System.out.println("worked");

        if (isAdmin) GameDatabase.generateMap(users.size());
        //GameDatabase.getMapFromServer();
        System.out.println("haha worked");
        if (isAdmin) GameDatabase.generateRuin();
        if (!isAdmin) {
            GameDatabase.setSocket(Client.socket2,Client.dataOutputStream2,Client.dataInputStream2);
            System.out.println("miane");
            Thread.sleep(10000);
            GameDatabase.getMapFromServer();
        }
        //GameDatabase.mapTransferStarted = true;
        System.out.println("haha worked");
    }

}
