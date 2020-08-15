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
        System.out.println("Seed: " + nn.getSeed());
        NNlib.showInfo(NNlib.infoGraph(false), nn);
        train(3);
        System.exit(0);
    }

    public static void train(int epochs) {
        System.out.println("Training started...");
        for (int epoch = 1; epoch <= epochs; epoch++) {
            test();
            int threadnum = 4;
            int batchsize = 60000 / threadnum;
            Thread[] threads = new Thread[threadnum];
            for (int i = 0; i < threadnum; i++) {
                int start = batchsize * i;
                int end = batchsize * (i + 1);
                threads[i] = new Thread(() -> {
                    for (int j = start; j < end; j++) {
                        nn.backpropagation(new float[][][]{mnist.trainingimages[j]}, mnist.traininglabels[j]);
//                        nn.feedforward(new float[][][]{mnist.trainingimages[i]});
//                        System.out.println(Thread.currentThread().getName() + " " + j);
                    }
                });
                threads[i].setName("t" + i);
                threads[i].start();
            }
            for (int i = 0; i < 4; i++) {
                try {
                    threads[i].join();
                } catch (Exception e) {
                }
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
