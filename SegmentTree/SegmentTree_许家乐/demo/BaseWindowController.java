package segmenttreedemo.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BaseWindowController {
    @FXML private Button closeButton;

    @FXML
    protected void handleRectangleButtonClicked() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml_rectangle.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("线段树演示——求解矩形面积与周长");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void handleIntervalButtonClicked() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml_interval.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("线段树演示——求解区间最值与总和");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void handleCloseButtonClicked() {
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }
}
