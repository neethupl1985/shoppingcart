package com.disco.shoppingcart.utils;

import com.disco.shoppingcart.user.model.User;
import com.disco.shoppingcart.userCart.dto.UserCart;
import com.disco.shoppingcart.userCart.model.Summary;

import java.util.*;

import static java.util.Comparator.comparingDouble;

public final class DiscountCalculator {

    public static Summary summary(List<UserCart> userCart, User user) {

        double totalPrice = 0;
        double maxDiscount = 0;
        double totalPriceForOneSetOfItems = 0;

        Map<FURNITURE, Integer> discountMap = new HashMap<>();

        for (UserCart cart : userCart) {
            int itemCount = cart.getItemCount();
            totalPrice += cart.getItemPrice();
            totalPriceForOneSetOfItems +=  cart.getItemPrice()/itemCount;
            if (cart.getItemType().equals("CH")) {
                discountMap.put(FURNITURE.CHAIR, itemCount);
                //this has the final chair discount
                if (itemCount >= 4) {
                    maxDiscount = Math.max(maxDiscount, cart.getItemPrice() * 20 / 100);
                }
            } else if (cart.getItemType().equals(FURNITURE.COUCH.getValue())) {
                discountMap.put(FURNITURE.COUCH, itemCount);
            } else if (cart.getItemType().equals(FURNITURE.TABLE.getValue())) {
                discountMap.put(FURNITURE.TABLE, itemCount);
            } else if (cart.getItemType().equals(FURNITURE.DESK.getValue())) {
                discountMap.put(FURNITURE.DESK, itemCount);
            }
        }
        if (discountMap.size() == 4) {
            Integer minCountOfAllItems =
                    Collections.min(discountMap.entrySet(), comparingDouble(Map.Entry::getValue)).getValue();
            maxDiscount = Math.max(maxDiscount, totalPriceForOneSetOfItems * minCountOfAllItems * 17 / 100);
        }
        if (totalPrice > 1000) {
            maxDiscount = Math.max(maxDiscount, totalPrice * 15 /100);
        }
        return Summary.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .discountsApplied(maxDiscount)
                .totalPriceBeforeDiscount(totalPrice)
                .finalPrice(totalPrice - maxDiscount).build();


    }

}
