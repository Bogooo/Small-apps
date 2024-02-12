package Model;

import java.util.*;

import static java.lang.Math.abs;

public class Operation {
    //0-null  1-add 2-sub 3-mul 4-div 5-intg 6-der
    private int tip;
    public Operation()
    {
        this.tip=0;
    }

    public Operation(int tip)
    {
        this.tip=tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public int getTip() {
        return tip;
    }

    public String toString()
    {
        return ""+this.tip ;
    }

    public static Polynomial add(Polynomial p, Polynomial q)
    {
        Polynomial sum= new Polynomial((HashMap<Integer, Double>) p.getdegree().clone());
        for(Map.Entry<Integer,Double> entry: q.getdegree().entrySet())
        {
            if(sum.getdegree().containsKey(entry.getKey()))
            {
                sum.addDegree(entry.getKey(), entry.getValue()+sum.getdegree().get(entry.getKey()));
            }
            else
                sum.addDegree(entry.getKey(), entry.getValue());
        }
        return sum;
    }

    public static Polynomial sub(Polynomial p, Polynomial q)
    {
        Polynomial dif= new Polynomial((HashMap<Integer, Double>) p.getdegree().clone());
        for(Map.Entry<Integer,Double> entry: q.getdegree().entrySet())
        {
            if(dif.getdegree().containsKey(entry.getKey()))
            {
                if(Math.abs(dif.getdegree().get(entry.getKey())- entry.getValue())<0.0001)
                    dif.getdegree().remove(entry.getKey());
                else
                    dif.addDegree(entry.getKey(),dif.getdegree().get(entry.getKey())- entry.getValue());
            }
            else
                dif.addDegree(entry.getKey(), -entry.getValue());
        }
        return dif;
    }

    public static Polynomial integral(Polynomial p)
    {
        Polynomial inte = new Polynomial();

        for(Map.Entry entry: p.getdegree().entrySet())
        {
            inte.addDegree((int)entry.getKey()+1,((Double) entry.getValue())/ ((int)entry.getKey()+1));
        }
        return inte;
    }

    public static Polynomial derivative(Polynomial p)
    {
        Polynomial der = new Polynomial();

        for(Map.Entry entry: p.getdegree().entrySet())
        {
            if((int) entry.getKey() > 0)
            der.addDegree((int)entry.getKey()-1,(Double) entry.getValue()*(int)entry.getKey());
        }
        return der;
    }

    public static Polynomial multiply(Polynomial p, Polynomial q)
    {
        Polynomial mul = new Polynomial();
        for(Map.Entry pentry: p.getdegree().entrySet())
        {
            for(Map.Entry qentry: q.getdegree().entrySet())
            {
                if(mul.getdegree().containsKey((int)pentry.getKey()+(int)qentry.getKey()))
                     mul.addDegree((int)pentry.getKey()+(int)qentry.getKey(),(Double) pentry.getValue()*(Double) qentry.getValue()+mul.getdegree().get((int)pentry.getKey()+(int)qentry.getKey()));
                else
                     mul.addDegree((int)pentry.getKey()+(int)qentry.getKey(),(Double) pentry.getValue()*(Double) qentry.getValue());
            }
        }
        return mul;
    }

    public static Polynomial[] div(Polynomial p, Polynomial q)
    {
        Polynomial div=new Polynomial();
        if(!p.getdegree().containsKey(0))
            p.addDegree(0,0.0);

        List<Integer> pDegree = new ArrayList<>(p.getdegree().keySet());
        List<Integer> qDegree = new ArrayList<>(q.getdegree().keySet());
        Collections.sort(pDegree,Collections.reverseOrder());
        Collections.sort(qDegree,Collections.reverseOrder());

        if(qDegree.get(0) != 0)
            while (pDegree.get(0) >= qDegree.get(0))
            {
                Polynomial mono=new Polynomial();
                mono.addDegree(pDegree.get(0)-qDegree.get(0),p.getdegree().get(pDegree.get(0))/q.getdegree().get(qDegree.get(0)));
                div.addDegree(pDegree.get(0)-qDegree.get(0),p.getdegree().get(pDegree.get(0))/q.getdegree().get(qDegree.get(0)));
                mono = Operation.multiply(mono,q);
                p=Operation.sub(p,mono);
                pDegree.clear();
                pDegree.addAll(p.getdegree().keySet());
                Collections.sort(pDegree,Collections.reverseOrder());
            }
        else {
            Polynomial d = new Polynomial();
            d.addDegree(0,1.0/ q.getdegree().get(0));
            div = Operation.multiply(p,d);
            p= new Polynomial();
        }

        Polynomial[] res = new Polynomial[2];
        res[0] = div;
        res[1]= p;
        return res;
    }
}
