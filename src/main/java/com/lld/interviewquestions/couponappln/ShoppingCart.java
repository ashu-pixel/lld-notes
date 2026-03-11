package com.lld.interviewquestions.couponappln;


import java.util.ArrayList;
import java.util.List;

import com.lld.interviewquestions.couponappln.coupons.PercentageCouponDecorator;
import com.lld.interviewquestions.couponappln.coupons.TypeCouponDecorator;
import com.lld.interviewquestions.couponappln.product.Product;

public class ShoppingCart {

    List<Product> productList;

    public ShoppingCart() {
        productList = new ArrayList<>();
    }

    public void addToCart(Product product) {
        // Decorate the Product with applicable coupons
        Product productWithEligibleDiscount =
                new TypeCouponDecorator(new PercentageCouponDecorator(product, 20), 10);
        productList.add(productWithEligibleDiscount);
    }

    public double getTotalPrice() {
        double totalPrice = 0;

        // productList contains decorated products
        for (Product product : productList) {
            totalPrice += product.getPrice(); // getPrice() will return the price after applying the coupons
        }

        return totalPrice;
    }
}