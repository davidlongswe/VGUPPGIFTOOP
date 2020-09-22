package Model.entities.projectiles;

import javafx.scene.image.ImageView;

public class Bullet {

    private ImageView bulletImage;
    private double posX;
    private double posY;

    public Bullet(ImageView bulletImage, double posX, double posY) {
        this.bulletImage = bulletImage;
        this.posX = posX;
        this.posY = posY;
    }

    public ImageView getBulletImage() {
        return bulletImage;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

}
