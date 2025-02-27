package Client;

import java.io.IOException;

import Client.Database.GameDatabase;
import Server.UserDatabase;
import Server.Model.GameModel;
import Client.View.Components.Account;
import Client.View.GraphicalBases;
import Client.Client;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        Client.setupSocketConnectionRegister();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //UserDatabase.readFromFile("UserDatabase.json");
        UserDatabase.readFromDatabase();
        //Account.readAccounts("AccountURLs.json");
        Account.readFromDatabase();

        GraphicalBases.stage = stage;
        GraphicalBases.firstLogin();

//        Scanner scanner = new Scanner(System.in);
//
//        MainMenuModel mainMenuModel = new MainMenuModel();
//        MainMenuController mainMenuController = new MainMenuController(mainMenuModel);
//        MainMenu mainMenu = new MainMenu(mainMenuController);
//
//        LoginMenu loginMenu = new LoginMenu(new LoginMenuController(new LoginMenuModel()));
//
//        while (true) {
//            User.loggedInUser = loginMenu.run(scanner);
//            if (User.loggedInUser == null) {
//                break;
//            }
//            mainMenu.run(scanner, User.loggedInUser);
//        }

        //UserDatabase.writeInFile("UserDatabase.json");
    }

    @Override
    public void stop() {
        if(GameModel.isGame) {
            GameDatabase.saveGame();
        }
        try{
            //UserDatabase.writeInFile("UserDatabase.json");
        } catch (Exception e) {

        }
    }

}
