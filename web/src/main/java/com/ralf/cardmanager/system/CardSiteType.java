package com.ralf.cardmanager.system;

public enum CardSiteType {

    AMERICA_CARD("AmercicaCard", "美卡"), HONGKONG_CARD("hongKongCard", "港卡");

    private String name;

    private String nameZh;

    CardSiteType(String name, String nameZh) {
        this.name = name;
        this.nameZh = nameZh;
    }


}
