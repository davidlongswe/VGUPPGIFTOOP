package Model.subscenes;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class SubmitScoreSubscene extends SubScene {

    private boolean isHidden;
    int playerScore;
    String playerName;
    public static final String BACKGROUND_IMAGE = "model/resources/blue_panel.png";

    public SubmitScoreSubscene() {
        super(new AnchorPane(), 600, 400);
        prefWidth(600);
        prefHeight(400);
        BackgroundImage subSceneBg = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 400, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(subSceneBg));
        isHidden = true;
        setLayoutX(84);
        setLayoutY(1024);
    }

    public void moveSubScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);
        if(isHidden){
            transition.setToY(-712);
            isHidden = false;
        }else{
            transition.setToY(1024);
            isHidden = true;
        }
        transition.play();
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }
}
