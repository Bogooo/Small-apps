package BusinessLogic.Validators;

import Model.Products;

public class ProductsValidator implements Validator<Products>{

    public boolean validateNb(Number n)
    {
        int nb = n.intValue();
        if(nb<0)return false;
        return true;
    }
    @Override
    public boolean validate(Products products) {
        return validateNb(products.getId_product())&validateNb(products.getStock())&validateNb(products.getPrice());
    }
}
