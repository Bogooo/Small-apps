package BusinessLogic;

import BusinessLogic.Validators.ProductsValidator;
import DataAccess.ProductsDAO;
import Model.Products;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductsBLL {
    private ProductsDAO productsDAO;
    private ProductsValidator validator;

    public ProductsBLL(){
        this.productsDAO=new ProductsDAO();
        this.validator=new ProductsValidator();
    }

    public Products findProductById(int id)throws Exception {
        if(validator.validateNb(id)==false)
            throw new Exception("Invalid id");
        Products product= productsDAO.findById(id,"id_product");
        if(product == null)
            throw new NoSuchElementException("The product with id="+id+" was not found");
        return product;
    }

    public void deleteProductById(int id)throws Exception {
        if(validator.validateNb(id)==false)
            throw new Exception("Invalid id");
        productsDAO.deleteById(id,"id_product");
    }

    public void saveProduct(Products product) throws Exception {
        if(validator.validate(product)==false)
            throw new Exception("Invalid data to update");
        productsDAO.updateById(product.getName(),"name",product.getId_product(),"id_product");
        productsDAO.updateById(product.getPrice(),"price",product.getId_product(),"id_product");
        productsDAO.updateById(product.getStock(),"stock",product.getId_product(),"id_product");
    }

    public void createProducts(Products product)throws Exception {
        if(validator.validate(product)==false)
            throw new Exception("Invalid data to create");
        Products c=productsDAO.findById(product.getId_product(),"id_product");
        if(c!=null)
            throw new Exception("This id already exists");
        productsDAO.insert(product);
    }

    public List<Products> allProducts()
    {
        List<Products> list=productsDAO.findAll();
        return list;
    }
}
