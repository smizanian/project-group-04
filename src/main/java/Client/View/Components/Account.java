package Client.View.Components;

import Client.View.GraphicalBases;
import Server.User;
import Server.UserDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class Account {

    public static ArrayList<Account> accounts = new ArrayList<Account>();

    private String username;
    private String avatarURL;

    public static Account getUserAccount(User user) {
        for (Account account : accounts) {
            if(account.getUsername().equals(user.getUsername())) {
                return account;
            }
        }
        return null;
    }

    public Account(String username, String avatarURL) {
        this.username = username;
        this.avatarURL = avatarURL;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarURL() {
        return toURL(avatarURL);
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = toJson(avatarURL);
    }

    public Account(String username) {
        this.username = username;
        this.avatarURL = assignAvatar();
    }

    private String assignAvatar() {
        Random random = new Random();
        int avatarIndex = random.nextInt(GraphicalBases.NUMBER_OF_AVATARS) + 1;
        return toJson("/pics/Avatars/" + avatarIndex + ".png");
    }

    private String toJson(String avatarURL) {
        String tempAvatarURL = avatarURL.replace("/", "*");
        return tempAvatarURL.replace(":", "^");
    }

    private String toURL(String avatarURL) {
        String tempAvatarURL = avatarURL.replace("*", "/");
        return tempAvatarURL.replace("^", ":");
    }

    public static void readAccounts(String fileName) throws IOException {
        Gson gson = new Gson();
        Path userPath = Paths.get(fileName);
        if (!userPath.toFile().isFile()) {
            return;
        }
        Reader reader = Files.newBufferedReader(userPath);
        ArrayList<?> jsonStringUserDatabase = gson.fromJson(reader, ArrayList.class);
        reader.close();

        for (int i = 0; i < jsonStringUserDatabase.size(); i++) {
            Account tempAccount = gson.fromJson(jsonStringUserDatabase.get(i).toString(), Account.class);
            Account.accounts.add(tempAccount);
        }
    }

    public static void writeOneAccount(Account account) throws IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String query = "insert into account(username,avatarURL) values('%s','%s')";
            query = String.format(query, account.getUsername(), account.getAvatarURL());
            statement.execute(query);
            statement.close();
            connection.close();
        } catch (Exception e) {
            Account.writeAccounts("AccountURLs.json");
            System.err.println("Database ERROR");
        }
    }

    public static void editAccount(Account account) throws IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String query = "update account set username='%s',avatarURL='%s' where username='%s'";
            query = String.format(query, account.getUsername(), account.getAvatarURL(), account.getUsername());
            statement.execute(query);
            statement.close();
            connection.close();
        } catch (Exception e) {
            Account.writeAccounts("AccountURLs.json");
            System.err.println("Database ERROR");
        }
    }

    public static void readFromDatabase() throws IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/project-group-04?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String query = "select * from account";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                String avatarURL = resultSet.getString(2);
                Account account = new Account(username, avatarURL);
                Account.accounts.add(account);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Account.readAccounts("AccountURLs.json");
            System.err.println("Database ERROR");
        }
    }

    public static void writeAccounts(String fileName) throws IOException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        Path userPath = Paths.get(fileName);
        Writer writer = Files.newBufferedWriter(userPath);
        gsonBuilder.toJson(Account.accounts, writer);
        writer.close();
    }

    public static Image getAvatarImage(User user) {
        int index = findAvatarIndex(user.getAvatarURL()) - 1;
        if(index == -1) {
            return loadSelectedAvatar(user);
        } else {
            return loadDefaultAvatar(index);
        }
    }

    private static Image loadSelectedAvatar(User user) {
        return new Image(user.getAvatarURL());
    }

    public static int findAvatarIndex(String avatarURL) {
        String prefix = "/pics/Avatars/";
        String suffix = ".png";
        int index = 0;
        if(avatarURL.matches("\\/pics\\/Avatars\\/\\d+.png")) {
            index = Integer.parseInt(avatarURL.substring(prefix.length(), avatarURL.length() - suffix.length()));
        }
        return index;
    }

    public static Image loadDefaultAvatar(int index) {
        return GraphicalBases.AVATARS[index];
    }

}
