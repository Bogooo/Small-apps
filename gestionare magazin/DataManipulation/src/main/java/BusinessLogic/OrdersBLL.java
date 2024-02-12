package BusinessLogic;

import BusinessLogic.Validators.OrdersValidator;
import DataAccess.OrdersDAO;
import Model.Carts;
import Model.Orders;

import java.util.List;
import java.util.NoSuchElementException;

public class OrdersBLL {
    private OrdersValidator validator;
    private OrdersDAO ordersDAO;
    public OrdersBLL()
    {
        this.validator=new OrdersValidator();
        this.ordersDAO=new OrdersDAO();
    }

    public Orders findOrderById(int id) throws Exception {
        if(validator.validateNb(id)==false)
            throw new Exception("Invalid id");
        Orders cl = ordersDAO.findById(id,"id_order");
        if(cl == null)
        {
            throw new NoSuchElementException("The order with id="+id+" was not found");
        }
        return cl;
    }

    public void deleteOrderById(int id) throws Exception {
        if(validator.validateNb(id)==false)
            throw new Exception("Invalid id");
        ordersDAO.deleteById(id,"id_order");
    }


    /**
     * Only the client can be changed, the total price is calculated.
     * @param order
     * @throws Exception
     */
    public void saveOrder(Orders order) throws Exception {
        if(validator.validate(order)==false)
            throw new Exception("Invalid data to update");
        ordersDAO.updateById(order.getId_client(),"id_client",order.getId_order(),"id_order");
    }

    public void createOrder(Orders order)throws Exception {
        if(validator.validate(order)==false)
            throw new Exception("Invalid data to create");
        Orders c=ordersDAO.findById(order.getId_order(),"id_order");
        if(c!=null)
            throw new Exception("This id already exists");
        ordersDAO.insert(order);
    }

    public List<Orders> allOrders()
    {
        List<Orders> list=ordersDAO.findAll();
        return list;
    }

    /**
     * Search for all the products from the order and calculate total price.
     * @param idOrder
     */
    public void calculateTotalPrice(int idOrder)
    {
        float sum=0;
        CartsBLL cartsBLL=new CartsBLL();
        try {
            List<Carts> cartsList = cartsBLL.findAllCartsById(idOrder);
            for (Carts c:cartsList)
            {
                sum += c.priceOfItems();
            }
            ordersDAO.updateById(sum,"total_price",idOrder,"id_order");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
