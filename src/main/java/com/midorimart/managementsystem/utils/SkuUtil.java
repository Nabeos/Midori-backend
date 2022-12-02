package com.midorimart.managementsystem.utils;

public class SkuUtil {
    public static String getSku(int categoryId, int productId) {
        String sku = "";
        if (categoryId < 10) {
            sku = "0" + categoryId;
        } else {
            sku = categoryId + "";
        }
        if (productId < 10) {
            sku += "000" + productId;
        } else if (productId < 100) {
            sku += "00" + productId;
        } else if (productId < 1000) {
            sku += "0" + productId;
        } else {
            sku += categoryId + "";
        }
        return sku;
    }
}
