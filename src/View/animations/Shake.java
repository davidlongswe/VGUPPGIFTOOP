package View.animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Shake {

    private boolean xyState = true;
    private final  Timeline timeline;

    public Shake(Stage stage) {
        timeline = new Timeline();
        timeline.setCycleCount(20);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(20),
                actionEvent -> {
                    if (xyState) {
                        stage.setX(stage.getX() + 10);
                        stage.setY(stage.getY() + 10);
                    } else {
                        stage.setX(stage.getX() - 10);
                        stage.setY(stage.getY() - 10);
                    }
                    xyState= !xyState;
                }));
    }

    public void shake(){
        timeline.play();
    }

}
