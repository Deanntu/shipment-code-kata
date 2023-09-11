package com.trendyol.shipment;

import java.util.*;
import java.util.stream.Collectors;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() {
        return findLargestSize(products);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    private ShipmentSize findLargestSize(List<Product> products) {
        Map<ShipmentSize, Long> sizeCount = products.stream()
                .map(Product::getSize)
                .collect(Collectors.groupingBy(
                        size -> size,
                        Collectors.counting()
                ));

        for (ShipmentSize size : sizeCount.keySet()) {
            if (sizeCount.get(size) >= 3) {
                return getNextSize(size);
            }
        }

        return products.stream()
                .map(Product::getSize)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    private static ShipmentSize getNextSize(ShipmentSize size) {
        return switch (size) {
            case SMALL -> ShipmentSize.MEDIUM;
            case MEDIUM -> ShipmentSize.LARGE;
            case LARGE -> ShipmentSize.X_LARGE;
            default -> size;
        };
    }
}
