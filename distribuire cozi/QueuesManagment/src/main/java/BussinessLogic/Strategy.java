package BussinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;
import java.util.Set;

public interface Strategy {

    public  void addTask(List<Server> servers,Task task);

}
