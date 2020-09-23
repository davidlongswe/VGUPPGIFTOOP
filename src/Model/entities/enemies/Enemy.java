package Model.entities.enemies;

import javafx.animation.PauseTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

public class Enemy {

    public static final int GAME_HEIGHT = 768;
    public static final int GAME_WIDTH = 1024;
    private ImageView enemyTankBulletImage;
    private ImageView enemyTankImage;
    private double speed;

    public Enemy(ImageView enemyTankImage, ImageView enemyTankBulletImage, double speed) {
        this.enemyTankImage = enemyTankImage;
        this.enemyTankBulletImage = enemyTankBulletImage;
        this.speed = speed;
    }

    public void move(){
        enemyTankImage.setLayoutY(enemyTankImage.getLayoutY() + speed);
    }

    public void setNewStartPosition(){
        Random randomNumberGenerator = new Random();
        enemyTankImage.setLayoutX(randomNumberGenerator.nextInt(GAME_WIDTH-46));
        enemyTankImage.setLayoutY(-(randomNumberGenerator.nextInt(GAME_HEIGHT-46)));
    }

    public boolean passedSouthernBorder(){
        if(enemyTankImage.getLayoutY() > GAME_HEIGHT){
            return true;
        }else{
            return false;
        }
    }

    public ImageView getEnemyTankImage() {
        return enemyTankImage;
    }

    public ImageView getEnemyTankBulletImage(){
        return enemyTankImage;
    }

    public void spawnBullet(){
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            enemyTankBulletImage.setLayoutX(enemyTankImage.getLayoutX() + 21);
            enemyTankBulletImage.setLayoutY(enemyTankImage.getLayoutY());
        });
        pause.play();
    }

    public void shoot(){
        enemyTankBulletImage.setLayoutY(enemyTankBulletImage.getLayoutY() + 5);
    }

}
