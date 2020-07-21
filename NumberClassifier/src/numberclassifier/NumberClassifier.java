package numberclassifier;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import numberclassifier.NNlib.NN;

public class NumberClassifier extends Application {

    public final int CANVAS_SIZE = 952;
    public final int WINDOW_WIDTH = (int) (CANVAS_SIZE * 1.5);
    public static Group ROOT = new Group();
    private DrawingCanvas canvas;
    private final Text[] percents = new Text[10];
    private NN nn = Model.cnn5;
    private Timeline updater;

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
        initUpdater();
        nn.loadInsideJar();
    }

    public StackPane createCanvas() {
        canvas = new DrawingCanvas(CANVAS_SIZE, 28, Color.WHITE);
        StackPane pane = new StackPane(canvas);
        pane.setStyle("-fx-background-color: #363636;");
        return pane;
    }

    public void initUpdater() {
        updater = new Timeline(new KeyFrame(Duration.millis(32), (t) -> {
            float[] prediction = ((float[][]) nn.feedforward(new float[][][]{canvas.getPixelArray()}))[0];
            for (int i = 0; i < 10; i++) {
                percents[i].setText(String.valueOf(toPercent(prediction[i])));
            }
        }));
        updater.setCycleCount(-1);
        updater.play();
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
        for (int i = 0; i < 10; i++) {
            classifications.getChildren().add(classification(i, i * CANVAS_SIZE / 32));
        }
        classifications.setTranslateX(CANVAS_SIZE * 1.05);
        classifications.setTranslateY(button.getTranslateY() + shape.getHeight() * 1.25);
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
        percents[num] = classPercent;
        return hbox;
    }

    public static float toPercent(float decimal) {
        return Math.round(decimal * 100);
    }
}
