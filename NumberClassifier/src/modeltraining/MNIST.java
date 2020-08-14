package modeltraining;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import numberclassifier.NNlib;

public class MNIST {

    private static final String CD = System.getProperty("user.dir") + File.separator + "src" + File.separator + "modeltraining" + File.separator;
    private static final String TRAIN_IMAGES_PATH = CD + "train-images.idx3-ubyte";
    private static final String TRAIN_LABELS_PATH = CD + "train-labels.idx1-ubyte";
    private static final String TEST_IMAGES_PATH = CD + "t10k-images.idx3-ubyte";
    private static final String TEST_LABELS_PATH = CD + "t10k-labels.idx1-ubyte";
    public float[][][] trainingimages = new float[60000][28][28];
    public float[][][] traininglabels = new float[60000][1][10];
    public float[][][] testingimages = new float[10000][28][28];
    public float[][][] testinglabels = new float[10000][1][10];

    public MNIST() {
        try {
            System.out.println("Loading the MNIST Dataset...");
            load();
            System.out.println("MNIST Dataset Finished Loading.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void load() throws IOException, ClassNotFoundException {
        //Training set images
        System.out.println("Loading Training Images...");
        FileChannel channel = new FileInputStream(TRAIN_IMAGES_PATH).getChannel();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        System.out.println("Read in " + buffer.getInt() + ", should equal 2051.");
        System.out.println("Read in " + buffer.getInt() + ", should equal 60000.");
        System.out.println("Read in " + buffer.getInt() + ", should equal 28.");
        System.out.println("Read in " + buffer.getInt() + ", should equal 28.");
        for (int n = 0; n < 60000; n++) {
            for (int row = 0; row < 28; row++) {
                for (int col = 0; col < 28; col++) {
                    trainingimages[n][row][col] = (float) (buffer.get() / 255.0);
                }
            }
        }
        System.out.println("Training Images Finished Loading.");
        channel.close();
        //Training set labels
        System.out.println("Loading Training Labels...");
        channel = new FileInputStream(TRAIN_LABELS_PATH).getChannel();
        buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        System.out.println("Read in " + buffer.getInt() + ", should equal 2049.");
        System.out.println("Read in " + buffer.getInt() + ", should equal 60000.");
        for (int n = 0; n < 60000; n++) {
            traininglabels[n] = NNlib.oneHot(1, 10, 0, buffer.get());
        }
        channel.close();
        System.out.println("Training Labels Finished Loading.");
        //Test set images
        System.out.println("Loading Test Images...");
        channel = new FileInputStream(TEST_IMAGES_PATH).getChannel();
        buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        System.out.println("Read in " + buffer.getInt() + ", should equal 2051.");
        System.out.println("Read in " + buffer.getInt() + ", should equal 10000.");
        System.out.println("Read in " + buffer.getInt() + ", should equal 28.");
        System.out.println("Read in " + buffer.getInt() + ", should equal 28.");
        for (int n = 0; n < 10000; n++) {
            for (int row = 0; row < 28; row++) {
                for (int col = 0; col < 28; col++) {
                    testingimages[n][row][col] = (float) (buffer.get() / 255.0);
                }
            }
        }
        channel.close();
        System.out.println("Test Images Finished Loading.");
        //Test set labels
        System.out.println("Loading Test Labels...");
        channel = new FileInputStream(TEST_LABELS_PATH).getChannel();
        buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        System.out.println("Read in " + buffer.getInt() + ", should equal 2049.");
        System.out.println("Read in " + buffer.getInt() + ", should equal 10000.");
        for (int n = 0; n < 10000; n++) {
            testinglabels[n] = NNlib.oneHot(1, 10, 0, buffer.get());
        }
        channel.close();
        System.out.println("Test Labels Finished Loading.");
    }
}
