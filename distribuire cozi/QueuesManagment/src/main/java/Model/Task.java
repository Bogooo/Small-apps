package Model;

import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Comparable,Cloneable{
    private int id;
    private int arrivelTime;
    private AtomicInteger serviceTime;
    private  static int idNumber=1;
    public Task(int arrivelTime,int serviceTime)
    {
        this.id = idNumber;
        idNumber++;
        this.arrivelTime=arrivelTime;
        this.serviceTime=new AtomicInteger();
        this.serviceTime.set(serviceTime);
    };

    public static void reset()
    {
        idNumber=1;
    }
    public int getArrivelTime() {
        return arrivelTime;
    }

    public void setArrivelTime(int arrivelTime) {
        this.arrivelTime = arrivelTime;
    }

    public int getServiceTime() {
        return serviceTime.intValue();
    }

    synchronized public void setServiceTime(int serviceTime) {
        this.serviceTime.set( serviceTime);
    }

    public int compareTo(Object o)
    {
        Task t= (Task) o;
        return this.arrivelTime - t.getArrivelTime();
    }

    @Override
    public String toString() {
        return "("+this.id+","+this.arrivelTime+","+this.serviceTime+")";
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
