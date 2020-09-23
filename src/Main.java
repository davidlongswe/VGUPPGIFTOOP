import View.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ViewManager viewManager = new  ViewManager();
        primaryStage = viewManager.getMainStage();
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
