package Controller;

import Model.Operation;
import Model.Polynomial;

public class Main  {

    public static void main(String[] args) {
        Polynomial p = new Polynomial();;
        Polynomial q = new Polynomial();;

        p.addDegree(3,1.0);
        p.addDegree(2,-2.0);
        p.addDegree(1,6.0);
        p.addDegree(0,-5.0);

        q.addDegree(2,1.0);
        q.addDegree(0,-1.0);

        Polynomial div[] = Operation.div(p,q);
        System.out.println(p);
        System.out.println(q);
        System.out.println(div[0]);
        System.out.println(div[1]);


    }

}
