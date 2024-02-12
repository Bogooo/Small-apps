import Model.Operation;
import Model.Polynomial;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDer {

    @ParameterizedTest
    @MethodSource("provideInputDer")
    void testDerivative(Polynomial p, Polynomial res){
        int i = res.compareTo(Operation.derivative(p));
        System.out.println(res);
        System.out.println(Operation.derivative(p));
        assertEquals(0,i);
    }
    private static List<Polynomial[]> provideInputDer(){
        List<Polynomial[]> arguments = new ArrayList<>();
        Polynomial[] set1 = new Polynomial[2];
        set1[0] = new Polynomial( new HashMap<Integer,Double>(){{
            put(1,1.0);
            put(2,-1.0);
        }
        });
        set1[1] = new Polynomial( new HashMap<Integer,Double>(){{
            put(0,1.0);
            put(1,-2.0);
        }
        });
        arguments.add(set1);

        Polynomial[] set2 = new Polynomial[2];
        set2[0] = new Polynomial( new HashMap<Integer,Double>(){{
            put(3,1.0);
            put(2,-2.0);
            put(1,6.0);
            put(0,-5.0);
        }
        });
        set2[1] = new Polynomial( new HashMap<Integer,Double>(){{
            put(2,3.0);
            put(1,-4.0);
            put(0,6.0);
        }});
        arguments.add(set2);
        return arguments;
    }
}
