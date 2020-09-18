package Model;

public enum TANK {
    DARK("View/resources/tanks/tank_dark_rotated.png"),
    RED("View/resources/tanks/tank_red_rotated.png"),
    SAND("View/resources/tanks/tank_sand_rotated.png");

    private String urlTank;
    private String urlLife;

    TANK(String urlTank){
        this.urlTank = urlTank;
    }

    public String getUrlTank() {
        return urlTank;
    }

    public String getUrlLife(){
        return urlLife;
    }

}
