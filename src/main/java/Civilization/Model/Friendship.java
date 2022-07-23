package Civilization.Model;

import Server.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Friendship {
    public static ArrayList<Friendship> friendships = new ArrayList<>();

    private String firstUsername;
    private String secondUsername;
    private boolean isAccepted;
    private boolean isDenied;

    private Friendship(String firstUsername, String secondUsername) {
        this.firstUsername = firstUsername;
        this.secondUsername = secondUsername;
        this.isAccepted = false;
        this.isDenied = false;
        friendships.add(this);
    }

    public String getFirstUsername() {
        return firstUsername;
    }

    public String getSecondUsername() {
        return secondUsername;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public boolean isDenied() {
        return isDenied;
    }

    public static void addFriendship(String firstUsername, String secondUsername) {
        Friendship friendship = Friendship.getFriendshipByUsernames(firstUsername, secondUsername);
        if(friendship == null) {
            new Friendship(firstUsername, secondUsername);
        }
    }

    public static ArrayList<String> getRequests(String myUsername) {
        ArrayList<String> myRequests = new ArrayList<>();
        for (Friendship friendship : friendships) {
            if(!friendship.isDenied() && !friendship.isAccepted()) {
                myRequests.add(friendship.getFirstUsername());
            }
        }
        return myRequests;

    }

    public void accept() {
        this.isAccepted = true;
        this.isDenied = false;
    }

    public void deny() {
        this.isDenied = true;
        this.isAccepted = false;
    }

    public static ArrayList<String> getMyFriends(String myUsername) {
        ArrayList<String> myFriends = new ArrayList<>();
        for (Friendship friendship : friendships) {
            if(friendship.getFirstUsername().equals(myUsername) || friendship.secondUsername.equals(myUsername)) {
                if(friendship.isAccepted() && !friendship.isDenied()) {
                    if(friendship.getFirstUsername().equals(myUsername)) {
                        myFriends.add(friendship.getSecondUsername());
                    } else {
                        myFriends.add(friendship.getFirstUsername());
                    }
                }
            }
        }
        return myFriends;
    }

    public static Friendship getFriendshipByUsernames(String firstUsername, String secondUsername) {
        for (Friendship friendship : friendships) {
            if(friendship.getFirstUsername().equals(firstUsername) && friendship.getSecondUsername().equals(secondUsername)) {
                return friendship;
            } else if(friendship.getFirstUsername().equals(secondUsername) && friendship.getSecondUsername().equals(firstUsername)) {
                return friendship;
            }
        }
        return null;
    }

    public static void readFriendships(String fileName) throws IOException {
        Gson gson = new Gson();
        Path userPath = Paths.get(fileName);
        if (!userPath.toFile().isFile()) {
            return;
        }
        Reader reader = Files.newBufferedReader(userPath);
        ArrayList<?> jsonStringUserDatabase = gson.fromJson(reader, ArrayList.class);
        reader.close();

        for (int i = 0; i < jsonStringUserDatabase.size(); i++) {
            Friendship friendship = gson.fromJson(jsonStringUserDatabase.get(i).toString(), Friendship.class);
            Friendship.friendships.add(friendship);
        }
    }

    public static void writeFriendships(String fileName) throws IOException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        Path userPath = Paths.get(fileName);
        Writer writer = Files.newBufferedWriter(userPath);
        gsonBuilder.toJson(Friendship.friendships, writer);
        writer.close();
    }
}
