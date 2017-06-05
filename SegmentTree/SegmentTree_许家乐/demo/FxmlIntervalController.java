package segmenttreedemo.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import segmenttreedemo.queryinterval.TwoDimensionalSegmentTree;

public class FxmlIntervalController {
    @FXML private GridPane matrixPane;
    @FXML private TextField modifyTextField;
    @FXML private Button closeButton;
    @FXML private TextField minTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField sumTextField;

    private int originalColumnIndex;
    private int originalRowIndex;
    private int finalColumnIndex;
    private int finalRowIndex;
    private int[][] matrix = new int[10][10];

    @FXML
    protected void handleMousePressed(MouseEvent event) {
        ObservableList<Node> children = matrixPane.getChildren();
        updateMatrix();
        originalColumnIndex = (int)event.getX() / 60;
        originalRowIndex = (int)event.getY() / 60;
        for (Node node : children) {
            if (node instanceof Label) {
                int columnOfNode = GridPane.getColumnIndex(node);
                int rowOfNode = GridPane.getRowIndex(node);
                if (columnOfNode == originalColumnIndex && rowOfNode == originalRowIndex) {
                    node.setStyle("-fx-background-color: lightsteelblue");
                } else {
                    node.setStyle("-fx-background-color: transparent");
                }
            }
        }
    }

    @FXML
    protected void handleMouseDragged(MouseEvent event) {
        ObservableList<Node> children = matrixPane.getChildren();
        finalColumnIndex = (int)event.getX() / 60;
        finalRowIndex = (int)event.getY() / 60;

        int columnLeft = Math.min(originalColumnIndex, finalColumnIndex);
        int columnRight = Math.max(originalColumnIndex, finalColumnIndex);
        int rowLeft = Math.min(originalRowIndex, finalRowIndex);
        int rowRight = Math.max(originalRowIndex, finalRowIndex);
        for (Node node : children) {
            if (node instanceof Label) {
                int columnOfNode = GridPane.getColumnIndex(node);
                int rowOfNode = GridPane.getRowIndex(node);
                if (rowLeft <= rowOfNode && rowOfNode <= rowRight &&
                        columnLeft <= columnOfNode && columnOfNode <= columnRight) {
                    node.setStyle("-fx-background-color: lightsteelblue");
                } else {
                    node.setStyle("-fx-background-color: transparent");
                }
            }
        }
    }

    @FXML
    protected void handleMouseReleased(MouseEvent event) {
        finalColumnIndex = (int)event.getX() / 60;
        finalRowIndex = (int)event.getY() / 60;
        updateTextFields();
    }

    @FXML
    protected void handleUpdateButtonClicked() {
        String text = modifyTextField.getText();
        int modifyValue;
        try {
            modifyValue = Integer.parseInt(text);
        } catch (Exception e) {
            modifyValue = 0;
        }

        ObservableList<Node> children = matrixPane.getChildren();
        int columnLeft = Math.min(originalColumnIndex, finalColumnIndex);
        int columnRight = Math.max(originalColumnIndex, finalColumnIndex);
        int rowLeft = Math.min(originalRowIndex, finalRowIndex);
        int rowRight = Math.max(originalRowIndex, finalRowIndex);

        for (Node node : children) {
            if (node instanceof Label) {
                int columnOfNode = GridPane.getColumnIndex(node);
                int rowOfNode = GridPane.getRowIndex(node);
                if (rowLeft <= rowOfNode && rowOfNode <= rowRight &&
                        columnLeft <= columnOfNode && columnOfNode <= columnRight) {
                    int preValue = Integer.parseInt(((Label)node).getText());
                    ((Label)node).setText(String.valueOf(preValue + modifyValue));
                }
            }
        }
        updateTextFields();
    }

    @FXML
    protected void handleCloseButtonClicked() {
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }

    private void updateMatrix() {
        ObservableList<Node> children = matrixPane.getChildren();
        for (Node node : children) {
            if (node instanceof Label) {
                int columnOfNode = GridPane.getColumnIndex(node);
                int rowOfNode = GridPane.getRowIndex(node);
                String text = ((Label)node).getText();
                matrix[rowOfNode][columnOfNode] = Integer.parseInt(text);
            }
        }
    }

    private void updateTextFields() {
        updateMatrix();
        TwoDimensionalSegmentTree segmentTree = new TwoDimensionalSegmentTree(matrix);

        int columnLeft = Math.min(originalColumnIndex, finalColumnIndex);
        int columnRight = Math.max(originalColumnIndex, finalColumnIndex);
        int rowLeft = Math.min(originalRowIndex, finalRowIndex);
        int rowRight = Math.max(originalRowIndex, finalRowIndex);

        int minOfArea = segmentTree.queryIntervalMin(rowLeft, rowRight, columnLeft, columnRight);
        int maxOfArea = segmentTree.queryIntervalMax(rowLeft, rowRight, columnLeft, columnRight);
        int sumOfArea = segmentTree.queryIntervalSum(rowLeft, rowRight, columnLeft, columnRight);

        minTextField.setText(String.valueOf(minOfArea));
        maxTextField.setText(String.valueOf(maxOfArea));
        sumTextField.setText(String.valueOf(sumOfArea));
    }
}
