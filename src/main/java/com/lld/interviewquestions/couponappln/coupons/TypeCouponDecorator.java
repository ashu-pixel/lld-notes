package com.lld.interviewquestions.couponappln.coupons;

import java.util.ArrayList;
import java.util.List;

import com.lld.interviewquestions.couponappln.product.Product;
import com.lld.interviewquestions.couponappln.product.ProductType;

public class TypeCouponDecorator extends CouponDecorator {

    static List<ProductType> eligibleTypes = new ArrayList<>();

    static {
        eligibleTypes.add(ProductType.FURNITURE);
        eligibleTypes.add(ProductType.ELECTRONICS);
    }

    public TypeCouponDecorator(Product product, int percentage) {
        super(product, percentage);
    }

    @Override
    public double getPrice() {
        double price = this.product.getPrice();
        if (eligibleTypes.contains(this.product.getType())) {
            double priceAfterDiscount = price - (price * discountPercentage) / 100;
            System.out.println("Applying specific product type coupon of " + discountPercentage + "% on " + product.getName() + ", original price : " + price + ", price after discount : " + priceAfterDiscount);
            return priceAfterDiscount;
        }
        return price;
    }
}