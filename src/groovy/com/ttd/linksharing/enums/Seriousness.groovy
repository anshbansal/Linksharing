package com.ttd.linksharing.enums

enum Seriousness {

    CASUAL(seriousness: "Casual"),
    SERIOUS(seriousness: "Serious"),
    VERY_SERIOUS(seriousness: "Very Serious");


    String seriousness

    @Override
    public String toString() {
        "$seriousness"
    }


}