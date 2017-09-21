package com.aperise.model;

public class MenuItemImpl implements MenuItem {
    private Long id;
    private String name;
    private String url;


    public MenuItemImpl(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
