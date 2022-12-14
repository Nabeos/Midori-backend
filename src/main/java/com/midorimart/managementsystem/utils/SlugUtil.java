package com.midorimart.managementsystem.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugUtil {
    // Create Slug from title and sku
    public static String getSlug(String productName, String sku){
        return deAccent(productName).toLowerCase().replaceAll("\\s+", "-")+"--"+sku;
    }

    // Remove vietnamese
    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
