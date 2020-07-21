package numberclassifier;

import numberclassifier.NNlib.*;
import numberclassifier.NNlib.Layer.*;
import static numberclassifier.NNlib.*;

public class Model {

    public static final NN fc = new NN("fc", 0, .0001f, LossFunction.CROSSENTROPY(1), Optimizer.AMSGRAD,//94.32%
            new Flatten(1, 28, 28),
            new Dense(784, 200, Activation.TANH, Initializer.XAVIER),
            new Dense(200, 80, Activation.TANH, Initializer.XAVIER),
            new Dense(80, 10, Activation.SOFTMAX, Initializer.XAVIER)
    );

    public static final NN cnn = new NN("cnn", 0, 0, LossFunction.CROSSENTROPY(1), Optimizer.ADADELTA,//97.41%
            new Conv(20, 1, 3, 3, 1, 0, 0, Activation.TANH),//1-28-28 conv(s=1) 20-3-3 = 20-26-26
            new Conv(10, 20, 5, 5, 3, 0, 0, Activation.TANH),//20-26-26 conv(s=3) 10-20-5-5 = 10-8-8
            new Flatten(10, 8, 8),//10-8-8 = 640
            new Dense(640, 10, Activation.SOFTMAX, Initializer.XAVIER)
    );

    public static final NN cnn2 = new NN("cnn2", 0, 0, LossFunction.CROSSENTROPY(1), Optimizer.ADADELTA,//96.215
            new Conv(20, 1, 3, 3, 1, 0, 0, Activation.TANH),//1-28-28 conv(s=1) 20-3-3 = 20-26-26
            new Maxpool(20, 26, 26, 2, 2, 2),//20-26-26 pool(s=2) 2-2 = 20-13-13
            new Conv(20, 20, 10, 10, 1, 0, 0, Activation.TANH),//20-13-13 conv(s=1) 20-20-10-10 = 20-4-4
            new Flatten(20, 4, 4),//20-4-4 = 320
            new Dense(320, 10, Activation.SOFTMAX, Initializer.XAVIER)
    );

    public static final NN cnn3 = new NN("cnn3", 0, .0001f, LossFunction.CROSSENTROPY(.2), Optimizer.AMSGRAD,//95.8%
            new Conv(20, 1, 3, 3, 1, 2, 2, Activation.TANH),//1-32-32 conv(s=1) 20-3-3 = 20-30-30
            new Maxpool(20, 30, 30, 5, 5, 5),//20-30-30 pool(s=5) 5-5 = 20-6-6
            new Flatten(20, 6, 6),//20-6-6 = 720
            new Dense(720, 10, Activation.SOFTMAX, Initializer.XAVIER)
    );

    public static final NN cnn4 = new NN("cnn4", 0, 0, LossFunction.CROSSENTROPY(1), Optimizer.ADADELTA,//98.04%
            new Conv(5, 1, 3, 3, 1, 1, 1, Activation.TANH),//1-30-30 conv(s=1) 20-1-3-3 = 5-28-28
            new Conv(5, 5, 8, 8, 2, 0, 0, Activation.TANH),//10-28-28 conv(s=2) 10-10-8-8 = 10-11-11
            new Flatten(5, 11, 11),//10-11-11 = 1210
            new Dense(605, 10, Activation.SOFTMAX, Initializer.XAVIER)
    );
    
    public static final NN cnn5 = new NN("cnn5", 0, 0, LossFunction.QUADRATIC(.1), Optimizer.ADADELTA,//98.29%
            new Conv(5, 1, 5, 5, 1, 1, 1, Activation.TANH),//1-30-30 conv(s=1) 5-1-5-5 = 5-26-26
            new Conv(10, 5, 8, 8, 2, 0, 0, Activation.TANH),//5-26-26 conv(s=2) 10-5-8-8 = 10-10-10
            new Flatten(10, 10, 10),//10-10-10 = 1000
            new Dense(1000, 10, Activation.SOFTMAX, Initializer.XAVIER)
    );

    static {
        System.out.println("fc learnable Parameters: " + fc.getParameterCount());
        System.out.println("cnn learnable Parameters: " + cnn.getParameterCount());
        System.out.println("cnn2 learnable Parameters: " + cnn2.getParameterCount());
        System.out.println("cnn3 learnable Parameters: " + cnn3.getParameterCount());
        System.out.println("cnn4 learnable Parameters: " + cnn4.getParameterCount());
        System.out.println("cnn5 learnable Parameters: " + cnn5.getParameterCount());
    }
}
