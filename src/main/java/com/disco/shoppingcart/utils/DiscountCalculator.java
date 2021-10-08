package com.disco.shoppingcart.utils;

import com.disco.shoppingcart.user.model.User;
import com.disco.shoppingcart.userCart.dto.UserCart;
import com.disco.shoppingcart.userCart.model.Summary;

import java.util.*;

import static java.util.Comparator.comparingDouble;

public final class DiscountCalculator {

    public static Summary summary(List<UserCart> userCart, User user) {

        double totalPrice = 0;
        SortedSet<Double> maxDiscount = new TreeSet<>();
        Map<FURNITURE, Integer> discountMap = new HashMap<>();

        for (UserCart cart : userCart) {
            int itemCount = cart.getItemCount();
            totalPrice += cart.getItemPrice();
            if (cart.getItemType().equals("CH")) {
                discountMap.put(FURNITURE.CHAIR, itemCount);
                if (itemCount >= 4) {
                    maxDiscount.add(20.0);
                }
            } else if (cart.getItemType().equals(FURNITURE.COUCH.toString())) {
                discountMap.put(FURNITURE.COUCH, itemCount);
            } else if (cart.getItemType().equals(FURNITURE.TABLE.toString())) {
                discountMap.put(FURNITURE.TABLE, itemCount);
            } else if (cart.getItemType().equals(FURNITURE.DESK.toString())) {
                discountMap.put(FURNITURE.DESK, itemCount);
            }
        }
        if (!discountMap.containsValue(0) && discountMap.size() == 4) {
            Integer minCountOfAllItems =
                    Collections.min(discountMap.entrySet(), comparingDouble(Map.Entry::getValue)).getValue();
            maxDiscount.add(minCountOfAllItems * 17.0);
        }
        if (totalPrice > 1000) {
            maxDiscount.add(15.0);
        }
        return Summary.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .discountsApplied(maxDiscount.last())
                .totalPriceBeforeDiscount(totalPrice)
                .finalPrice(totalPrice - (totalPrice * (maxDiscount.last() / 100))).build();


    }

}
