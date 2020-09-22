package View;

import Model.LevelLabel;
import Model.PointLabel;
import Model.entities.enemies.*;
import Model.entities.players.TANK;
import Model.entities.projectiles.Bullet;
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
    private Stage menuStage;

    public static final int GAME_WIDTH = 1024;
    public static final int GAME_HEIGHT = 768;
    public static final int TANK_RADIUS = 21;
    public static final int HEART_RADIUS = 16;
    public static final int TANK_HEIGHT = 46;
    public static final int HEART_DIAMETER = 32;
    public static final int TANK_WIDTH = 42;

    boolean shooting,
            isLeftKeyPressed,
            isRightKeyPressed,
            isUpKeyPressed,
            isDownKeyPressed;

    private int angle;
    private int level;
    private AnimationTimer gameTimer;

    private GridPane gridPane1;
    private GridPane gridPane2;

    public static final String BACKGROUND_IMAGE = "View/resources/bg.png";
    public static final String HEART_IMAGE = "View/resources/heart.png";
    public static final String EXPLOSION = "View/resources/explosionSmoke2.png";
    public static final String BULLET_THIN = "View/resources/shotThin.png";
    public static final String SMOKE = "View/resources/explosionSmoke4.png";

    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> thinBullets;
    private ArrayList<ImageView> playerLives;

    private Random randomNumberGenerator;

    private ImageView tank;
    private ImageView heart;

    private PointLabel pointsLabel;
    private LevelLabel levelLabel;

    private int livesLeft;
    private int points;

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
                shooting = false;
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
        enemies = new ArrayList<>();
        level = 1;
        createBackground();
        createTank(chosenTank);
        addPlayerInfo();
        setUpLevelLabel();
        createGameElements();
        createGameLoop();
        gameStage.show();
    }

    private void createGameElements(){
        addHeart();
        generateNextLevel(level);
    }

    private void generateNextLevel(int level){
        enemies.clear();
        addEnemies(enemies, "GREEN", level);
        addEnemies(enemies, "BLUE",  level);
        addEnemies(enemies,  "RED", level);
        addEnemies(enemies, "HUGE", level);
        addEnemiesToGamePane();
    }

    private void addEnemiesToGamePane() {
        for(Enemy enemy : enemies){
            gamePane.getChildren().add(enemy.getEnemyTankImage());
        }
    }

    private void addPlayerInfo(){
        livesLeft = 3;
        playerLives = new ArrayList<>();
        updateLives(livesLeft);
        setUpPointsLabel();
    }

    private void addHeart(){
        heart = new ImageView(HEART_IMAGE);
        setNewElementPosition(heart);
        gamePane.getChildren().add(heart);
    }

    private void setUpLevelLabel(){
        levelLabel = new LevelLabel("LEVEL: " + level);
        levelLabel.setLayoutX(20);
        levelLabel.setLayoutY(20);
        gamePane.getChildren().add(levelLabel);
    }

    private void setUpPointsLabel(){
        pointsLabel = new PointLabel("POINTS: 00");
        pointsLabel.setLayoutX(GAME_WIDTH - 250);
        pointsLabel.setLayoutY(20);
        gamePane.getChildren().add(pointsLabel);
    }

    private void addEnemies(ArrayList<Enemy> enemies, String type, int amountOfEnemies){
        int startIndex = enemies.size();
        int endIndex = enemies.size() + amountOfEnemies;
        for (int i = startIndex; i < endIndex; i++) {
            switch(type){
                case "BLUE":
                    enemies.add(new BlueEnemy());
                    break;
                case "RED":
                    enemies.add(new BigRedEnemy());
                    break;
                case "HUGE":
                    enemies.add(new HugeEnemy());
                    break;
                case "GREEN":
                    enemies.add(new GreenEnemy());
                    break;
            }
            enemies.get(i).setNewStartPosition();
        }
    }

    private void moveGameElements(){
        heart.setLayoutY(heart.getLayoutY() + 1);

        for (Bullet thinBullet : thinBullets) {
            thinBullet.getBulletImage().setLayoutY(thinBullet.getBulletImage().getLayoutY() - 10);
        }

        for(Enemy enemy : enemies){
            enemy.move();
        }
    }

    private void checkIfElementsAreBehindPlayerAndRelocate(){
        if(heart.getLayoutY() > GAME_HEIGHT){
            heart.setLayoutY(0);
        }
        for(Enemy enemy : enemies){
            enemy.resetPosition();
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
            tank.setRotate(-90);
            if(tank.getLayoutX() > 5){
                tank.setLayoutX(tank.getLayoutX() - 5);
            }
        }
        if(isRightKeyPressed && !isLeftKeyPressed){
            tank.setRotate(90);
            if(tank.getLayoutX() < GAME_WIDTH-51){
                tank.setLayoutX(tank.getLayoutX() + 5);
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
        checkCollisionWithEnemies();
    }

    private void checkCollisionWithHeart(){
        if(TANK_RADIUS+HEART_RADIUS > calculateDistance(
                tank.getLayoutX() + TANK_WIDTH, heart.getLayoutX() + HEART_DIAMETER,
                tank.getLayoutY() + TANK_HEIGHT, heart.getLayoutY()+ HEART_DIAMETER)){
            if(playerLives.size() <= 3 && playerLives.size() > 0){
                livesLeft++;
                updateLives(livesLeft);
                setNewElementPosition(heart);
            }
        }
    }

    private boolean collisionWithBullets(Enemy enemyTank) {
        for(Bullet bullet : thinBullets) {
            if (TANK_RADIUS+4 > calculateDistance(
                    enemyTank.getEnemyTankImage().getLayoutX() + TANK_WIDTH, bullet.getBulletImage().getLayoutX() + 8,
                    enemyTank.getEnemyTankImage().getLayoutY() + TANK_HEIGHT, bullet.getBulletImage().getLayoutY() + 26)) {
                collisionEffect(EXPLOSION, enemyTank.getEnemyTankImage().getLayoutX(), enemyTank.getEnemyTankImage().getLayoutY());
                gamePane.getChildren().remove(bullet.getBulletImage());
                gamePane.getChildren().remove(enemyTank.getEnemyTankImage());
                enemies.remove(enemyTank);
                if(canGoToNextLevel()){
                    createGameElements();
                }
                thinBullets.remove(bullet);
                increasePoints(enemyTank);
                return true;
            }
        }
        return false;
    }

    private void increasePoints(Enemy enemyTank) {
        if(enemyTank instanceof HugeEnemy){
            addPoints(100);
        }
        if(enemyTank instanceof BigRedEnemy){
            addPoints(75);
        }
        if(enemyTank instanceof GreenEnemy || enemyTank instanceof BlueEnemy){
            addPoints(50);
        }
    }

    private void addPoints(int pointAmount) {
        points += pointAmount;
        //for (int j = 0; j <= pointAmount ; j++) {
            pointsLabel.setText("POINTS: " + (points));
        //}
        System.out.println(points);
    }

    private void increaseLevel(){
        level++;
        levelLabel.setText("LEVEL: " + level);
        System.out.println(level);
    }

    private void collisionEffect(String effectImageUrl, double layoutX, double layoutY) {
        ImageView explosion = new ImageView(effectImageUrl);
        explosion.setLayoutX(layoutX);
        explosion.setLayoutY(layoutY);
        gamePane.getChildren().add(explosion);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> gamePane.getChildren().remove(explosion));
        pause.play();    
    }

    private void checkCollisionWithEnemies() {
        for(Enemy enemy : enemies){
            if(TANK_WIDTH > calculateDistance(
                    tank.getLayoutX() + TANK_WIDTH, enemy.getEnemyTankImage().getLayoutX() + TANK_WIDTH,
                    tank.getLayoutY() + TANK_HEIGHT, enemy.getEnemyTankImage().getLayoutY() + TANK_HEIGHT)){
                removeLife();
                updateLives(livesLeft);
                collisionEffect(SMOKE, tank.getLayoutX(), tank.getLayoutY());
                enemy.setNewStartPosition();
            }
            if(collisionWithBullets(enemy)){
                break;
            }
        }
    }

    private void removeLife(){
        int indexToRemove = livesLeft - 1;
        gamePane.getChildren().remove(playerLives.get(indexToRemove));
        livesLeft--;
        if(livesLeft == 0){
            endGame();
        }
    }

    private void endGame(){
        gameStage.close();
        gameTimer.stop();
    }

    private void updateLives(int livesLeft){
        for (ImageView playerLive : playerLives) {
            gamePane.getChildren().remove(playerLive);
        }
        playerLives = new ArrayList<>();
        for (int i = 0; i < livesLeft; i++) {
            playerLives.add(new ImageView(HEART_IMAGE));
            playerLives.get(i).setLayoutX((GAME_WIDTH-105) - (i*50));
            playerLives.get(i).setLayoutY(80);
            gamePane.getChildren().add(playerLives.get(i));
        }
    }

    private double calculateDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

    private boolean canGoToNextLevel(){
        if(enemies.isEmpty()){
            increaseLevel();
            gamePane.getChildren().remove(heart);
            return true;
        }
        return false;
    }

}
