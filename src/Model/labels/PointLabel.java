package Model.labels;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PointLabel extends Label {
    private final static String FONT_PATH = "src/Model/resources/kenvector_future.ttf";

    public PointLabel(String text){
        setPrefWidth(300);
        setPrefHeight(60);
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
