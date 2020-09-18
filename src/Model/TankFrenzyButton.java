package Model;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TankFrenzyButton extends Button {

    public static final String FONT_PATH = "src/Model/resources/kenvector_future.ttf";
    public static final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent;" +
            " -fx-background-image: url('/model/resources/blue_button00.png')";
    public static final String BUTTON_RELEASED_STYLE = "-fx-background-color: transparent;" +
            " -fx-background-image: url('/model/resources/blue_button01.png')";

    public TankFrenzyButton(String text){
        this.setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_RELEASED_STYLE);
        initializeButtonListeners();
    }

    private void setButtonFont(){
        try {
            this.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            this.setFont(Font.font("Verdana", 23));
        }
    }

    private void setButtonPressedStyle(){
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonReleasedStyle(){
        setStyle(BUTTON_RELEASED_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void initializeButtonListeners(){
        setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                setButtonPressedStyle();
            }
        });
        setOnMouseReleased(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                setButtonReleasedStyle();
            }
        });
        setOnMouseEntered(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                setEffect(new DropShadow());
            }
        });
        setOnMouseExited(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                setEffect(null);
            }
        });
    }



}
