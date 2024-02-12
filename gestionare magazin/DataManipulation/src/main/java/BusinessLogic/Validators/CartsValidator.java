package BusinessLogic.Validators;

import Model.Carts;

public class CartsValidator implements Validator<Carts>{
    private boolean validateNb(Number n)
    {
        int nb = n.intValue();
        if(nb<=0)return false;
        return true;
    }

    @Override
    public boolean validate(Carts carts) {
        return validateNb(carts.getId_order())&validateNb(carts.getId_product())&validateNb(carts.getItems_number())&validateNb(carts.getPrice_current());
    }
}
