package com.jaejoo.fitdomysql.domain.user.core;

public enum Tier {
    BRONZE, SILVER, GOLD, PLATINUM, DIAMOND;

    public static Tier calculateTier(long total, long rank) {
        double percentage = (double) rank / (double) total * 100;
        if (percentage >= 0 && percentage <= 20) {
            return DIAMOND;
        } else if (percentage > 20 && percentage <= 40) {
            return PLATINUM;
        } else if (percentage > 40 && percentage <= 60) {

            return GOLD;
        } else if (percentage > 60 && percentage <= 80) {
            return SILVER;
        } else if (percentage > 80 && percentage <= 100) {
            return BRONZE;
        }
        return BRONZE;
    }
}
