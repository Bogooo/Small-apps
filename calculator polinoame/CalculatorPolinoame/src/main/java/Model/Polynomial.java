package Model;

import java.util.*;

import static java.lang.Math.abs;

public class Polynomial implements Comparable{
    private HashMap<Integer, Double> degree;

    public Polynomial(HashMap<Integer,Double> degree){
        this.degree=degree;
    }
    public Polynomial()
    {
        degree = new HashMap<>();
    }

    public void setdegree(HashMap<Integer, Double> p)
    {
        this.degree=p;
    }

    public HashMap<Integer, Double> getdegree() {
        return degree;
    }

    public String toString()
    {
        String s="";
        boolean ok=true;
        List<Integer> sorted = new ArrayList<>(this.degree.keySet());
        Collections.sort(sorted,Collections.reverseOrder());

        for (Integer key:sorted)
        {
            if(abs(this.degree.get(key)) > 0.0001)
            {

            if(ok)ok=false;
            else
            {
                if( (Double) this.degree.get(key) >0)
                    s+="+";
            }
            if(key == 0)
                s+=this.degree.get(key).shortValue();
            else
                if(key!= 1)
                    if(abs(this.degree.get(key)+1.0) < 0.001)
                        s+="-X^"+key;
                    else
                        if(abs(this.degree.get(key)-1) > 0.001)s+=this.degree.get(key).floatValue()+"X^"+key;
                                    else s+="X^"+key;
                else
                    if(abs(this.degree.get(key)+1.0) < 0.001)
                        s+="-X";
                    else
                        if(abs(this.degree.get(key)-1) > 0.001)s+=this.degree.get(key).floatValue()+"X";
                            else s+="X";
        }}
        return s;
    }

    @Override
    public int compareTo(Object p) {
        Polynomial q= (Polynomial) p;
        return  this.toString().compareTo(q.toString());
    }

    public void addDegree(Integer degr, Double coef)
    {
        this.degree.put(degr,coef);
    }

    public void removeDegree(Integer degr,Double coef)
    {
        this.degree.remove(degr);
    }
}
