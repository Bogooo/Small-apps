package BusinessLogic;

import BusinessLogic.Validators.ClientsValidator;
import DataAccess.ClientsDAO;
import Model.Clients;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientsBLL {
    private ClientsValidator validator;
    private ClientsDAO clientsDAO;
    public ClientsBLL()
    {
        this.validator=new ClientsValidator();
        this.clientsDAO=new ClientsDAO();
    }

    public Clients findClientById(int id) throws Exception {
        if(validator.validateId(id)==false)
            throw new Exception("Invalid id");
        Clients cl = clientsDAO.findById(id,"id_client");
        if(cl == null)
        {
            throw new NoSuchElementException("The client with id="+id+" was not found");
        }
        return cl;
    }

    public void deleteClientById(int id) throws Exception {
        if(validator.validateId(id)==false)
            throw new Exception("Invalid id");
        clientsDAO.deleteById(id,"id_client");
    }

    public void saveClient(Clients client) throws Exception {
        if(validator.validate(client)==false)
            throw new Exception("Invalid data to update");
        clientsDAO.updateById(client.getName(),"name",client.getId_client(),"id_client");
        clientsDAO.updateById(client.getEmail(),"email",client.getId_client(),"id_client");
    }

    public void createClient(Clients client)throws Exception {
        if(validator.validate(client)==false)
            throw new Exception("Invalid data to create");
        Clients c=clientsDAO.findById(client.getId_client(),"id_client");
        if(c!=null)
            throw new Exception("This id already exists");
        else
            clientsDAO.insert(client);
    }

    public List<Clients> allClients()
    {
        List<Clients> list=clientsDAO.findAll();
        return list;
    }

}
