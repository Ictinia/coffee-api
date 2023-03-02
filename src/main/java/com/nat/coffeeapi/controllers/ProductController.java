package com.nat.coffeeapi.controllers;

import com.nat.coffeeapi.dao.JdbcProductDao;
import com.nat.coffeeapi.dao.ProductDao;
import com.nat.coffeeapi.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController{
    private ProductDao dao;

    public ProductController(ProductDao dao) {
        this.dao = dao;
    }

    /**
     * All get GET action functionality
     */

//    @GetMapping(path = "")
//    public List<Product> getAllProducts() {
//        return dao.getAllProducts();
//    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id){
        return dao.getProduct(id);
    }

    @GetMapping(path = "")
    public List<Product> getProduct(@RequestParam(required = false) Integer id,
                                    @RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "") String roaster,
                                    @RequestParam(defaultValue = "") String tags) {
        List<Product> products = new ArrayList<>();

        if (id != null) {
            products.add(dao.getProduct(id));
            return products;
        }

        if (name.equals("") || roaster.equals("") || tags.equals("")) {
            return getFilteredList(name, roaster, tags);
        }


        if (products == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }

        return products;
    }

    public List<Product> getFilteredList(String name, String roaster, String tags) {
        List<Product> products = new ArrayList<>(dao.getAllProducts());
        List<Product> productList = new ArrayList<>();

        if (emptyString(name) && emptyString(roaster) && emptyString(tags)) {
            return products;
        }

        for (Product product : products) {
            boolean nameCompare = product.getProductName().toLowerCase().contains(name.toLowerCase());
            boolean roasterCompare = product.getRoasterName().toLowerCase().contains(roaster.toLowerCase());
            boolean tagCompare = product.getTags().toLowerCase().contains(tags.toLowerCase());

            if (!emptyString(name) && !emptyString(roaster) && !emptyString(tags)) {
                if (nameCompare && roasterCompare && tagCompare) {
                    productList.add(product);
                }
            } else if (!emptyString(name) && !emptyString(roaster) && emptyString(tags)) {
                if (nameCompare && roasterCompare) {
                    productList.add(product);
                }
            } else if (!emptyString(name) && emptyString(roaster) && !emptyString(tags)) {
                if (nameCompare && tagCompare) {
                    productList.add(product);
                }
            } else if (!emptyString(name) && emptyString(roaster) && emptyString(tags)) {
                if (nameCompare) {
                    productList.add(product);
                }
            } else if (emptyString(name) && !emptyString(roaster) && !emptyString(tags)) {
                if (roasterCompare && tagCompare) {
                    productList.add(product);
                }
            } else if (emptyString(name) && !emptyString(roaster) && emptyString(tags)) {
                if (roasterCompare) {
                    productList.add(product);
                }
            } else if (emptyString(name) && emptyString(roaster) && !emptyString(tags)) {
                if (tagCompare) {
                    productList.add(product);
                }
            } else {
                return products;
            }
        }
        return productList;
    }

    /**
     * GET action functionality for stock
     */
//    @GetMapping(path = "/{stock}")
//    public List<Product> getProductStock(@PathVariable int stock) {
//        List<Product> products = new ArrayList<>();
//
//        if (stock > 0) {
//            products = dao.getProductsInStock();
//        } else if (stock == 0) {
//            products = dao.getProductsOutOfStock();
//        }
//
//        return products;
//    }

    /**
     * Creating product functionality
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "")
    public Product createProduct(@Valid @RequestBody Product product) {
        return dao.createProduct(product);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        dao.deleteProduct(id);
    }

    static private boolean emptyString(String s) {
        // Check if a string is null or empty
        return (s == null || s.trim().length() == 0);
    }

}
