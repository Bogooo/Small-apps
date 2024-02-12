import Model.Operation;
import Model.Polynomial;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMul {
    @ParameterizedTest
    @MethodSource("provideInputMul")
    void testAdditions(Polynomial p, Polynomial q, Polynomial res){
        int i = res.compareTo(Operation.multiply(p,q));
        System.out.println(res);
        System.out.println(Operation.multiply(p,q));
        assertEquals(0,i);
    }
    private static List<Polynomial[]> provideInputMul(){
        List<Polynomial[]> arguments = new ArrayList<>();
        Polynomial[] set1 = new Polynomial[3];
        set1[0] = new Polynomial( new HashMap<Integer,Double>(){{
            put(1,1.0);
            put(2,-1.0);
        }
        });
        set1[1] = new Polynomial( new HashMap<Integer,Double>(){{
            put(1,-1.0);
        }
        });
        set1[2] = new Polynomial( new HashMap<Integer,Double>(){{
            put(2,-1.0);
            put(3,1.0);
        }
        });
        arguments.add(set1);

        Polynomial[] set2 = new Polynomial[3];
        set2[0] = new Polynomial( new HashMap<Integer,Double>(){{
            put(2,3.0);
            put(1,-1.0);
            put(0,1.0);
        }
        });
        set2[1] = new Polynomial( new HashMap<Integer,Double>(){{
            put(1,1.0);
            put(0,-2.0);
        }
        });
        set2[2] = new Polynomial( new HashMap<Integer,Double>(){{
            put(3,3.0);
            put(2,-7.0);
            put(1,3.0);
            put(0,-2.0);
        }
        });
        arguments.add(set2);
        return arguments;
    }
}
