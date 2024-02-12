package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private BlockingQueue<Task> tasks;

    public Server()
    {
        tasks = new LinkedBlockingQueue<Task>();
    };

    public int getTaskNo()
    {
        return tasks.size();
    }

    public int getWaitingTime()
    {
        int sum = 0;
        for(Task t:tasks)
            sum+=t.getServiceTime();
        return sum;
    }
    public void addTask(Task task)
    {
        this.tasks.add(task);
    }
    public BlockingQueue<Task> getTask()
    {
        return tasks;
    }

    @Override
    public void run() {
        Task curr = null;

        while (true)
        {
            if(!tasks.isEmpty())
            {
                curr=tasks.element();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
               // System.out.println("wait:" + waitingPeriod);
                if(curr.getServiceTime() > 1) {
                    curr.setServiceTime(curr.getServiceTime()-1);
                }
                else
                {
                    tasks.remove(curr);
                }
            }

        }
    }

    public String toString()
    {
        String s="";
        for(Task task: tasks)
        {
            s+=task+", ";
        }
        if(s!="")s.substring(0,s.length()-1);
        return s;
    }
}
