package BusinessLogic;

import BusinessLogic.Validators.CartsValidator;
import DataAccess.BillDAO;
import DataAccess.CartsDAO;
import Model.Bills;
import Model.Carts;
import Model.Orders;
import Model.Products;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class CartsBLL {
    //actualizeaza restul
    private CartsValidator validator;
    private CartsDAO cartsDAO;
    private BillDAO billDAO;
    public CartsBLL()
    {
        this.validator=new CartsValidator();
        this.cartsDAO=new CartsDAO();
        this.billDAO=new BillDAO();
    }


    /**
     * Return a list with all products from an order
     * @param id
     * @return
     * @throws Exception
     */
    public List<Carts> findAllCartsById(int id) throws Exception {
        if(id<=0)
            throw new Exception("Invalid id");
        List<Carts> cl = cartsDAO.findAllById(id);
        if(cl == null)
        {
            throw new NoSuchElementException("The carts with id="+id+" were not found");
        }
        return cl;
    }

    /**
     * remove a product from an order
     * @param idOrder
     * @param idProduct
     * @throws Exception
     */
    public void deleteCartById(int idOrder,int idProduct) throws Exception {
        if(idOrder<=0 || idProduct<=0)
            throw new Exception("Invalid ids");
        cartsDAO.deleteByBothIds(idOrder,idProduct);
    }

    /**
     * Add a product in an order, and also create a bill and save for the current transaction at the current time.
     * @param cart
     * @throws Exception
     */
    public void createCarts(Carts cart)throws Exception {
        if(validator.validate(cart)==false)
            throw new Exception("Invalid data to create");
        Carts c=cartsDAO.findByBothIds(cart.getId_order(),cart.getId_product());
        if(c!=null)
        {
            deleteCartById(cart.getId_order(),cart.getId_product());
            cart.setItems_number(cart.getItems_number()+c.getItems_number());
        }
        ProductsBLL pl =new ProductsBLL();
        Products pr =(Products) pl.findProductById(cart.getId_product());

        OrdersBLL ordersBLL=new OrdersBLL();
        Orders order=ordersBLL.findOrderById(cart.getId_order());
        if(cart.getItems_number() > pr.getStock())
            throw  new Exception("Not enough stock");
        cartsDAO.insert(cart);

        Bills bills =new Bills(cart.getId_order(),order.getId_client(),cart.getId_product(),cart.priceOfItems(), LocalDateTime.now().toString());
        billDAO.insert(bills);
    }
}
