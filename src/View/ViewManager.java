package View;

import Model.*;
import Model.entities.players.TANK;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    public static final int MENU_BUTTONS_START_X = 100;
    public static final int MENU_BUTTONS_START_Y = 150;

    private TankFrenzySubscene creditsSubScene;
    private TankFrenzySubscene helpSubscene;
    private TankFrenzySubscene scoreSubscene;
    private TankFrenzySubscene tankPickerSubscene;

    private TankFrenzySubscene sceneToHide;

    List<TankFrenzyButton> menuButtons;
    List<TankPicker> tankList;
    private TANK chosenTank;

    public ViewManager(){
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createButtons();
        createBackground();
        createLogo();
    }

    private void showSubScene(TankFrenzySubscene subScene){
        if(sceneToHide != null){
            sceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createSubScenes(){
        creditsSubScene = new TankFrenzySubscene();
        mainPane.getChildren().add(creditsSubScene);

        helpSubscene = new TankFrenzySubscene();
        mainPane.getChildren().add(helpSubscene);

        scoreSubscene = new TankFrenzySubscene();
        mainPane.getChildren().add(scoreSubscene);

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
        box.setLayoutX(300 - (118*2));
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
        createCreditsButton();
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
        scoresButton.setOnAction(event -> showSubScene(scoreSubscene));
    }

    private void createHelpButton(){
        TankFrenzyButton helpButton = new TankFrenzyButton("HELP");
        addMenuButton(helpButton);
        helpButton.setOnAction(event -> showSubScene(helpSubscene));
    }

    private void createCreditsButton(){
        TankFrenzyButton creditsButton = new TankFrenzyButton("CREDITS");
        addMenuButton(creditsButton);
        creditsButton.setOnAction(event -> showSubScene(creditsSubScene));
    }

    private void createExitButton(){
        TankFrenzyButton exitButton = new TankFrenzyButton("EXIT");
        addMenuButton(exitButton);
        exitButton.setOnAction(event -> mainStage.close());
    }

    private void createBackground(){
        Image backgroundImage = new Image("View/resources/bg.png", WIDTH,HEIGHT,false,true);
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo(){
        ImageView logo = new ImageView("View/resources/tank_logo.png");
        logo.setFitHeight(100);
        logo.setFitWidth(300);
        logo.setLayoutX(400);
        logo.setLayoutY(50);
        logo.setOnMouseEntered(event -> logo.setEffect(new DropShadow()));
        logo.setOnMouseExited(event -> logo.setEffect(null));
        mainPane.getChildren().add(logo);
    }

}
