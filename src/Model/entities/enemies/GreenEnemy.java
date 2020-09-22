package Model.entities.enemies;

import javafx.scene.image.ImageView;

public class GreenEnemy extends Enemy {

    public static final String ENEMY_GREEN = "View/resources/tanks/tank_green.png";
    public static final String ENEMY_GREEN_BULLET = "View/resources/bulletGreen3.png";

    public GreenEnemy() {
        super(new ImageView(ENEMY_GREEN), new ImageView(ENEMY_GREEN_BULLET), 1.5);
    }

}
