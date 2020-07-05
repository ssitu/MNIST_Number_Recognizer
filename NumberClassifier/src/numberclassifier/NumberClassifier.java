package numberclassifier;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NumberClassifier extends Application {

    public final int CANVAS_SIZE = 812;
    public final int WINDOW_WIDTH = (int) (CANVAS_SIZE * 1.5);
    public static Group ROOT = new Group();
    private DrawingCanvas canvas;
    private Text[] percents = new Text[9];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ROOT.getChildren().addAll(createCanvas(), rightSide());
        Scene scene = new Scene(ROOT, WINDOW_WIDTH, CANVAS_SIZE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    public StackPane createCanvas() {
        canvas = new DrawingCanvas(CANVAS_SIZE, 28, Color.WHITE);
        StackPane pane = new StackPane(canvas);
        pane.setStyle("-fx-background-color: #363636;");
        return pane;
    }

    public Group rightSide() {
        //Clear Canvas Button
        Rectangle shape = new Rectangle();
        shape.setWidth(CANVAS_SIZE * 3 / 7);
        shape.setHeight(shape.getWidth() / 3);
        shape.setArcWidth(shape.getHeight());
        shape.setArcHeight(shape.getHeight());
        shape.setFill(Color.GRAY.darker().darker());
        Text label = new Text("Clear Canvas");
        label.setStyle("-fx-font: " + shape.getWidth() / 10 + "px MSPGothic");
        label.setFill(Color.ALICEBLUE);
        StackPane button = new StackPane(shape, label);
        button.setOnMousePressed((event) -> {
            shape.setFill(((Color) shape.getFill()).darker());
        });
        button.setOnMouseReleased((event) -> {
            shape.setFill(((Color) shape.getFill()).brighter());
        });
        button.setOnMouseClicked((event) -> {
            canvas.clearCanvas();
        });
        button.setTranslateX(CANVAS_SIZE + (WINDOW_WIDTH - CANVAS_SIZE) / 2 - shape.getWidth() / 2);
        button.setTranslateY(CANVAS_SIZE / 50);
        //Classifications
        VBox classifications = new VBox();
        for (int i = 0; i < 9; i++) {
            classifications.getChildren().add(classification(i + 1, i * CANVAS_SIZE / 32));
        }
        classifications.setTranslateX(CANVAS_SIZE * 1.05);
        classifications.setTranslateY(button.getTranslateY() + shape.getHeight() * 1.5);
        Group ui = new Group(button, classifications);
        return ui;
    }

    public HBox classification(int num, int gap) {
        int fontsize = CANVAS_SIZE / 28;
        Text numClass = new Text(num + ":   ");
        numClass.setStyle("-fx-font: " + fontsize + "px MSPGothic");
        Text classPercent = new Text("0.00");
        classPercent.setStyle("-fx-font: " + fontsize + "px MSPGothic");
        Text percentSymbol = new Text("%");
        percentSymbol.setStyle("-fx-font: " + fontsize + "px MSPGothic");
        HBox hbox = new HBox(numClass, classPercent, percentSymbol);
        hbox.setTranslateY(gap);
        percents[num - 1] = classPercent;
        return hbox;
    }

    public static double decimalToPercent(double decimal) {
        return Math.round(decimal * 10000) / 100.0;
    }
}
