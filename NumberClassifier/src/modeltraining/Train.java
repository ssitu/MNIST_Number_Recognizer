package modeltraining;

import java.io.FileNotFoundException;
import java.io.IOException;
import numberclassifier.Model;
import numberclassifier.NNlib;
import static numberclassifier.NNlib.*;
import numberclassifier.NNlib.NN;

public class Train {

    public static NN nn = Model.nn;
    public static MNIST mnist = new MNIST();

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        nn.loadInsideJar();
        NNlib.showInfo(NNlib.infoGraph(false), nn);
        train(50);
        System.exit(0);
    }

    public static void train(int epochs) {
        System.out.println("Training started...");
        for (int epoch = 1; epoch <= epochs; epoch++) {
            test();
            for (int i = 0; i < 60000; i++) {
                nn.backpropagation(new float[][][]{mnist.trainingimages[i]}, mnist.traininglabels[i]);
            }
            nn.saveInsideJar();
            System.out.println("Epochs: " + epoch);
        }
        System.out.println("Training ended.");
    }

    public static void test() {
        System.out.println("Testing started...");
        int correct = 0;
        for (int n = 0; n < 10000; n++) {
            int predictedNum = argmax((float[][]) nn.feedforward(new float[][][]{mnist.testingimages[n]}));
            int actualNum = argmax(mnist.testinglabels[n]);
            if (predictedNum == actualNum) {
                correct++;
            }
        }
        System.out.println("Accuracy: " + (correct / 10000.0) * 100 + "%");
        System.out.println(correct + " out of the 10000 testing examples predicted correctly");
        System.out.println("Testing ended.");
    }
}
