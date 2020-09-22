package Model.entities.enemies;

import javafx.scene.image.ImageView;

public class BigRedEnemy extends Enemy {

    private final static String ENEMY_BIG_RED = "View/resources/tanks/tank_bigRed.png";
    private static final String ENEMY_BIG_RED_BULLET = "View/resources/bulletRed3.png";

    public BigRedEnemy() {
        super(new ImageView(ENEMY_BIG_RED), new ImageView(ENEMY_BIG_RED_BULLET), 0.75);
    }
}
