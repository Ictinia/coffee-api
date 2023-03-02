package com.nat.coffeeapi.dao;

import com.nat.coffeeapi.model.Product;
import java.util.List;

public interface ProductDao {

    Product getProduct(int productId);

    /**
     * Returns all products.
     */
    List<Product> getAllProducts();

    /**
     * Get all products from table for a given roaster name,
     * ordered ASC by producerName.
     */
    List<Product> getProductByRoasterName(String roaster);

    /**
     * Search product by name.
     */
    List<Product> getProductByName(String name);

    /**
     * Will display all products that fit a criteria of tags
     */
    List<Product> getProductByTag(String tags);
    List<Product> getFilteredList(String name, String roaster, String tags);

    /**
     * Get a list of products from table with more than,
     * a given stock value.
     */
    List<Product> getProductsInStock();
    List<Product> getProductsOutOfStock();


    /**
     * Creates a new product.
     */
    Product createProduct(Product newProduct);

    /**
     * Updates existing product.
     *
     * @return
     */
    void updateProductStock(Product updatedProduct);
    void updateProduct(Product updatedProduct);


    /**
     * Deletes specified product from table given product ID.
     * Try not to delete, just leave stock at 0.
     */
    void deleteProduct(int productId);

}