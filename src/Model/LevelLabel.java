package Model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LevelLabel extends Label {
    private final static String FONT_PATH = "src/Model/resources/kenvector_future.ttf";
    public static final String BACKGROUND_PATH = "View/resources/blue_button13.png";

    public LevelLabel(String text){
        setPrefWidth(170);
        setPrefHeight(50);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(
                        BACKGROUND_PATH,
                        170, 50,
                        false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10,10,10,10));
        setLabelFont();
        setText(text);
    }

    private void setLabelFont(){
        try{
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 15));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 15));
        }
    }
}
