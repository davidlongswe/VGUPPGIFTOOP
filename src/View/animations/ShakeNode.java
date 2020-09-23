package View.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ShakeNode {

    private TranslateTransition translateTransition;

    public ShakeNode(Node node){
        translateTransition = new TranslateTransition(Duration.millis(50), node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(10f);
        translateTransition.setCycleCount(6);
        translateTransition.setAutoReverse(true);
    }

    public void shake(){
        translateTransition.playFromStart();
    }

}
