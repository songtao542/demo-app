package com.aperise.model;

import java.util.List;

public interface Menu extends Iterable<MenuItem> {
    Long getId();

    String getName();

    String getUrl();

    void add(MenuItem item);

    List<? extends MenuItem> getItems();

    Integer size();
}
