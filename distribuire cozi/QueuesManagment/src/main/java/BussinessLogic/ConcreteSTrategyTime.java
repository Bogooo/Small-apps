package BussinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteSTrategyTime implements Strategy{

    @Override
    public void addTask(List<Server> servers, Task task) {

        Server min = servers.get(0);
        for(Server ser: servers)
        {
            if(ser.getWaitingTime() < min.getWaitingTime())
                    min=ser;
        }
        SimulatorManager.totalWaintingTime += min.getWaitingTime();

        min.addTask(task);
    }
}
