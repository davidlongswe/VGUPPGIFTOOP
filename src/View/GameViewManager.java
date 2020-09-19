package View;

import Model.Bullet;
import Model.pointLabel;
import Model.TANK;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class GameViewManager {

    private AnchorPane gamePane;
    private Stage gameStage;
    private Scene gameScene;

    public static final int GAME_WIDTH = 1024;
    public static final int GAME_HEIGHT = 768;

    private Stage menuStage;
    private ImageView tank;

    boolean shooting,
            thinShotFired,
            largeShotFired,
            isLeftKeyPressed,
            isRightKeyPressed,
            isUpKeyPressed,
            isDownKeyPressed;

    private int angle;
    private AnimationTimer gameTimer;

    private GridPane gridPane1;
    private GridPane gridPane2;
    public static final String BACKGROUND_IMAGE = "View/resources/bg.png";

    public final static String ENEMY_TANK_BLUE = "View/resources/tanks/tank_blue.png";
    public final static String ENEMY_BIG_RED = "View/resources/tanks/tank_bigRed.png";
    public final static String ENEMY_HUGE = "View/resources/tanks/tank_huge.png";
    public static final String ENEMY_GREEN = "View/resources/tanks/tank_green.png";

    private ImageView[] blueEnemyTanks;
    private ImageView[] bigRedEnemyTanks;
    private ImageView[] hugeEnemyTanks;
    private ImageView[] greenEnemyTanks;
    private ArrayList<Bullet> thinBullets;
    private ArrayList<Bullet> largeBullets;
    private Random randomNumberGenerator;

    private ImageView heart;
    private pointLabel pointsLabel;
    private ArrayList<ImageView> playerLives;
    private int livesLeft;
    private int points;
    public static final String HEART_IMAGE = "View/resources/heart.png";
    public static final String EXPLOSION = "View/resources/explosionSmoke2.png";
    public static final String BULLET_THIN = "View/resources/shotThin.png";
    public static final int TANK_RADIUS = 21;
    public static final int HEART_RADIUS = 16;
    public static final int TANK_HEIGHT = 46;
    public static final int HEART_HEIGHT = 32;
    public static final int TANK_WIDTH = 42;
    public static final int HEART_WIDTH = 32;

    public GameViewManager(){
        initializeStage();
        createKeyListeners();
        randomNumberGenerator = new Random();
    }

    private void createKeyListeners(){
        gameScene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if(keyCode == KeyCode.LEFT){
                isLeftKeyPressed = true;
            }
            if(keyCode == KeyCode.RIGHT){
                isRightKeyPressed = true;
            }
            if(keyCode == KeyCode.UP){
                isUpKeyPressed = true;
            }
            if(keyCode == KeyCode.DOWN){
                isDownKeyPressed = true;
            }
            if(keyCode == KeyCode.SPACE){
                if(!shooting){
                    addBullet();
                    System.out.println("thin bullet added, there are " + thinBullets.size() + " bullets in the list");
                    thinShotFired = true;
                    shooting = true;
                }
            }
        });
        gameScene.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.LEFT){
                isLeftKeyPressed = false;
            }
            if(event.getCode() == KeyCode.RIGHT){
                isRightKeyPressed = false;
            }
            if(event.getCode() == KeyCode.UP){
                isUpKeyPressed = false;
            }
            if(event.getCode() == KeyCode.DOWN){
                isDownKeyPressed = false;
            }
            if(event.getCode() == KeyCode.SPACE){
                thinShotFired = false;
                shooting = false;
            }
            if(event.getCode() == KeyCode.SHIFT){
                largeShotFired = false;
                shooting = true;
            }
        });
    }

    private void addBullet() {
        Bullet bullet = new Bullet(new ImageView(BULLET_THIN), tank.getLayoutX()+TANK_RADIUS, tank.getLayoutY());
        bullet.getBulletImage().setLayoutX(bullet.getPosX());
        bullet.getBulletImage().setLayoutY(bullet.getPosY());
        thinBullets.add(bullet);
        gamePane.getChildren().add(bullet.getBulletImage());
    }

    private void initializeStage(){
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Stage menuStage, TANK chosenTank){
        this.menuStage = menuStage;
        this.menuStage.hide();
        thinBullets = new ArrayList<>();
        largeBullets = new ArrayList<>();
        createBackground();
        createTank(chosenTank);
        createGameElements();
        createGameLoop();
        gameStage.show();
    }

    private void createGameElements(){
        addPlayerInfo();
        setNewElementPosition(heart);

        blueEnemyTanks = new ImageView[3];
        bigRedEnemyTanks = new ImageView[2];
        hugeEnemyTanks = new ImageView[1];
        greenEnemyTanks = new ImageView[3];

        addEnemies(blueEnemyTanks, ENEMY_TANK_BLUE);
        addEnemies(bigRedEnemyTanks, ENEMY_BIG_RED);
        addEnemies(hugeEnemyTanks, ENEMY_HUGE);
        addEnemies(greenEnemyTanks, ENEMY_GREEN);
    }

    private void addPlayerInfo(){
        livesLeft = 3;
        heart = new ImageView(HEART_IMAGE);
        setNewElementPosition(heart);
        gamePane.getChildren().add(heart);
        pointsLabel = new pointLabel("POINTS : 00");
        pointsLabel.setLayoutX(GAME_WIDTH - 160);
        pointsLabel.setLayoutY(20);
        gamePane.getChildren().add(pointsLabel);
        playerLives = new ArrayList<>();
        updateLives(livesLeft);
    }

    private void addEnemies(ImageView[] enemies, String path){
        for(int i = 0; i < enemies.length; i++){
            enemies[i] = new ImageView(path);
            setNewElementPosition(enemies[i]);
            gamePane.getChildren().add(enemies[i]);
        }
    }

    private void moveGameElements(){
        heart.setLayoutY(heart.getLayoutY() + 1);

        for (Bullet thinBullet : thinBullets) {
            thinBullet.getBulletImage().setLayoutY(thinBullet.getBulletImage().getLayoutY() - 10);
        }

        for (ImageView blueEnemyTank : blueEnemyTanks) {
            blueEnemyTank.setLayoutY(blueEnemyTank.getLayoutY() + 1);
        }
        for (ImageView bigRedEnemyTank : bigRedEnemyTanks) {
            bigRedEnemyTank.setLayoutY(bigRedEnemyTank.getLayoutY() + 1);
        }
        for (ImageView hugeEnemyTank : hugeEnemyTanks) {
            hugeEnemyTank.setLayoutY(hugeEnemyTank.getLayoutY() + 1);
        }
        for (ImageView greenEnemyTank : greenEnemyTanks) {
            greenEnemyTank.setLayoutY(greenEnemyTank.getLayoutY() + 1);
        }
    }

    private void checkIfElementsAreBehindPlayerAndRelocate(){
        if(heart.getLayoutY() > GAME_HEIGHT){
            heart.setLayoutY(0);
        }

        for (ImageView blueEnemyTank : blueEnemyTanks) {
            if(blueEnemyTank.getLayoutY() > GAME_HEIGHT){
                blueEnemyTank.setLayoutY(0);
            }
        }
        for (ImageView bigRedEnemyTank : bigRedEnemyTanks) {
            if(bigRedEnemyTank.getLayoutY() > GAME_HEIGHT){
                bigRedEnemyTank.setLayoutY(0);
            }
        }
        for (ImageView hugeEnemyTank : hugeEnemyTanks) {
            if(hugeEnemyTank.getLayoutY() > GAME_HEIGHT){
                hugeEnemyTank.setLayoutY(0);
            }
        }
        for (ImageView greenEnemyTank : greenEnemyTanks) {
            if(greenEnemyTank.getLayoutY() > GAME_HEIGHT){
                greenEnemyTank.setLayoutY(0);
            }
        }
    }

    private void setNewElementPosition(ImageView image){
        image.setLayoutX(randomNumberGenerator.nextInt(GAME_WIDTH-46));
        image.setLayoutY(-(randomNumberGenerator.nextInt(GAME_HEIGHT-46)));
    }

    private void createTank(TANK chosenTank){
        tank = new ImageView(chosenTank.getUrlTank());
        tank.setLayoutX((float)GAME_WIDTH/2);
        tank.setLayoutY(GAME_HEIGHT - 46);
        gamePane.getChildren().add(tank);
    }

    private void createGameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveBackground();
                moveGameElements();
                checkIfElementsAreBehindPlayerAndRelocate();
                checkCollisions();
                moveTank();
            }
        };
        gameTimer.start();
    }

    private void moveTank(){
        if(isLeftKeyPressed && !isRightKeyPressed){
            if(angle > -30){
                angle -= 90;
            }
            tank.setRotate(angle);
            if(tank.getLayoutX() > 0){
                tank.setLayoutX(tank.getLayoutX() - 3);
            }
        }
        if(isRightKeyPressed && !isLeftKeyPressed){
            if(angle < 30){
                angle += 90;
            }
            tank.setRotate(angle);
            if(tank.getLayoutX() < GAME_WIDTH-46){
                tank.setLayoutX(tank.getLayoutX() + 3);
            }
        }
        if(!isRightKeyPressed && !isLeftKeyPressed){
            if(angle < 0){
                angle += 5;
            }else if(angle > 0){
                angle -= 5;
            }
            tank.setRotate(angle);
        }
        if(isRightKeyPressed && isLeftKeyPressed){
            if(angle < 0){
                angle += 5;
            }else if(angle > 0){
                angle -= 5;
            }
            tank.setRotate(angle);
        }
    }

    private void createBackground(){
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();

        for(int i = 0; i < 12; i++){
            ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
            backgroundImage1.setFitWidth(GAME_WIDTH);
            backgroundImage1.preserveRatioProperty();
            ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
            backgroundImage2.setFitWidth(GAME_WIDTH);
            backgroundImage1.preserveRatioProperty();
            GridPane.setConstraints(backgroundImage1, i % 3, i / 3);
            GridPane.setConstraints(backgroundImage2, i % 3, i / 3 );
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
        }

        gridPane2.setLayoutY(-GAME_HEIGHT);
        gamePane.getChildren().addAll(gridPane1,gridPane2);
    }

    private void moveBackground(){
        gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
        gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);

        if(gridPane1.getLayoutY() >= GAME_HEIGHT){
            gridPane1.setLayoutY(-GAME_HEIGHT);
        }
        if(gridPane2.getLayoutY() >= GAME_HEIGHT){
            gridPane2.setLayoutY(-GAME_HEIGHT);
        }
    }

    private void checkCollisions(){
        checkCollisionWithHeart();
        checkCollisionWithEnemies(blueEnemyTanks);
        checkCollisionWithEnemies(greenEnemyTanks);
        checkCollisionWithEnemies(hugeEnemyTanks);
        checkCollisionWithEnemies(bigRedEnemyTanks);
    }

    private void checkCollisionWithHeart(){
        if(TANK_RADIUS+HEART_RADIUS > calculateDistance(
                tank.getLayoutX() + TANK_WIDTH, heart.getLayoutX() + HEART_WIDTH,
                tank.getLayoutY() + TANK_HEIGHT, heart.getLayoutY()+ HEART_HEIGHT)){
            if(playerLives.size() <= 3 && playerLives.size() > 0){
                livesLeft++;
                updateLives(livesLeft);
                setNewElementPosition(heart);
            }
        }
    }

    private void checkCollisionWithBullets(ImageView enemyTank) {
        for(Bullet bullet : thinBullets) {
            if (TANK_WIDTH > calculateDistance(
                    enemyTank.getLayoutX() + TANK_WIDTH, bullet.getBulletImage().getLayoutX() + 8,
                    enemyTank.getLayoutY() + TANK_HEIGHT, bullet.getBulletImage().getLayoutY() + 26)) {
                explode(enemyTank.getLayoutX(), enemyTank.getLayoutY());
                gamePane.getChildren().remove(bullet.getBulletImage());
                gamePane.getChildren().remove(enemyTank);
            }
        }
    }

    private void explode(double layoutX, double layoutY) {
        ImageView explosion = new ImageView(EXPLOSION);
        explosion.setLayoutX(layoutX);
        explosion.setLayoutY(layoutY);
        gamePane.getChildren().add(explosion);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> gamePane.getChildren().remove(explosion));
        pause.play();    
    }

    private void checkCollisionWithEnemies(ImageView[] tanks) {
        for(ImageView enemyTank : tanks){
            if(TANK_WIDTH > calculateDistance(
                    tank.getLayoutX() + TANK_WIDTH, enemyTank.getLayoutX() + TANK_WIDTH,
                    tank.getLayoutY() + TANK_HEIGHT, enemyTank.getLayoutY() + TANK_HEIGHT)){
                removeLife();
                updateLives(livesLeft);
                setNewElementPosition(enemyTank);
            }
            checkCollisionWithBullets(enemyTank);
        }
    }

    private void removeLife(){
        int indexToRemove = livesLeft - 1;
        gamePane.getChildren().remove(playerLives.get(indexToRemove));
        livesLeft--;
        if(livesLeft == 0){
            gameStage.close();
            gameTimer.stop();
        }
    }

    private void updateLives(int livesLeft){
        for (ImageView playerLive : playerLives) {
            gamePane.getChildren().remove(playerLive);
        }
        playerLives = new ArrayList<>();
        for (int i = 0; i < livesLeft; i++) {
            playerLives.add(new ImageView(HEART_IMAGE));
            playerLives.get(i).setLayoutX((GAME_WIDTH-60) - (i*50));
            playerLives.get(i).setLayoutY(80);
            gamePane.getChildren().add(playerLives.get(i));
        }
    }

    private double calculateDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

}
