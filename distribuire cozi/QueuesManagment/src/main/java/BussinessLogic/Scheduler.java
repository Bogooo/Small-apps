package BussinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTaskPerServer;
    private Strategy strategy;
    private List<Thread> threads;

    public Scheduler(int maxNoServers,int maxTaskPerServer,SelectionPolicy selectionPolicy){
        this.maxNoServers = maxNoServers;
        this.maxTaskPerServer=maxTaskPerServer;
        this.changeStrategy(selectionPolicy);
        servers= new ArrayList<Server>();
        threads= new ArrayList<Thread>();

        for(int i=1;i<=maxNoServers;i++)
        {
            Server ser = new Server();
            servers.add(ser);
            Thread tr=new Thread(ser);
            threads.add(tr);
            tr.start();

        }
    }
    public int totalSizeQueues()
    {
        int sum=0;
        for(Server ser :servers)
        {
            sum+=ser.getTaskNo();
        }
        return sum;
    }
    public List<Server> getServers()
    {
        return servers;
    }

    public void closeServers()
    {
        for(Thread t:threads)
        {
            t.stop();
        }
    }
    public void changeStrategy(SelectionPolicy policy)
    {
        if(policy == SelectionPolicy.SHORTEST_TIME)
        {
            this.strategy = new ConcreteSTrategyTime();
        }
        if(policy == SelectionPolicy.SHORTEST_QUEUE)
        {
            this.strategy = new ConcreteSTrategyQueue();
        }
    }

    synchronized public void dispatchTask(Task task)
    {
        this.strategy.addTask(this.servers,task);
    };


}
