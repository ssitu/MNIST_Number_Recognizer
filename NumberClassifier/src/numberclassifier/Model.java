package numberclassifier;

import java.util.Random;
import numberclassifier.NNlib.*;
import numberclassifier.NNlib.Layer.*;

public class Model {
    
    private static final NN fc = new NN("fc", 1, .001f, LossFunctions.CROSSENTROPY(1), Optimizers.ADAM,
            new Flatten(1, 28, 28),
            new Dense(128, Activations.RELU, Initializers.HE),
            new Dense(128, Activations.RELU, Initializers.HE),
            new Dense(10, Activations.SOFTMAX, Initializers.XAVIER)
    );
    
    public static final NN nn = fc;
    
    static {
        System.out.println("nn parameters: " + nn.getParameterCount());
    }
}
