package Client.View.FXMLControllers;

import Client.Client;
import Client.View.Transitions.ChatroomChoosingTransition;
import Client.View.Transitions.GameMenuUserChoosingTransition;
import Server.Model.Chat;
import Client.View.Transitions.Chatroom;
import Server.Controller.ChatroomController;
import Server.UserDatabase;
import Server.Model.GameModel;
import Server.User;
import Client.View.GraphicalBases;
import com.google.gson.JsonObject;
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChatRoomFXMLController {
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    TextField messageBox;
    @FXML
    VBox chatBox;
    @FXML
    AnchorPane chatBoxPane;
    @FXML
    ChoiceBox<String> users;
    @FXML
    AnchorPane mainAnchorPane;

    Button editOk = new Button("Ok");
    @FXML
    Button startChat = new Button("start");
    @FXML
    Button roomOK;

    boolean isChatPrivate = false;
    boolean isRoomChat = false;

    private Set<String> selectedUsers;

    ChoiceBox<String> roomUsers = new ChoiceBox<>();

    @FXML
    public void initialize(){
        selectedUsers = new HashSet<>();
        GameModel.isGame = false;
        setChoices();
        roomOK.setVisible(false);
        Chatroom.refreshTimer = refresh5Sec();
        Chatroom.refreshTimer.start();
        Chatroom.privateRefreshTimer = privateRefresh5Sec();
        Chatroom.roomRefreshTimer = roomRefresh5Sec();
        editOk.setLayoutX(595);
        editOk.setLayoutY(663);
        editOk.setVisible(false);
        editOk.setStyle("-fx-background-size: cover;\n" +
                "    -fx-border-color: #fffde9;\n" +
                "    -fx-border-width: 3; -fx-background-color: black;" +
                "-fx-text-fill: white");
        mainAnchorPane.getChildren().add(editOk);
        startChat.setVisible(false);
        setRoomChoiceBox();
    }

    public void handleChoiceBox() {
        if(roomUsers.getValue() != null) {
            selectedUsers.add(roomUsers.getValue());

        }

    }

    public void setRoomChoiceBox(){
        ChatroomChoosingTransition chatroomChoosingTransition = new ChatroomChoosingTransition(this);
        chatroomChoosingTransition.play();

        ArrayList<String> usersNames = new ArrayList<>();
        for (User user : User.users) {
            if(user.getUsername().equals(User.loggedInUser.getUsername())) {
                continue;
            }
            usersNames.add(user.getNickname());
        }
        ObservableList<String> usersInput = FXCollections.observableArrayList(usersNames);
        roomUsers.setItems(usersInput);


        roomUsers.setLayoutX(500);
        roomUsers.setLayoutY(40);
        roomUsers.setStyle(users.getStyle());
        roomUsers.setDisable(true);
        roomUsers.setVisible(false);
        mainAnchorPane.getChildren().add(roomUsers);

    }
    public AnimationTimer refresh5Sec(){
        final LongProperty lastUpdateTime = new SimpleLongProperty();
        double[] secondsSinceStart = new double[1];
        AnimationTimer ref = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (lastUpdateTime.get() > 0){
                    final double elapsedSeconds = (l - lastUpdateTime.get()) / 1_000_000_000.0;
                    secondsSinceStart[0] += elapsedSeconds;
                    if (secondsSinceStart[0] > 1){
                        try {
                            refresh();
                            secondsSinceStart[0] = 0;
                        } catch (IOException e) {
                            //e.printStackTrace();
                            System.err.println("Connection lost");
                        }
                    }
                }
                lastUpdateTime.set(l);
            }
        };
        return ref;
    }

    public AnimationTimer privateRefresh5Sec(){
        final LongProperty lastUpdateTime = new SimpleLongProperty();
        double[] secondsSinceStart = new double[1];
        AnimationTimer ref = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (lastUpdateTime.get() > 0){
                    final double elapsedSeconds = (l - lastUpdateTime.get()) / 1_000_000_000.0;
                    secondsSinceStart[0] += elapsedSeconds;
                    if (secondsSinceStart[0] > 1){
                        try {
                            privateRefresh();
                            secondsSinceStart[0] = 0;
                        } catch (IOException e) {
                            //e.printStackTrace();
                            System.err.println("Connection lost");
                        }
                    }
                }
                lastUpdateTime.set(l);
            }
        };
        return ref;
    }


    public AnimationTimer roomRefresh5Sec(){
        final LongProperty lastUpdateTime = new SimpleLongProperty();
        double[] secondsSinceStart = new double[1];
        AnimationTimer ref = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (lastUpdateTime.get() > 0){
                    final double elapsedSeconds = (l - lastUpdateTime.get()) / 1_000_000_000.0;
                    secondsSinceStart[0] += elapsedSeconds;
                    if (secondsSinceStart[0] > 1){
                        try {
                            roomRefresh();
                            secondsSinceStart[0] = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                lastUpdateTime.set(l);
            }
        };
        return ref;
    }

    public void startPrivateChat() throws IOException {
        isChatPrivate = true;
        chatBox.getChildren().clear();
        Chat.chats.clear();
        Chatroom.refreshTimer.stop();
        Chatroom.roomRefreshTimer.stop();
        Chatroom.privateRefreshTimer.start();
        ChatroomController.readChats("privateChatDatabase.json");

    }

    public void startRoomChat() throws IOException {
        isRoomChat = true;
        chatBox.getChildren().clear();
        Chat.chats.clear();
        Chatroom.refreshTimer.stop();
        Chatroom.privateRefreshTimer.stop();
        Chatroom.roomRefreshTimer.start();
        ChatroomController.readChats("roomChatDatabase.json");
    }

    public void refresh() throws IOException {
        JSONObject clientCommandJ = new JSONObject();
        clientCommandJ.put("menu type", "Chatroom");
        clientCommandJ.put("action", "refresh");
        clientCommandJ.put("name", User.loggedInUser.getNickname());
        Client.dataOutputStream1.writeUTF(clientCommandJ.toString());
        Client.dataOutputStream1.flush();
        chatBox.getChildren().clear();
        ChatroomController.readChats("chatDatabase.json");
        updateChatBox();
    }

    public void privateRefresh() throws IOException {
        JSONObject clientCommandJ = new JSONObject();
        clientCommandJ.put("menu type", "Chatroom");
        clientCommandJ.put("action", "privateRefresh");
        clientCommandJ.put("name", User.loggedInUser.getNickname());
        Client.dataOutputStream1.writeUTF(clientCommandJ.toString());
        Client.dataOutputStream1.flush();
        chatBox.getChildren().clear();
        ChatroomController.readChats("privateChatDatabase.json");
        updateChatBox();
    }

    public void roomRefresh() throws IOException {
        JSONObject clientCommandJ = new JSONObject();
        clientCommandJ.put("menu type", "Chatroom");
        clientCommandJ.put("action", "roomRefresh");
        clientCommandJ.put("name", User.loggedInUser.getNickname());
        Client.dataOutputStream1.writeUTF(clientCommandJ.toString());
        Client.dataOutputStream1.flush();
        chatBox.getChildren().clear();
        ChatroomController.readChats("roomChatDatabase.json");
        updateChatBox();
    }

    public void deleteChat(Chat chat) throws IOException {
        System.out.println("deleting");
        JSONObject clientCommandJ = new JSONObject();
        clientCommandJ.put("menu type", "Chatroom");
        clientCommandJ.put("action", "delete");
        clientCommandJ.put("message", new String(chat.getMessage(), StandardCharsets.UTF_8));
        clientCommandJ.put("name", new String(chat.getName(), StandardCharsets.UTF_8));
        clientCommandJ.put("time", new String(chat.getTime(), StandardCharsets.UTF_8));
        clientCommandJ.put("imageUrl", new String(chat.getImageUrl(), StandardCharsets.UTF_8));
        clientCommandJ.put("seen", chat.isSeen());
        clientCommandJ.put("edited", chat.isEdited());
        clientCommandJ.put("receiver", new String(chat.getReceiver(), StandardCharsets.UTF_8));

        if (chat.getRoomReceivers() != null) {
            clientCommandJ.put("room", new String(chat.getRoomReceivers().get(0), StandardCharsets.UTF_8));
        } else {
            clientCommandJ.put("room", "null");
        }
        Client.dataOutputStream1.writeUTF(clientCommandJ.toString());
        Client.dataOutputStream1.flush();

    }

    public void editChat(Chat chat) throws IOException {

        System.out.println("editing");
        JSONObject clientCommandJ = new JSONObject();
        clientCommandJ.put("menu type", "Chatroom");
        clientCommandJ.put("action", "edit");
        clientCommandJ.put("name", new String(chat.getName(), StandardCharsets.UTF_8));
        clientCommandJ.put("time", new String(chat.getTime(), StandardCharsets.UTF_8));
        clientCommandJ.put("message", new String(chat.getMessage(), StandardCharsets.UTF_8));
        clientCommandJ.put("imageUrl", new String(chat.getImageUrl(), StandardCharsets.UTF_8));
        clientCommandJ.put("seen", chat.isSeen());
        clientCommandJ.put("edited", chat.isEdited());
        clientCommandJ.put("receiver", new String(chat.getReceiver(), StandardCharsets.UTF_8));
        if (chat.getRoomReceivers() != null) {
            clientCommandJ.put("room", new String(chat.getRoomReceivers().get(0), StandardCharsets.UTF_8));
        } else {
            clientCommandJ.put("room", "null");
        }
        editOk.setVisible(true);
        messageBox.setText(new String(chat.getMessage(), StandardCharsets.UTF_8));
        editOk.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    clientCommandJ.put("newMessage",messageBox.getText());
                    Client.dataOutputStream1.writeUTF(clientCommandJ.toString());
                    Client.dataOutputStream1.flush();
                    editOk.setVisible(false);
                    messageBox.clear();
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.err.println("Connection lost");
                }

            }
        });

     }

    public void updateChatBox() throws IOException {


        for (Chat chat:Chat.chats){

            if (isChatPrivate){
                if (!(new String(chat.getReceiver(), StandardCharsets.UTF_8).equals(User.loggedInUser.getNickname())
                        || new String(chat.getName(), StandardCharsets.UTF_8).equals(User.loggedInUser.getNickname()))){
                    continue;
                }
                if (new String(chat.getReceiver(), StandardCharsets.UTF_8).equals("null")){
                    continue;
                }
            }
            if (isRoomChat){
                boolean exists = false;
                for (byte[] roomReceiver : chat.getRoomReceivers()) {
                    if (new String(roomReceiver, StandardCharsets.UTF_8).equals(User.loggedInUser.getNickname())){
                        exists = true;
                    }
                }
                if (!(exists ||  new String(chat.getName(), StandardCharsets.UTF_8).equals(User.loggedInUser.getNickname()))){
                    continue;
                }
            }

            Text name = new Text(new String(chat.getName(), StandardCharsets.UTF_8));
            Label label = new Label();
            label.setText(" " + new String(chat.getMessage(), StandardCharsets.UTF_8) + " ");
            Text time = new Text(new String(chat.getTime(), StandardCharsets.UTF_8));

            Image avatarImage = new Image(new String(chat.getImageUrl(), StandardCharsets.UTF_8));
            ImageView avatar = new ImageView(avatarImage);

            Button edit = new Button("edit");
            edit.setStyle("-fx-font-size: 15;" +
                    "    -fx-background-color: black;" +
                    "    -fx-border-color: #fffde9;" +
                    "    -fx-text-fill: white;" +
                    "    -fx-border-width: 1;" +
                    "    -fx-fill: white;" +
                    "    -fx-text-alignment: center;" +
                    "    -fx-tile-alignment: center;" +
                    "    -fx-border-radius: 2;" +
                    "-fx-start-margin: 20");

            avatar.setFitHeight(37);
            avatar.setFitWidth(37);
            HBox line;
            if (new String(chat.getName(), StandardCharsets.UTF_8).equals(User.loggedInUser.getNickname())) {
                Circle sent = new Circle(5);
                sent.setFill(Color.WHITE);
                Button trash = new Button();
                trash.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            deleteChat(chat);
                        } catch (IOException e) {
                            //e.printStackTrace();
                            System.err.println("Connection lost");
                        }
                    }
                });
                edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            editChat(chat);
                        } catch (IOException e) {
                            //e.printStackTrace();
                            System.err.println("Connection lost");
                        }
                    }
                });
                trash.setBackground(Background.fill(new ImagePattern(GraphicalBases.trash)));
                trash.setPrefWidth(40);

                if (chat.isSeen()) {
                    Circle seen = new Circle(5);
                    seen.setFill(Color.WHITE);
                    line = new HBox(avatar, name, label, time, edit,trash, sent, seen);
                } else {


                    line = new HBox(avatar, name, label, time, edit,trash, sent);
                }
            } else {
                line = new HBox(avatar, name, label, time);
            }
            time.setStyle("-fx-font-size: 10; -fx-fill: white; -fx-text-fill: white;");
            name.setStyle("-fx-fill: white; -fx-font-size: 10");
            line.setStyle("-fx-alignment: center-left; -fx-spacing: 10; -fx-padding: 10");
            label.setStyle("-fx-font-size: 15;" +
                    "    -fx-background-color: black;" +
                    "    -fx-border-color: #fffde9;" +
                    "    -fx-text-fill: white;" +
                    "    -fx-border-width: 1;" +
                    "    -fx-fill: white;" +
                    "    -fx-text-alignment: center;" +
                    "    -fx-tile-alignment: center;" +
                    "    -fx-border-radius: 2;" +
                    "-fx-start-margin: 20");

            chatBox.getChildren().add(line);
            chatBoxPane.setPrefHeight(chatBoxPane.getPrefHeight() + 50);
            chatBox.setPrefHeight(chatBox.getPrefHeight() + 50);
        }


    }

    private void setChoices() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("public", "private", "rooms");
        choiceBox.setItems(availableChoices);
        ArrayList<String> names = new ArrayList<>();
        for (User user: UserDatabase.getAllUsers()){
            names.add(user.getNickname());
        }
        ObservableList<String> availableUsers = FXCollections.observableArrayList(names);
        users.setItems(availableUsers);
    }


    public void sendMessage() throws IOException {
        if (!messageBox.getText().isBlank()) {
            JSONObject clientCommandJ = new JSONObject();
            clientCommandJ.put("menu type", "Chatroom");
            clientCommandJ.put("action", "send");
            String message = messageBox.getText();
            messageBox.clear();
            Text name = new Text(User.loggedInUser.getNickname());
            Label label = new Label();
            label.setText(" " + message + " ");
            Text time = new Text();
            time.setText(LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond());

//            try{
//                sendMessageInReal(User.loggedInUser.getNickname(), users.getValue(), message, choiceBox.getValue());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            Image avatarImage = new Image(GraphicalBases.class.getResource(User.loggedInUser.getAvatarURL()).toString());
            ImageView avatar = new ImageView(avatarImage);
            avatar.setFitHeight(37);
            avatar.setFitWidth(37);
            Button edit = new Button("edit");
            edit.setStyle("-fx-font-size: 10;" +
                    "    -fx-background-color: black;" +
                    "    -fx-border-color: #fffde9;" +
                    "    -fx-text-fill: white;" +
                    "    -fx-border-width: 1;" +
                    "    -fx-fill: white;" +
                    "    -fx-text-alignment: center;" +
                    "    -fx-tile-alignment: center;" +
                    "    -fx-border-radius: 2;" +
                    "-fx-start-margin: 20");
            Circle sent = new Circle(5);
            sent.setFill(Color.WHITE);
            Button trash = new Button();
            byte[] byteArrrayName = name.getText().getBytes(StandardCharsets.UTF_8);
            byte[] byteArrrayImage = avatarImage.getUrl().getBytes(StandardCharsets.UTF_8);
            byte[] byteArrrayTime = time.getText().getBytes(StandardCharsets.UTF_8);
            byte[] byteArrrayMessage = message.getBytes(StandardCharsets.UTF_8);
            trash.setBackground(Background.fill(new ImagePattern(GraphicalBases.trash)));
            trash.setPrefWidth(40);


            HBox line = new HBox(avatar, name, label, time, edit,trash, sent);

            if (isChatPrivate){
                clientCommandJ.put("receiver", users.getValue());
            } else  {
                clientCommandJ.put("receiver", "null");
                if (isRoomChat){
                    JSONObject roomUsers = new JSONObject();
                    int i = 1;
                    for (String selectedUser : selectedUsers) {
                        roomUsers.put(String.valueOf(i), selectedUser);
                        i++;
                    }
                    clientCommandJ.put("room", roomUsers);

                } else {
                    clientCommandJ.put("room", "null");
                }
            }

            clientCommandJ.put("message", message);
            clientCommandJ.put("name",User.loggedInUser.getNickname());
            clientCommandJ.put("time", LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond());
            clientCommandJ.put("imageUrl", GraphicalBases.class.getResource(User.loggedInUser.getAvatarURL()).toString());
            Client.dataOutputStream1.writeUTF(clientCommandJ.toString());



            name.setStyle("-fx-fill: white; -fx-font-size: 10");
            line.setStyle("-fx-alignment: center-left; -fx-spacing: 10; -fx-padding: 10");
            label.setStyle("-fx-font-size: 15;" +
                    "    -fx-background-color: black;" +
                    "    -fx-border-color: #fffde9;" +
                    "    -fx-text-fill: white;" +
                    "    -fx-border-width: 1;" +
                    "    -fx-fill: white;" +
                    "    -fx-text-alignment: center;" +
                    "    -fx-tile-alignment: center;" +
                    "    -fx-border-radius: 2;" +
                    "-fx-start-margin: 20");
            time.setStyle("-fx-font-size: 10; -fx-fill: white; -fx-text-fill: white;");
            chatBox.getChildren().add(line);
            chatBoxPane.setPrefHeight(chatBoxPane.getPrefHeight() + 50);
            chatBox.setPrefHeight(chatBox.getPrefHeight() + 50);
            //ChatroomController.readChats("chatDatabase.json");
//            trash.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    try {
//                        deleteChat(Chat.getChat(byteArrrayMessage, byteArrrayName, byteArrrayTime, byteArrrayImage, false, false));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//
//                }
//            });
        }
    }

    private void sendMessageInReal(String nickname, String value, String message, String value1) throws IOException {
        if(value1.equals("public")) {
            value = "ALL USERS";
        } else if (value1.equals("rooms")) {
            value = "ALL IN ROOM";
        }
        JSONObject input = new JSONObject();
        input.put("menu type","Chatroom");
        input.put("action","send message");
        input.put("from", nickname);
        input.put("to", value);
        input.put("text", message);
        input.put("room", value1);
        Client.dataOutputStream1.writeUTF(input.toString());
        Client.dataOutputStream1.flush();
    }

    public void backToMain() {
        Chatroom.refreshTimer.stop();
        GraphicalBases.changeMenu("MainMenu");
    }

    public void changeChatType() {
        if (choiceBox.getValue().equals("private")){
            roomOK.setVisible(false);
            users.setVisible(true);
            startChat.setVisible(true);
        } else if (choiceBox.getValue().equals("public")) {
            roomOK.setVisible(false);
            users.setVisible(false);
            startChat.setVisible(false);
            isChatPrivate = false;
            isRoomChat = false;
            Chatroom.privateRefreshTimer.stop();
            Chatroom.roomRefreshTimer.stop();
            Chatroom.refreshTimer.start();
        } else {
            users.setVisible(false);
            startChat.setVisible(false);
            roomOK.setVisible(true);
            Chatroom.privateRefreshTimer.stop();
            Chatroom.refreshTimer.stop();
            roomUsers.setDisable(false);
            roomUsers.setVisible(true);

        }
    }
}
