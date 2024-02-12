package BusinessLogic.Validators;

import Model.Orders;

public class OrdersValidator implements Validator<Orders> {
    public boolean validateNb(Number n)
    {
        int nb = n.intValue();
        if(nb<=0)return false;
        return true;
    }
    @Override
    public boolean validate(Orders orders) {
        return validateNb(orders.getId_order()) & validateNb(orders.getId_client());
    }
}
