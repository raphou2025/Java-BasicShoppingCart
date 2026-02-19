// package model;

// import java.time.LocalDateTime;

// public class Product {
//     private int id;
//     private String name;
//     private String description;
//     private double price;
//     private int stock;
//     private LocalDateTime createdAt;
//     private LocalDateTime updatedAt;

//     // Constructor for existing product from DB
//     public Product(int id, String name, String description, double price, int stock,
//                    LocalDateTime createdAt, LocalDateTime updatedAt) {
//         this.id = id;
//         this.name = name;
//         this.description = description;
//         this.price = price;
//         this.stock = stock;
//         this.createdAt = createdAt;
//         this.updatedAt = updatedAt;
//     }

//     // Constructor for creating new product (before inserting to DB)
//     public Product(String name, String description, double price, int stock) {
//         this.name = name;
//         this.description = description;
//         this.price = price;
//         this.stock = stock;
//         this.createdAt = LocalDateTime.now();
//         this.updatedAt = LocalDateTime.now();
//     }

//     // Getters
//     public int getId() { return id; }
//     public String getName() { return name; }
//     public String getDescription() { return description; }
//     public double getPrice() { return price; }
//     public int getStock() { return stock; }
//     public LocalDateTime getCreatedAt() { return createdAt; }
//     public LocalDateTime getUpdatedAt() { return updatedAt; }

//     // Setters
//     public void setName(String name) { this.name = name; }
//     public void setDescription(String description) { this.description = description; }
//     public void setPrice(double price) { this.price = price; }
//     public void setStock(int stock) { this.stock = stock; }
//     public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

//     @Override
//     public String toString() {
//         return name + " - $" + price + " (" + stock + " in stock)";
//     }
// }

package model;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imagePath; // new field

    // ================= CONSTRUCTOR =================
    public Product(int id, String name, String description, double price, int stock,
                   LocalDateTime createdAt, LocalDateTime updatedAt, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imagePath = imagePath;
    }

    // ================= GETTERS & SETTERS =================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
