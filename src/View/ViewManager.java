package View;

import Model.*;
import Model.entities.players.TANK;
import Model.labels.InfoLabel;
import Model.labels.TextLabel;
import Model.subscenes.TankFrenzySubscene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ViewManager {

    public static final int WIDTH = 1024 ;
    public static final int HEIGHT = 768;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    public static final int MENU_BUTTONS_START_X = 100;
    public static final int MENU_BUTTONS_START_Y = 185;
    public static final String BACKGROUND_IMAGE = "View/resources/blue_squared_flipped.png";

    private TankFrenzySubscene helpSubscene;
    private TankFrenzySubscene scoreSubscene;
    private TankFrenzySubscene tankPickerSubscene;

    private TankFrenzySubscene sceneToHide;

    private List<TankFrenzyButton> menuButtons;
    private List<TankPicker> tankList;

    private ScoreKeeper scoreKeeper;

    private TANK chosenTank;

    private ListView<String> scoreList;

    public ViewManager(){
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        scoreKeeper = new ScoreKeeper();
        createSubScenes();
        createButtons();
        setBackgroundStyle();
        createLogo();
        showSubScene(tankPickerSubscene);
    }

    private void showSubScene(TankFrenzySubscene subScene){
        if(sceneToHide != null){
            sceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createSubScenes(){
        createHelpSubscene();
        createScoresSubscene();
        createTankPickerSubscene();
    }

    private void createTankPickerSubscene() {
        tankPickerSubscene = new TankFrenzySubscene();
        mainPane.getChildren().add(tankPickerSubscene);
        InfoLabel chooseTankLabel = new InfoLabel("CHOOSE YOUR TANK");
        chooseTankLabel.setLayoutX(110);
        chooseTankLabel.setLayoutY(25);
        tankPickerSubscene.getPane().getChildren().add(chooseTankLabel);
        tankPickerSubscene.getPane().getChildren().add(createTanksToChoose());
        tankPickerSubscene.getPane().getChildren().add(createStartGameButton());
    }

    private void createHelpSubscene(){
        helpSubscene = new TankFrenzySubscene();
        mainPane.getChildren().add(helpSubscene);
        /*VBox helpElements = new VBox();
        String fsi = "Forget Space Invaders!";
        String lptf = "Let's play some Tank Frenzy!";
        String controls = "Use the arrow keys & space button \\n\\n to maneuver your ship\" +\n" +
                " and destroy the enemy tanks!";
        TextLabel textLabel = new TextLabel();


        String playerHelp = "Forget Space Invaders, \n\n\n welcome to Tank Frenzy! \n\n\n Use the arrow keys & space button \n\n to maneuver your ship" +
                " and destroy the enemy tanks!\n\n Hold your own soldier! ";
        Label helpLabel = new Label();
        helpLabel.setText(playerHelp);
        helpLabel.setPadding(new Insets(10,10,10,10));
        try {
            helpLabel.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 18));
        } catch (FileNotFoundException e) {
            helpLabel.setFont(Font.font("Verdana", 18));
        }
        helpLabel.setAlignment(Pos.CENTER);
        helpLabel.setLayoutX(10);
        helpLabel.setLayoutY(10);
        helpSubscene.getPane().getChildren().add(helpLabel);*/
    }

    private void createScoresSubscene(){
        scoreSubscene = new TankFrenzySubscene();
        mainPane.getChildren().add(scoreSubscene);
        InfoLabel highScores = new InfoLabel("HIGHSCORES");
        highScores.setLayoutX(110);
        highScores.setLayoutY(25);
        scoreList = new ListView<>();
        scoreList.setPrefWidth(380);
        scoreList.setPrefHeight(200);
        scoreList.setLayoutX(110);
        scoreList.setLayoutY(80);
        scoreSubscene.getPane().getChildren().add(highScores);
        scoreSubscene.getPane().getChildren().add(scoreList);
    }

    private HBox createTanksToChoose(){
        HBox box = new HBox();
        box.setSpacing(60);
        tankList = new ArrayList<>();
        for(TANK tank : TANK.values()){
            TankPicker tankToPick = new TankPicker(tank);
            tankList.add(tankToPick);
            box.getChildren().add(tankToPick);
            tankToPick.setOnMouseClicked(event -> {
                for(TankPicker tankInList : tankList){
                    tankInList.setTankChosen(false);
                }
                tankToPick.setTankChosen(true);
                chosenTank = tankToPick.getTank();
            });

        }
        box.setLayoutX(410 - (118*2));
        box.setLayoutY(100);
        return box;
    }

    private TankFrenzyButton createStartGameButton(){
        TankFrenzyButton startGameButton = new TankFrenzyButton("START");
        startGameButton.setLayoutX(350);
        startGameButton.setLayoutY(300);
        startGameButton.setOnAction(event -> {
            if(chosenTank != null){
                GameViewManager gameViewManager = new GameViewManager();
                gameViewManager.createNewGame(mainStage, chosenTank);
            }
        });
        return startGameButton;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(TankFrenzyButton tankFrenzyButton){
        tankFrenzyButton.setLayoutX(MENU_BUTTONS_START_X);
        tankFrenzyButton.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(tankFrenzyButton);
        mainPane.getChildren().add(tankFrenzyButton);
    }

    private void createButtons(){
        createStartButton();
        createScoresButton();
        createHelpButton();
        createExitButton();
    }

    private void createStartButton(){
        TankFrenzyButton startButton = new TankFrenzyButton("PLAY");
        addMenuButton(startButton);
        startButton.setOnAction(event -> showSubScene(tankPickerSubscene));
    }

    private void createScoresButton(){
        TankFrenzyButton scoresButton = new TankFrenzyButton("SCORES");
        addMenuButton(scoresButton);
        scoresButton.setOnAction(event -> {
            showSubScene(scoreSubscene);
            updateScoreList();
        });
    }

    private void createHelpButton(){
        TankFrenzyButton helpButton = new TankFrenzyButton("HELP");
        addMenuButton(helpButton);
        helpButton.setOnAction(event -> showSubScene(helpSubscene));
    }

    private void createExitButton(){
        TankFrenzyButton exitButton = new TankFrenzyButton("EXIT");
        addMenuButton(exitButton);
        exitButton.setOnAction(event -> mainStage.close());
    }

    private void setBackgroundStyle(){
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(BACKGROUND_IMAGE, WIDTH, HEIGHT,
                        false, true), BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(backgroundImage));
    }

    private void createLogo(){
        TextLabel textLabel = new TextLabel("Tank Frenzy", 60);
        textLabel.setTextFill(Color.WHITE);
        textLabel.setLayoutX(380);
        textLabel.setLayoutY(50);
        textLabel.setOnMouseEntered(event -> textLabel.setEffect(new DropShadow()));
        textLabel.setOnMouseExited(event -> textLabel.setEffect(null));
        mainPane.getChildren().add(textLabel);
    }

    private void updateScoreList(){
        scoreSubscene.getPane().getChildren().remove(scoreList);
        ArrayList<String> scores = new ArrayList<>();
        for(Score score : scoreKeeper.getScores()){
            scores.add(score.getName() + " " +  score.getScore());
            System.out.println(score.getName());
        }
        ObservableList<String> scoreItems = FXCollections.observableArrayList(scores);
        scoreList.setItems(scoreItems);
        scoreSubscene.getPane().getChildren().add(scoreList);
    }

    public void addScore(Score score) {
        scoreKeeper.addScore(score);
    }
}
