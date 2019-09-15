package com.ralf.cardmanager.system;

public enum CardSiteType {

    AMERICA_CARD("divvy","AmercicaCard", "美卡"), HONGKONG_CARD("yili","hongKongCard", "港卡");

    private String key;

    private String name;

    private String nameZh;

    CardSiteType(String key,String name, String nameZh) {
        this.key = key;
        this.name = name;
        this.nameZh = nameZh;
    }


}
