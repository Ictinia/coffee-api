package com.nat.coffeeapi.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;


public class Product {
    private int productId;
    @NotNull(message = "The field name is required.")
    private String productName;
    @NotNull(message = "The field roaster name is required.")
    private String roasterName;
    @NotNull(message = "The field roaster ID is required.")
    private int roasterId;
    @NotNull(message = "The field weight (oz) is required.")
    private double weightOz;
    @NotNull(message = "The field weight (g) is required.")
    private double weightGram;
    @NotNull(message = "The field origin is required.")
    private String origin;
    @NotNull(message = "The field roast type is required.")
    private String roastType;
    @NotNull(message = "The field tags is required.")
    private String tags;
    @NotNull(message = "The field process is required.")
    private String process;
    @NotNull(message = "The field variety is required.")
    private String variety;
    @NotNull(message = "The field elevation is required.")
    private String elevation;
    @NotNull(message = "The field price is required.")
    private BigDecimal price;
    @NotNull(message = "The field short desc. is required.")
    private String shortDescription;
    @NotNull(message = "The field long desc. is required.")
    private String longDescription;
    @NotNull(message = "The field stock is required.")
    private int stock;
    private String productImage;

    public Product() {}

    public Product(int productId, String productName, String roasterName, int roasterId, double weightOz, double weightGram, String origin, String roastType, String tags,
                   String process, String variety, String elevation, BigDecimal price, String shortDescription, String longDescription, int stock, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.roasterName = roasterName;
        this.roasterId = roasterId;
        this.weightOz = weightOz;
        this.weightGram = weightGram;
        this.origin = origin;
        this.roastType = roastType;
        this.tags = tags;
        this.process = process;
        this.variety = variety;
        this.elevation = elevation;
        this.price = price;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.stock = stock;
        this.productImage = "default-product-image.png";
    }

    public double getWeightOz() {
        return weightOz;
    }

    public void setWeightOz(double weightOz) {
        this.weightOz = weightOz;
    }

    public double getWeightGram() {
        return weightGram;
    }

    public void setWeightGram(double weightGram) {
        this.weightGram = weightGram;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRoastType() {
        return roastType;
    }

    public void setRoastType(String roastType) {
        this.roastType = roastType;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRoasterName() {
        return roasterName;
    }

    public void setRoasterName(String roasterName) {
        this.roasterName = roasterName;
    }

    public int getRoasterId() {
        return roasterId;
    }

    public void setRoasterId(int roasterId) {
        this.roasterId = roasterId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public String getProductImage() {return productImage;}
    public void setProductImage(String productImage) {this.productImage = productImage;}

    @Override
    public String toString() {
        return "Product [id =" + productId + ", name=" + productName + ", roaster=" + roasterName + ", roaster-id=" + roasterId + ", weight(oz,g)=" + weightOz + ", " +weightGram + ", origin=" + origin + ", roast=" + roastType +", tags=" + tags + ", process=" + process + ", variety=" + variety + ", elevation=" + elevation + ", price=" + price + ", short-description=" + shortDescription + ", long-description=" + longDescription + ", stock=" + stock + ", product-image=" + productImage + "]";
    }
}

