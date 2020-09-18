package Model;

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

    public void setBulletImage(ImageView bulletImage) {
        this.bulletImage = bulletImage;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
