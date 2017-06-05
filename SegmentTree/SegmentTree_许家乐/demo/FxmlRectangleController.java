package segmenttreedemo.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import segmenttreedemo.queryrectangle.RectangleEdge;
import segmenttreedemo.queryrectangle.SegmentTree;

import java.util.ArrayList;
import java.util.Collections;

public class FxmlRectangleController {
    @FXML private Pane canvasPane;

    @FXML private TextField textFieldX;
    @FXML private TextField textFieldY;
    @FXML private TextField textFieldWidth;
    @FXML private TextField textFieldHeight;

    @FXML private TextField textFieldCount;
    @FXML private TextField textFieldArea;
    @FXML private TextField textFieldCircumference;

    @FXML private Button closeButton;

    private double mouseOriginalX;
    private double mouseOriginalY;
    private double mouseFinalX;
    private double mouseFinalY;

    private int count = 0;
    private ArrayList<RectangleEdge> verticalEdges = new ArrayList<>();
    private ArrayList<RectangleEdge> horizontalEdges = new ArrayList<>();
    private double area;
    private double circumference;

    @FXML
    protected void handleMousePressed(MouseEvent event) {
        mouseOriginalX = event.getX();
        mouseOriginalY = event.getY();
        ObservableList<Node> children = canvasPane.getChildren();
        children.add(new Rectangle(mouseOriginalX, mouseOriginalY, 0, 0));
    }

