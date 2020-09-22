package Model.entities.enemies;

import javafx.scene.image.ImageView;

public class BlueEnemy extends Enemy {

    public final static String ENEMY_TANK_BLUE = "View/resources/tanks/tank_blue.png";
    public static final String ENEMY_TANK_BLUE_BULLET = "View/resources/bulletBlue3.png";

    public BlueEnemy() {
        super(new ImageView(ENEMY_TANK_BLUE),  new ImageView(ENEMY_TANK_BLUE_BULLET),1);
    }
}
