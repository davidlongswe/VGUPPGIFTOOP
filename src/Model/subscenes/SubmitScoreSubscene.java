package Model.subscenes;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SubmitScoreSubscene extends SubScene {

    private boolean isHidden;

    public SubmitScoreSubscene() {
        super(new AnchorPane(), 300, 200);
        prefWidth(300);
        prefHeight(200);
        AnchorPane root = (AnchorPane) this.getRoot();
        root.setStyle("-fx-background-color: #124ba1;");
        isHidden = true;
        setLayoutX(200);
        setLayoutY(200);
    }

    public void moveSubScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);
        if(isHidden){
            transition.setToY(-512);
            isHidden = false;
        }else{
            transition.setToY(1024);
            isHidden = true;
        }
        transition.play();
    }
}
