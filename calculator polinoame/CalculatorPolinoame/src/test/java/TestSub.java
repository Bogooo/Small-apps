import Model.Operation;
import Model.Polynomial;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSub {
    @ParameterizedTest
    @MethodSource("provideInputSub")
    void testSubstraction(Polynomial p, Polynomial q, Polynomial res){
        int i = res.compareTo(Operation.sub(p,q));
        System.out.println(res);
        System.out.println(Operation.sub(p,q));
        assertEquals(0,i);
    }
    private static List<Polynomial[]> provideInputSub(){
        List<Polynomial[]> arguments = new ArrayList<>();
        Polynomial[] set1 = new Polynomial[3];
        set1[0] = new Polynomial( new HashMap<Integer,Double>(){{
            put(1,1.0);
            put(2,-3.45);
        }
        });
        set1[1] = new Polynomial( new HashMap<Integer,Double>(){{
            put(1,1.0);
            put(2,3.4);
        }
        });
        set1[2] = new Polynomial( new HashMap<Integer,Double>(){{
            put(2,-6.85);
        }
        });
        arguments.add(set1);

        Polynomial[] set2 = new Polynomial[3];
        set2[0] = new Polynomial( new HashMap<Integer,Double>(){{
            put(5,4.0);
            put(4,-3.0);
            put(2,1.0);
            put(1,-8.0);
            put(0,1.0);
        }
        });
        set2[1] = new Polynomial( new HashMap<Integer,Double>(){{
            put(4,3.0);
            put(3,-1.0);
            put(2,1.0);
            put(1,2.0);
            put(0,-1.0);
        }
        });
        set2[2] = new Polynomial( new HashMap<Integer,Double>(){{
            put(5,4.0);
            put(4,-6.0);
            put(3,1.0);
            put(1,-10.0);
            put(0,2.0);
        }
        });
        arguments.add(set2);
        return arguments;
    }
}
