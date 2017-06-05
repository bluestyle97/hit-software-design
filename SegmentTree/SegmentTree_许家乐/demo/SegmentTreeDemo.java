package segmenttreedemo.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SegmentTreeDemo extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml_basewindow.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("线段树演示系统");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
