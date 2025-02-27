package Server.Controller;

import Server.UserDatabase;
import Server.Model.ProfileMenuModel;
import Server.User;
import Server.UserController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuController {

    private ProfileMenuModel profileMenuModel;
    private UserController userController;

    public ProfileMenuController(ProfileMenuModel profileMenuModel) {
        this.profileMenuModel = profileMenuModel;
        this.userController = new UserController();
    }

    public boolean isPasswordValid(String newPassword) {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(newPassword);
        return matcher.matches();
    }

    public boolean isNicknameValid(String nickname) {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(nickname);
        return matcher.matches();
    }

    public boolean isPasswordCorrect(String username, String password) {
        return this.userController.isPasswordCorrect(username, password);
    }

    public boolean isNicknameUnique(String nickname) {
        User user = UserDatabase.getUserByNickname(nickname);
        if (user == null) {
            return true;
        }
        return false;
    }

    /**
     * @param username
     * @return true if username was unique
     */
    public boolean isUsernameUnique(String username) {
        User user = UserDatabase.getUserByUsername(username);
        if (user == null) {
            return true;
        }
        return false;
    }

    public boolean isNewPasswordDifferent(String password, String newPassword) {
        if (password.equals(newPassword)) {
            return false;
        }
        return true;
    }

    /**
     * @param loggedInUser
     * @param username
     */
    public void changeUsername(User loggedInUser, String username) {
        this.profileMenuModel.changeUsername(loggedInUser, username);
    }

    /**
     * @param username
     * @param nickname
     */
    public void changeNickname(String username, String nickname) {
        //System.out.println("in game menu controller");
        this.profileMenuModel.changeNickname(UserDatabase.getUserByUsername(username), nickname);
    }

    /**
     * @param loggedInUser
     * @param password
     */
    public void changePassword(User loggedInUser, String password) {
        this.profileMenuModel.changePassword(loggedInUser, password);
    }

    /**
     * corrects commands
     *
     * @param command
     * @return
     */
    public String commandCorrector(String command) {
        if (command.startsWith("profile change") && isChangeRequestPassword(command)) {
            return correctChangePassword(command);
        }
        return command;
    }

    /**
     * @param command
     * @return true if the request was profile change password
     */
    private boolean isChangeRequestPassword(String command) {
        String[] splitCommand = command.split(" ");
        for (int i = 0; i < splitCommand.length; i++) {
            if (splitCommand[i].equals("--password") || splitCommand[i].equals("-p")) {
                return true;
            }
        }
        return false;
    }

    /**
     * corrects change password commands
     *
     * @param command
     * @return
     */
    private String correctChangePassword(String command) {
        String[] splitCommand = command.split(" ");
        String correctCommand = "profile change ";

        // search for --password or -p
        for (int i = 0; i < splitCommand.length; i++) {
            if (splitCommand[i].equals("--password") || splitCommand[i].equals("-p")) {
                correctCommand += splitCommand[i] + " ";
                break;
            }
        }

        // search for --current or -c
        for (int i = 0; i < splitCommand.length; i++) {
            if (splitCommand[i].equals("--current") || splitCommand[i].equals("-c")) {
                if (i == splitCommand.length - 1) {
                    return command;
                }
                if (splitCommand[i + 1].startsWith("-")) {
                    return command;
                }
                correctCommand += splitCommand[i] + " " + splitCommand[i + 1] + " ";
                break;
            }
        }

        // search for --new or -n
        for (int i = 0; i < splitCommand.length; i++) {
            if (splitCommand[i].equals("--new") || splitCommand[i].equals("-n")) {
                if (i == splitCommand.length - 1) {
                    return command;
                }
                if (splitCommand[i + 1].startsWith("-")) {
                    return command;
                }
                correctCommand += splitCommand[i] + " " + splitCommand[i + 1];
                break;
            }
        }
        return correctCommand;
    }


}
