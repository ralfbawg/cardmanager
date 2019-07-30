package com.ralf.cardmanager.spider.task;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
public class SiteBaseConfig {
    public Boolean shouldLogin = false;

    public volatile Boolean Logined = false;

    public volatile String loginUrl = "";

    public volatile String authToken = "";

    public String username;

    public String password;

    public Map requestHead = new HashMap<String,String>();

}
