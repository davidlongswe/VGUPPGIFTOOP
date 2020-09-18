package Model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class TankPicker extends VBox {

    private ImageView checkBoxImage;
    private ImageView tankImage;

    private String checkBoxUnchecked = "Model/resources/grey_box.png";
    private String checkBoxChecked = "Model/resources/blue_boxCheckmark.png";

    private TANK tank;
    private boolean isTankChosen;

    public TankPicker(TANK tank){
        checkBoxImage = new ImageView(checkBoxUnchecked);
        tankImage = new ImageView(tank.getUrlTank());
        this.tank = tank;
        isTankChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(checkBoxImage,tankImage);
    }

    public TANK getTank(){
        return tank;
    }

    public void setTankChosen(boolean tankChosen) {
        isTankChosen = tankChosen;
        String imageToSet = this.isTankChosen ? checkBoxChecked : checkBoxUnchecked;
        checkBoxImage.setImage(new Image(imageToSet));
    }
}
