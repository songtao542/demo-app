package com.aperise.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class MenuImpl implements Menu {
    private Long id;
    private String name;
    private String url;
    private List<MenuItem> items;

    public MenuImpl(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Override
    public List<MenuItem> getItems() {
        return items;
    }

    @Override
    public Integer size() {
        if (items == null) {
            return 0;
        }
        return items.size();
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


    @Override
    public void add(MenuItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }

    @Override
    public Iterator<MenuItem> iterator() {
        if (items == null) {
            return new Iterator<MenuItem>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public MenuItem next() {
                    return null;
                }
            };
        }
        return items.iterator();
    }

    @Override
    public void forEach(Consumer<? super MenuItem> consumer) {
        if (items == null) {
            return;
        }
        items.forEach(consumer);
    }

    @Override
    public Spliterator<MenuItem> spliterator() {
        if (items == null) {
            return null;
        }
        return items.spliterator();
    }
}
