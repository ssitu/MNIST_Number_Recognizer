package modeltraining;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import numberclassifier.NNlib;

public class MNIST {

    private static final String CD = System.getProperty("user.dir") + File.separator + "src" + File.separator + "modeltraining" + File.separator;
    private static final String TRAIN_IMAGES_PATH = CD + "train-images.idx3-ubyte";
    private static final String TRAIN_LABELS_PATH = CD + "train-labels.idx1-ubyte";
    private static final String TEST_IMAGES_PATH = CD + "t10k-images.idx3-ubyte";
    private static final String TEST_LABELS_PATH = CD + "t10k-labels.idx1-ubyte";
    private static final String TRAIN_IMAGES_SER = CD + "trainingimages.ser";
    private static final String TRAIN_LABELS_SER = CD + "traininglabels.ser";
    private static final String TEST_IMAGES_SER = CD + "testingimages.ser";
    private static final String TEST_LABELS_SER = CD + "testinglabels.ser";
    public float[][][] trainingimages = new float[60000][28][28];
    public float[][][] traininglabels = new float[60000][1][10];
    public float[][][] testingimages = new float[10000][28][28];
    public float[][][] testinglabels = new float[10000][1][10];

    public MNIST() {
        try {
            System.out.println("Loading the MNIST dataset...");
            load();
            System.out.println("Loading finished.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void load() throws IOException, ClassNotFoundException {
        //Training set images
        if (new File(TRAIN_IMAGES_SER).isFile()) {
            trainingimages = (float[][][]) new ObjectInputStream(new FileInputStream(TRAIN_IMAGES_SER)).readObject();
        } else {
            FileInputStream fis = new FileInputStream(TRAIN_IMAGES_PATH);
            byte[] intbytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                fis.read(intbytes);
                int integer = 0;
                for (int j = 0; j < 4; j++) {
                    integer <<= 8;
                    integer |= intbytes[j] & 0xff;
                }
                System.out.print(integer + " ");
            }
            System.out.println("");
            for (int n = 0; n < 60000; n++) {
                for (int row = 0; row < 28; row++) {
                    for (int col = 0; col < 28; col++) {
                        trainingimages[n][row][col] = (float) (fis.read() / 255.0);
                    }
                }
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TRAIN_IMAGES_SER));
            out.writeObject(trainingimages);
        }
        //Training set labels
        if (new File(TRAIN_LABELS_SER).isFile()) {
            traininglabels = (float[][][]) new ObjectInputStream(new FileInputStream(TRAIN_LABELS_SER)).readObject();
        } else {
            FileInputStream fis = new FileInputStream(TRAIN_LABELS_PATH);
            byte[] intbytes = new byte[4];
            for (int i = 0; i < 2; i++) {
                fis.read(intbytes);
                int integer = 0;
                for (int j = 0; j < 4; j++) {
                    integer <<= 8;
                    integer |= intbytes[j] & 0xff;
                }
                System.out.print(integer + " ");
            }
            System.out.println("");
            for (int n = 0; n < 60000; n++) {
                traininglabels[n] = NNlib.oneHot(1, 10, 0, fis.read());
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TRAIN_LABELS_SER));
            out.writeObject(traininglabels);
        }
        //Test set images
        if (new File(TEST_IMAGES_SER).isFile()) {
            testingimages = (float[][][]) new ObjectInputStream(new FileInputStream(TEST_IMAGES_SER)).readObject();
        } else {
            FileInputStream fis = new FileInputStream(TEST_IMAGES_PATH);
            byte[] intbytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                fis.read(intbytes);
                int integer = 0;
                for (int j = 0; j < 4; j++) {
                    integer <<= 8;
                    integer |= intbytes[j] & 0xff;
                }
                System.out.print(integer + " ");
            }
            System.out.println("");
            for (int n = 0; n < 10000; n++) {
                for (int row = 0; row < 28; row++) {
                    for (int col = 0; col < 28; col++) {
                        testingimages[n][row][col] = (float) (fis.read() / 255.0);
                    }
                }
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TEST_IMAGES_SER));
            out.writeObject(testingimages);
        }
        //Test set labels
        if (new File(TEST_LABELS_SER).isFile()) {
            testinglabels = (float[][][]) new ObjectInputStream(new FileInputStream(TEST_LABELS_SER)).readObject();
        } else {
            FileInputStream fis = new FileInputStream(TEST_LABELS_PATH);
            byte[] intbytes = new byte[4];
            for (int i = 0; i < 2; i++) {
                fis.read(intbytes);
                int integer = 0;
                for (int j = 0; j < 4; j++) {
                    integer <<= 8;
                    integer |= intbytes[j] & 0xff;
                }
                System.out.print(integer + " ");
            }
            System.out.println("");
            for (int n = 0; n < 10000; n++) {
                testinglabels[n] = NNlib.oneHot(1, 10, 0, fis.read());
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TEST_LABELS_SER));
            out.writeObject(testinglabels);
        }
    }
}
