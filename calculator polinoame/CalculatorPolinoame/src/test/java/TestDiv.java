import Model.Operation;
import Model.Polynomial;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDiv {

    @ParameterizedTest
    @MethodSource("provideInputDiv")
    void testDivision(Polynomial p, Polynomial q, Polynomial res, Polynomial cat){
        Polynomial[] imp = Operation.div(p,q);
        int i = res.compareTo(imp[0]) + cat.compareTo(imp[1]);
        System.out.println(res+" "+cat);
        System.out.println(imp[0] + " "+imp[1]);
        assertEquals(0,i);
    }
    private static List<Polynomial[]> provideInputDiv(){
        List<Polynomial[]> arguments = new ArrayList<>();


        Polynomial[] set2 = new Polynomial[4];
        set2[0] = new Polynomial( new HashMap<Integer,Double>(){{
            put(3,1.0);
            put(2,-2.0);
            put(1,6.0);
            put(0,-5.0);
        }
        });
        set2[1] = new Polynomial( new HashMap<Integer,Double>(){{
            put(2,1.0);
            put(0,-1.0);
        }
        });
        set2[2] = new Polynomial( new HashMap<Integer,Double>(){{
            put(1,1.0);
            put(0,-2.0);
        }
        });
        set2[3] = new Polynomial( new HashMap<Integer,Double>(){{
            put(1,7.0);
            put(0,-7.0);
        }
        });
        arguments.add(set2);
        return arguments;
    }
}
