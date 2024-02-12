package Controller;

import Model.Operation;
import Model.Polynomial;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    public static String calculateOperations(String pol1, String pol2 , Operation op) throws Exception
    {
        if(pol1.isEmpty())
        throw new Exception("First polynomial missing!!");

        final String regex="(\\+|-)?(\\d*(\\.\\d+)*)*(X|x)*(\\^[0-9]*)*";
        Pattern pattern = Pattern.compile(regex);
        Matcher m1 = pattern.matcher(pol1);
        Polynomial p=new Polynomial();

        while (m1.find())
        {
            String mono=m1.group();
            if(!mono.isEmpty())
            {
                translatePolynomial(p,mono);
            }

        }


        Polynomial q=new Polynomial();

        if(pol2.isEmpty() && !(op.getTip()==5 || op.getTip()==6) && op.getTip()!=0)
            throw new Exception("Second polynomial is missing!!");
        else {
            Matcher m2 = pattern.matcher(pol2);

            while (m2.find())
            {
                String  mon = m2.group();
                if(!mon.isEmpty())
                {
                    translatePolynomial(q,mon);
                }
            }
        }
        Polynomial res = null;
        Polynomial[] imp = new Polynomial[2];
        imp[0]=new Polynomial();
        imp[1]=new Polynomial();

        switch (op.getTip()){
            case 0:
                throw new Exception("Please select an operation!!");
            case 1:
                res =  Operation.add(p,q);
                break;
            case 2:
                res = Operation.sub(p,q);
                break;
            case 3:
                res = Operation.multiply(p,q);
                break;
            case 4:
                imp = Operation.div(p,q);
                break;
            case 5:
                res = Operation.integral(p);
                break;
            case 6:
                res = Operation.derivative(p);
                break;
        }

        if(op.getTip() == 4) {
            String c="";
            if(imp[0].toString().equals(""))
                c="0";
            else
                c=imp[0].toString();

            String r="";
            if(imp[1].toString().equals(""))
                r="0";
            else
                r=imp[1].toString();

            return c + "\n" + r;
        }
        else
            return res.toString();
    }

    private static void translatePolynomial(Polynomial p, String mono) {
            int pow = 0;
            Double coef=1.0;
            if (mono.charAt(0) == '-')
            {
                coef *= (-1);
                mono=mono.substring(1);
            }
            if(mono.charAt(0) == '+')
                mono=mono.substring(1);

            if (!(mono.contains("X") || mono.contains("x"))) {
                pow = 0;
                coef *= Double.valueOf(mono);
            } else {
                String x = "x";
                if (mono.contains("X")) x = "X";
                String[] nums = mono.split(x);
                if (mono.charAt(0) == 'X' || mono.charAt(0) == 'x') {
                    if (nums.length == 0)
                        pow = 1;
                    else
                    {
                        nums[1] = nums[1].substring(1);
                        pow = Integer.valueOf(nums[1]);
                    }
                } else {
                    coef *= Double.valueOf(nums[0]);
                    if (nums.length == 1)
                        pow = 1;
                    else {
                        nums[1] = nums[1].substring(1);
                        pow = Integer.valueOf(nums[1]);
                    }
                }
            }
            p.addDegree(pow, coef);
    }

}
