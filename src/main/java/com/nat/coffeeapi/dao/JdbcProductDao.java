package com.nat.coffeeapi.dao;

import com.nat.coffeeapi.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {
    private List<Product> products = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;
    public JdbcProductDao(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource);}
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT * FROM product;");
        while (results.next()) {
            products.add(mapRowToProduct(results));
        }
        return products;
    }

    @Override
    public Product getProduct(int productId) {
        Product product = null;
        String sql = "SELECT * " +
                "FROM product " +
                "WHERE product_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, productId);
        if (results.next()) {
            product = mapRowToProduct(results);
        }
        return product;
    }

    @Override
    public List<Product> getProductByName(String search) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM product " +
                "WHERE product_name ILIKE ?;";
        String wild = "%" + search + "%";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, wild);
        while (result.next()) {
            products.add(mapRowToProduct(result));
        }
        return products;
    }
    @Override
    public List<Product> getProductByRoasterName(String roasterName) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * " +
                     "FROM product " +
                     "WHERE roaster_name ILIKE ?";
        String wild = "%" + roasterName + "%";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, wild);
        while (results.next()) {
            products.add(mapRowToProduct(results));
        }
        return products;
    }

    @Override
    public List<Product> getProductByTag(String tags) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * " +
                     "FROM product " +
                     "WHERE tags ILIKE ?;";
        String wild = "%" + tags + "%";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, wild);
        while (result.next()) {
            products.add(mapRowToProduct(result));
        }
        return products;
    }

    @Override
    public List<Product> getFilteredList(String name, String roaster, String tags) {
        List<Product> productList = new ArrayList<>();

        if (emptyString(name) && emptyString(roaster) && emptyString(tags)) {
            return products;
        }

        for (Product product : products) {
            if (!emptyString(name)) {
                if (product.getProductName().toLowerCase().contains(name.toLowerCase())) {
                    productList.add(product);
                }
            } else if (!emptyString(roaster)) {
                if (product.getRoasterName().toLowerCase().contains(roaster.toLowerCase())) {
                    productList.add(product);
                }
            } else if (!emptyString(tags)) {
                if (product.getTags().toLowerCase().contains(tags.toLowerCase())) {
                    productList.add(product);
                }
            }
        }
        return productList;
    }

    @Override
    public List<Product> getProductsInStock() {
        List<Product> products = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT * FROM product WHERE stock > 0;");
        while (results.next()) {
            products.add(mapRowToProduct(results));
        }
        return products;
    }

    @Override
    public List<Product> getProductsOutOfStock() {
        List<Product> products = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT * FROM product WHERE stock <= 0;");
        while (results.next()) {
            products.add(mapRowToProduct(results));
        }
        return products;
    }

    @Override
    public Product createProduct(Product newProduct) {
        String sql = "INSERT INTO product (product_name, roaster_name, roaster_id, weight_oz, weight_g, origin, roast, tags, process, variety, elevation, price, short_desc, long_desc, stock) " +
                     "VALUES (?, ?, (SELECT producer_id FROM product WHERE producer_name = ?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING product_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class,
                newProduct.getProductName(), newProduct.getRoasterName(), newProduct.getRoasterId(), newProduct.getWeightOz(), newProduct.getWeightGram(), newProduct.getOrigin(),
                newProduct.getRoastType(), newProduct.getTags(), newProduct.getProcess(), newProduct.getVariety(), newProduct.getElevation(), newProduct.getPrice(),
                newProduct.getShortDescription(), newProduct.getLongDescription(), newProduct.getStock(), newProduct.getProductImage());

        return getProduct(newId);
    }

    @Override
    public void updateProduct(Product updatedProduct) {
        String sql = "UPDATE product " +
                     "SET product_name = ?, roaster_name = ?, weight_oz = ?, weight_g = ?, origin = ?, roast = ?, tags = ?, process = ?, variety = ?, elevation = ?, price = ?, short_desc = ?, long_desc = ?, stock = ?, product_image = ?;" +
                     "WHERE product_id = ?;";
        jdbcTemplate.update(sql, updatedProduct.getProductName(), updatedProduct.getRoasterName(), updatedProduct.getWeightOz(), updatedProduct.getWeightOz(), updatedProduct.getOrigin(),
                updatedProduct.getRoastType(), updatedProduct.getTags(), updatedProduct.getProcess(), updatedProduct.getVariety(), updatedProduct.getElevation(), updatedProduct.getPrice()
                , updatedProduct.getShortDescription(), updatedProduct.getLongDescription(), updatedProduct.getStock(), updatedProduct.getProductImage(), updatedProduct.getProductId());
    }

    @Override
    public void updateProductStock(Product updatedProduct) {
        String sql = "UPDATE product " +
                     "SET stock = ? " +
                     "WHERE product_id = ?;";
        jdbcTemplate.update(sql, updatedProduct.getStock(), updatedProduct.getProductId());
    }

    @Override
    public void deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE product_id = ?;";
        jdbcTemplate.update(sql, productId);
    }

    private int getMaxId() {
        int maxId = 0;
        for (Product product : products) {
            if (product.getProductId() > maxId) {
                maxId = product.getProductId();
            }
        }
        return maxId;
    }

    private int getMaxIdPlusOne() {
        return getMaxId() + 1;
    }

    private Product mapRowToProduct(SqlRowSet results) {
        Product product = new Product();
        product.setProductId(results.getInt("product_id"));
        product.setProductName(results.getString("product_name"));
        product.setRoasterName(results.getString("roaster_name"));
        product.setRoasterId(results.getInt("roaster_id"));
        product.setWeightOz(results.getDouble("weight_oz"));
        product.setWeightGram(results.getDouble("weight_g"));
        product.setOrigin(results.getString("origin"));
        product.setRoastType(results.getString("roast"));
        product.setTags(results.getString("tags"));
        product.setProcess(results.getString("process"));
        product.setVariety(results.getString("variety"));
        product.setElevation(results.getString("elevation"));
        product.setPrice(results.getBigDecimal("price"));
        product.setShortDescription(results.getString("short_desc"));
        product.setLongDescription(results.getString("long_desc"));
        product.setStock(results.getInt("stock"));
        product.setProductImage(results.getString("product_image"));
        return product;
    }

    static private boolean emptyString(String s) {
        // Check if a string is null or empty
        return (s == null || s.trim().length() == 0);
    }
}
