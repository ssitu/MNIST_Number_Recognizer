package numberclassifier;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DrawingCanvas extends Canvas {

    private PixelWriter pw;
    private float[][] pixelArray;
    private final double SIZE;
    private final double RES;
    private final int PIXELSIZE;
    private final Color FILL;
    private int[] lastPixel = {-1, -1};
    private static final float SURROUNDING_FACTOR = .1f;

    public DrawingCanvas(int size, int res, Color fill) {
        super(size, size);
        SIZE = size;
        RES = res;
        PIXELSIZE = size / res;
        FILL = fill;
        pw = this.getGraphicsContext2D().getPixelWriter();
        pixelArray = new float[res][res];
        for (int i = 0; i < res; i++) {
            for (int j = 0; j < res; j++) {
                pixelArray[i][j] = 0;
            }
        }
        this.setOnMousePressed((t) -> {
            fillPixel(t, FILL, true);
        });
        this.setOnMouseDragged((t) -> {
            fillPixel(t, FILL, false);
        });
    }

    private void fillPixel(MouseEvent t, Color fill, boolean pressed) {
        final int xScaled = (int) (t.getX() / SIZE * RES);
        final int yScaled = (int) (t.getY() / SIZE * RES);
        if (xScaled >= 0 && xScaled < RES && yScaled >= 0 && yScaled < RES && (lastPixel[0] != xScaled || lastPixel[1] != yScaled || pressed)) {
            lastPixel[0] = xScaled;
            lastPixel[1] = yScaled;
            pixelArray[yScaled][xScaled] = 1;
            for (int x = xScaled * PIXELSIZE; x < (xScaled + 1) * PIXELSIZE; x++) {
                for (int y = yScaled * PIXELSIZE; y < (yScaled + 1) * PIXELSIZE; y++) {
                    pw.setColor(x, y, fill);
                }
            }
            for (int i = -1; i <= 1; i += 2) {
                try {
                    int newX = xScaled + i;
                    pixelArray[yScaled][newX] = addInRange(pixelArray[yScaled][newX], SURROUNDING_FACTOR, 0, 1);
                    for (int x = newX * PIXELSIZE; x < (newX + 1) * PIXELSIZE; x++) {
                        for (int y = yScaled * PIXELSIZE; y < (yScaled + 1) * PIXELSIZE; y++) {
                            pw.setColor(x, y, Color.hsb(fill.getHue(), 0, 1, pixelArray[yScaled][newX]));
                        }
                    }
                } catch (Exception e) {
                }
            }
            for (int i = -1; i <= 1; i += 2) {
                try {
                    int newY = yScaled + i;
                    pixelArray[newY][xScaled] = addInRange(pixelArray[newY][xScaled], SURROUNDING_FACTOR, 0, 1);
                    for (int x = xScaled * PIXELSIZE; x < (xScaled + 1) * PIXELSIZE; x++) {
                        for (int y = newY * PIXELSIZE; y < (newY + 1) * PIXELSIZE; y++) {
                            pw.setColor(x, y, Color.hsb(fill.getHue(), 0, 1, pixelArray[newY][xScaled]));
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private float addInRange(float val1, float val2, float min, float max) {
        float sum = val1 + val2;
        if (sum > max) {
            sum = max;
        } else if (sum < min) {
            sum = min;
        }
        return sum;
    }

    public void clearCanvas() {
        for (int i = 0; i < RES; i++) {
            for (int j = 0; j < RES; j++) {
                pixelArray[i][j] = 0;
            }
        }
        this.getGraphicsContext2D().clearRect(0, 0, SIZE, SIZE);
    }

    public float[][] getPixelArray() {
        return pixelArray;
    }
}
