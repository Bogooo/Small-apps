package BussinessLogic;

import Model.Server;
import Model.Task;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimulatorManager implements Runnable{

    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int maxArrivalTime;
    private int minArrivalTime;
    private int numberOfServers;
    private int numberOfClients;
    private SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private  BlockingQueue<Task>  generatedTasks;
    private ObservableList<String> queues;
    private ObservableList<String> queuesDWL;

    private boolean allOrCurrent;
    private int totalServiceTime;
    private int peakHour;
    private int maxTasksInHour;
    public static int totalWaintingTime;

    public SimulatorManager(int numberOfClients, int numberOfServers, int timeLimit, int maxArrivalTime, int minArrivalTime, int maxProcessingTime, int minProcessingTime, SelectionPolicy sel , ObservableList<String> queues,ObservableList<String> queuesDWL,boolean allorc)
    {
        this.timeLimit=timeLimit;
        this.maxProcessingTime=maxProcessingTime;
        this.minProcessingTime=minProcessingTime;
        this.numberOfServers=numberOfServers;
        this.numberOfClients=numberOfClients;
        this.minArrivalTime=minArrivalTime;
        this.maxArrivalTime=maxArrivalTime;
        this.selectionPolicy=sel;
        this.queues=queues;
        this.queuesDWL=queuesDWL;
        this.allOrCurrent=allorc;
        this.scheduler = new Scheduler(numberOfServers,numberOfClients,selectionPolicy);
        generatedTasks = new LinkedBlockingQueue<Task>();
        this.generateRandomTasks();
    }

    public void generateRandomTasks()
    {
        int arrTime;
        int procTime;
        Task.reset();
        totalServiceTime=0;
        List<Task> gen = new ArrayList<>();
        Random rand = new Random();
        for(int i=1;i<= this.numberOfClients;i++)
        {
            procTime = rand.nextInt(this.minProcessingTime,this.maxProcessingTime+1);
            arrTime = rand.nextInt(minArrivalTime,maxArrivalTime+1);
            gen.add(new Task(arrTime,procTime));
            totalServiceTime+=procTime;
        }
        Collections.sort(gen);

        for(Task t: gen)
            generatedTasks.add(t);
    }

    @Override
    public void run() {
        int currentTime=0;
        peakHour=0;
        maxTasksInHour=0;
        SimulatorManager.totalWaintingTime=0;
        while (currentTime < this.timeLimit)
        {
            for (Task task:generatedTasks)
            {
                if(task.getArrivelTime() == currentTime)
                {
                           scheduler.dispatchTask(task);
                           generatedTasks.remove(task);
                }
            }
            int finalCurrentTime = currentTime;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    actualizare(finalCurrentTime,false);
                }
            });
            if(maxTasksInHour < scheduler.totalSizeQueues())
            {
                maxTasksInHour= scheduler.totalSizeQueues();
                peakHour=currentTime;
            }
            currentTime++;
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(checkEmptyness())
              break;
        }

        if(currentTime<=timeLimit)
        {
            int finalCurrentTime = currentTime;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    actualizare(finalCurrentTime,true);
                }
            });
        }

        scheduler.closeServers();
        if(!generatedTasks.isEmpty())
        {
            numberOfClients-=generatedTasks.size();
        }
    }

    public boolean checkEmptyness(){
        if(generatedTasks.isEmpty() && scheduler.totalSizeQueues()==0)
            return true;
        return false;
    }
    public void actualizare(int time,boolean ok)
    {
        List queu = new ArrayList();
        queu.add("");
        queu.add("Time: "+time);
        String s="Waiting clients: ";
        for(Task t:generatedTasks)
        {
            s+=t+",";
        }
        s=s.substring(0,s.length()-1);
        queu.add(s);
        int i=1;
        for(Server ser : scheduler.getServers()) {
            s = "Queue" + i + ": " + ser;
            queu.add(s);
            i++;
        }
        if(allOrCurrent)queues.clear();
        queues.addAll(queu);
        queuesDWL.addAll(queu);
        if(ok)
        {
            queues.add("");
            queues.add("Peak hour: "+peakHour);
            queues.add("Avg service time: "+ (1.0*totalServiceTime/numberOfClients));
            queues.add("Avg waiting time: "+ (1.0*totalWaintingTime/numberOfClients));
            queuesDWL.add("");
            queuesDWL.add("Peak hour: "+peakHour);
            queuesDWL.add("Avg service time: "+ (1.0*totalServiceTime/numberOfClients));
            queuesDWL.add("Avg waiting time: "+ (1.0*totalWaintingTime/numberOfClients));
        }
    }

}
