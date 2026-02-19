// package model;

// import java.time.LocalDateTime;

// public class CartItem {
//     private int id;
//     private int userId;
//     private Product product;
//     private int quantity;
//     private String status;
//     private LocalDateTime addedAt;

//     // Constructor for cart item retrieved from DB
//     public CartItem(int id, int userId, Product product, int quantity, String status, LocalDateTime addedAt) {
//         this.id = id;
//         this.userId = userId;
//         this.product = product;
//         this.quantity = quantity;
//         this.status = status;
//         this.addedAt = addedAt;
//     }

//     // Constructor for new cart item (before inserting to DB)
//     public CartItem(int userId, Product product, int quantity) {
//         this.userId = userId;
//         this.product = product;
//         this.quantity = quantity;
//         this.status = "active";
//         this.addedAt = LocalDateTime.now();
//     }

//     // Getters
//     public int getId() { return id; }
//     public int getUserId() { return userId; }
//     public Product getProduct() { return product; }
//     public int getQuantity() { return quantity; }
//     public String getStatus() { return status; }
//     public LocalDateTime getAddedAt() { return addedAt; }

//     // Setters
//     public void setQuantity(int quantity) { this.quantity = quantity; }
//     public void setStatus(String status) { this.status = status; }

//     // Total price for this item
//     public double getTotal() {
//         return product.getPrice() * quantity;
//     }

//     @Override
//     public String toString() {
//         return product.getName() + " x" + quantity + " = $" + getTotal();
//     }
// }

package model;

import java.time.LocalDateTime;

public class CartItem {
    private int id;
    private int userId;
    private Product product;
    private int quantity;
    private String status;
    private LocalDateTime addedAt;

    // Constructor for items retrieved from DB
    public CartItem(int id, int userId, Product product, int quantity, String status, LocalDateTime addedAt) {
        this.id = id;
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
        this.addedAt = addedAt;
    }

    // Constructor for new items
    public CartItem(int userId, Product product, int quantity) {
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
        this.status = "active";
        this.addedAt = LocalDateTime.now();
    }

    // Standard Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public LocalDateTime getAddedAt() { return addedAt; }

    // Setters
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setStatus(String status) { this.status = status; }

    // Logic: Total price for this line item
    public double getTotal() {
        if (product == null) return 0.0;
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return (product != null ? product.getName() : "Unknown") + 
               " x" + quantity + " = $" + String.format("%.2f", getTotal());
    }
}