package Model.labels;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TextLabel extends Label {

    private final static String FONT_PATH = "src/Model/resources/kenvector_future.ttf";

    public TextLabel(String text, int fontSize){
        setPrefWidth(550);
        setPrefHeight(60);
        setPadding(new Insets(10,10,10,10));
        setLabelFont(fontSize);
        setText(text);
        setTextFill(Color.WHITE);
    }

    private void setLabelFont(int fontsize){
        try{
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), fontsize));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", fontsize));
        }
    }
}
