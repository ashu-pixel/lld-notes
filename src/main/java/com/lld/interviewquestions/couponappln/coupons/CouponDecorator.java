package com.lld.interviewquestions.couponappln.coupons;

import com.lld.interviewquestions.couponappln.product.Product;

public abstract class CouponDecorator extends Product {
    Product product;
    int discountPercentage;

    public CouponDecorator(Product product, int discountPercentage) {
        super(product.getName(), product.getPrice(), product.getType());
        this.product = product;
        this.discountPercentage = discountPercentage;
    }
   
}
