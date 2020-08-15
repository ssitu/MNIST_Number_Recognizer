package numberclassifier;

import java.util.Random;
import numberclassifier.NNlib.*;
import numberclassifier.NNlib.Layer.*;

public class Model {

    private static final NN fc = new NN("fc", 0, .0001f, LossFunctions.CROSSENTROPY(1), Optimizers.ADAM,
            new Flatten(1, 28, 28),
            new Dense(128, Activations.RELU, Initializers.HE),
            new Dense(128, Activations.RELU, Initializers.HE),
            new Dense(10, Activations.SOFTMAX, Initializers.XAVIER)
    );

    private static final NN cnn = new NN("cnn", 0, 0, LossFunctions.CROSSENTROPY(1), Optimizers.ADADELTA,
            new Layer.Conv(1, 28, 28, 10, 3, 3, 1, 0, 0, Activations.TANH),
            new Layer.Conv(10, 5, 5, 3, 0, 0, Activations.TANH),
            new Flatten(),
            new Dense(10, Activations.SOFTMAX, Initializers.XAVIER)
    );

    public static final NN nn = fc;

    static {
        System.out.println("nn parameters: " + nn.getParameterCount());
    }
}
