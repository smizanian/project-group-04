package Client.View.Transitions;

import Client.View.FXMLControllers.GameMenuFXMLController;
import javafx.animation.Transition;
import javafx.util.Duration;

public class GameMenuUserChoosingTransition extends Transition {

    private GameMenuFXMLController gameMenuFXMLController;

    public GameMenuUserChoosingTransition(GameMenuFXMLController gameMenuFXMLController) {
        this.gameMenuFXMLController = gameMenuFXMLController;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
        TransitionDatabase.transitions.add(this);
    }
    @Override
    protected void interpolate(double v) {
        gameMenuFXMLController.handleChoiceBox();
        gameMenuFXMLController.getOKButton().setDisable(!gameMenuFXMLController.isOKValid());
    }
}
