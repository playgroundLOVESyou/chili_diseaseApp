package com.example.chili_diseaseapp.weather;

public class weather {

    private int id;
    private String main;
    private String description;
    private String icon;

    private String sys;

    public weather(int id, String main, String description, String icon, String sys) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }
}
