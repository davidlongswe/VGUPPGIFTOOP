package Model.entities.enemies;

import javafx.scene.image.ImageView;

public class HugeEnemy extends Enemy {

    public final static String ENEMY_HUGE = "View/resources/tanks/tank_huge.png";
    public static final String ENEMY_HUGE_BULLET = "View/resources/shotRed.png";

    public HugeEnemy() {
        super(new ImageView(ENEMY_HUGE),  new ImageView(ENEMY_HUGE_BULLET),0.5);
    }

}