    @FXML
    protected void handleMouseDragged(MouseEvent event) {
        double mouseCurrentX = event.getX();
        double mouseCurrentY = event.getY();
        if (0 <= mouseCurrentX && mouseCurrentX <= canvasPane.getWidth()) {
            mouseFinalX = mouseCurrentX;
        } else if (mouseCurrentX < 0) {
            mouseFinalX = 0;
        } else {
            mouseFinalX = canvasPane.getWidth();
        }
        if (0 <= mouseCurrentY && mouseCurrentY <= canvasPane.getHeight()) {
            mouseFinalY = mouseCurrentY;
        } else if (mouseCurrentY < 0) {
            mouseFinalY = 0;
        } else {
            mouseFinalY = canvasPane.getHeight();
        }

        double x = Math.min(mouseOriginalX, mouseFinalX);
        double y = Math.min(mouseOriginalY, mouseFinalY);
        double w = Math.max(mouseOriginalX, mouseFinalX) - x;
        double h = Math.max(mouseOriginalY, mouseFinalY) - y;
        Rectangle rectangle = new Rectangle(x, y, w, h);
        rectangle.setStrokeWidth(2);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);
        ObservableList<Node> children = canvasPane.getChildren();
        children.remove(children.size() - 1);
        children.add(rectangle);
    }

    @FXML
    protected void handleMouseReleased() {
        double x = Math.min(mouseOriginalX, mouseFinalX);
        double y = Math.min(mouseOriginalY, mouseFinalY);
        double w = Math.max(mouseOriginalX, mouseFinalX) - x;
        double h = Math.max(mouseOriginalY, mouseFinalY) - y;

        textFieldX.setText(String.valueOf(x));
        textFieldY.setText(String.valueOf(y));
        textFieldWidth.setText(String.valueOf(w));
        textFieldHeight.setText(String.valueOf(h));

        addRectangleEdges(x, y, w, h);

        ++count;
        area = 0;
        circumference = 0;
        queryRectangle();
        updateQueryFields();
    }

    @FXML
    protected void handleRevokeButtonClicked() {
        ObservableList<Node> children = canvasPane.getChildren();
        if (!children.isEmpty()) {
            Rectangle removedRectangle = (Rectangle)children.get(children.size() - 1);
            children.remove(removedRectangle);

            double x = removedRectangle.getX();
            double y = removedRectangle.getY();
            double w = removedRectangle.getWidth();
            double h = removedRectangle.getHeight();

            ArrayList<RectangleEdge> removedEdges = new ArrayList<>();
            int removeCounter = 0;
            for (RectangleEdge e : verticalEdges) {
                if (e.position == x && e.begin == y && e.end == y + h && e.weight == 1) {
                    removedEdges.add(e);
                    ++removeCounter;
                }
                if (e.position == x + w && e.begin == y && e.end == y + h && e.weight == -1) {
                    removedEdges.add(e);
                    ++removeCounter;
                }
                if (removeCounter == 2) {
                    break;
                }
            }
            verticalEdges.removeAll(removedEdges);
            removedEdges.clear();
            removeCounter = 0;
            for (RectangleEdge e : horizontalEdges) {
                if (e.position == y && e.begin == x && e.end == x + w && e.weight == 1) {
                    removedEdges.add(e);
                    ++removeCounter;
                }
                if (e.position == y + h && e.begin == x && e.end == x + w && e.weight == -1) {
                    removedEdges.add(e);
                    ++removeCounter;
                }
                if (removeCounter == 2) {
                    break;
                }
            }
            horizontalEdges.removeAll(removedEdges);

            textFieldX.setText("0.0");
            textFieldY.setText("0.0");
            textFieldWidth.setText("0.0");
            textFieldHeight.setText("0.0");

            --count;
            area = 0;
            circumference = 0;
            queryRectangle();
            updateQueryFields();
        }
    }

    @FXML
    protected void handleClearButtonClicked() {
        textFieldX.setText("0.0");
        textFieldY.setText("0.0");
        textFieldWidth.setText("0.0");
        textFieldHeight.setText("0.0");

        count = 0;
        area = 0;
        circumference = 0;
        updateQueryFields();

        canvasPane.getChildren().clear();
        verticalEdges.clear();
        horizontalEdges.clear();
    }

    @FXML
    protected void handleCloseButtonClicked() {
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleAddButtonClicked() {
        String xText = textFieldX.getText();
        String yText = textFieldY.getText();
        String widthText = textFieldWidth.getText();
        String heightText = textFieldHeight.getText();

        double x, y, width, height;
        try {
            x = Double.parseDouble(xText);
            y = Double.parseDouble(yText);
            width = Double.parseDouble(widthText);
            height = Double.parseDouble(heightText);
        } catch(Exception e) {
            x = 0;
            y = 0;
            width = 0;
            height = 0;
        }

        if (0 <= x && x <= canvasPane.getWidth() && 0 <= y && y <= canvasPane.getHeight() && width > 0 && height > 0) {
            if (x + width > canvasPane.getWidth()) {
                width = canvasPane.getWidth() - x;
                textFieldWidth.setText(String.valueOf(width));
            }
            if (y + height > canvasPane.getHeight()) {
                height = canvasPane.getHeight() - y;
                textFieldHeight.setText(String.valueOf(height));
            }

            Rectangle rectangle = new Rectangle(x, y, width, height);
            rectangle.setStrokeWidth(2);
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.TRANSPARENT);
            ObservableList<Node> children = canvasPane.getChildren();
            children.add(rectangle);

            addRectangleEdges(x, y, width, height);

            ++count;
            area = 0;
            circumference = 0;
            queryRectangle();
            updateQueryFields();
        } else {
            textFieldX.setText("0.0");
            textFieldY.setText("0.0");
            textFieldWidth.setText("0.0");
            textFieldHeight.setText("0.0");
        }
    }

    private void addRectangleEdges(double x, double y, double width, double height) {
        verticalEdges.add(new RectangleEdge(x, y, y + height, 1));
        verticalEdges.add(new RectangleEdge(x + width, y, y + height, -1));
        horizontalEdges.add(new RectangleEdge(y, x, x + width, 1));
        horizontalEdges.add(new RectangleEdge(y + height, x, x + width, -1));
    }

    private void updateQueryFields() {
        textFieldCount.setText(String.valueOf(count));
        textFieldArea.setText(String.valueOf(area));
        textFieldCircumference.setText(String.valueOf(circumference));
    }

    private void queryRectangle()
    {
        Collections.sort(verticalEdges);
        Collections.sort(horizontalEdges);
        double[] verticalCoordinates = new double[verticalEdges.size()];
        double[] horizontalCoordinates = new double[horizontalEdges.size()];
        int index = 0;
        for (RectangleEdge e : verticalEdges) {
            if (e.weight == 1) {
                verticalCoordinates[index++] = e.begin;
                verticalCoordinates[index++] = e.end;
            }
        }
        index = 0;
        for (RectangleEdge e : horizontalEdges) {
            if (e.weight == 1) {
                horizontalCoordinates[index++] = e.begin;
                horizontalCoordinates[index++] = e.end;
            }
        }

        solveRectangle(verticalCoordinates, horizontalCoordinates);
    }

    private void solveRectangle(double[] verticalArray, double[] horizonalArray) {
        insertionSort(verticalArray);
        insertionSort(horizonalArray);
        SegmentTree treeScanVerticalEdges = new SegmentTree(verticalArray);
        SegmentTree treeScanHorizontalEdges = new SegmentTree(horizonalArray);

        double totalVerticalLength = 0;
        double previousSegmentLength = 0;
        for (int i = 0; i < verticalEdges.size() - 1; ++i) {
            RectangleEdge e = verticalEdges.get(i);
            treeScanVerticalEdges.update(e.begin, e.end, e.weight);
            area += treeScanVerticalEdges.getLength() * (verticalEdges.get(i + 1).position - e.position);
            if (e.weight == 1) {
                totalVerticalLength = totalVerticalLength + treeScanVerticalEdges.getLength() - previousSegmentLength;
            }
            previousSegmentLength = treeScanVerticalEdges.getLength();
        }

        double totalHorizontalLength = 0;
        previousSegmentLength = 0;
        for (RectangleEdge e : horizontalEdges) {
            treeScanHorizontalEdges.update(e.begin, e.end, e.weight);
            if (e.weight == 1) {
                totalHorizontalLength = totalHorizontalLength + treeScanHorizontalEdges.getLength() - previousSegmentLength;
            }
            previousSegmentLength = treeScanHorizontalEdges.getLength();
        }

        circumference = 2 * (totalVerticalLength + totalHorizontalLength);
    }

    private void insertionSort(double[] array) {
        for (int i = 1; i < array.length; ++i) {
            double tmp = array[i];
            int j;
            for (j = i; j > 0 && tmp < array[j - 1]; --j)
                array[j] = array[j - 1];
            array[j] = tmp;
        }
    }
}
