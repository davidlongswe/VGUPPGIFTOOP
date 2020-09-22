package Model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LevelLabel extends Label {
    private final static String FONT_PATH = "src/Model/resources/kenvector_future.ttf";

    public LevelLabel(String text){
        setPrefWidth(250);
        setPrefHeight(50);
        setPadding(new Insets(10,10,10,10));
        setLabelFont();
        setText(text);
        setTextFill(Color.WHITE);
    }

    private void setLabelFont(){
        try{
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 25));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 25));
        }
    }
}
