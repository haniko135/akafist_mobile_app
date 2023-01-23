package com.example.akafist.models;

public class SkypesConfs {
    private int id;
    private String name;
    private String url;

    public SkypesConfs(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public SkypesConfs(int id, String name) {
        this.id = id;
        this.name = name;
        this.url = null;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public boolean isUrl(){
        if(url != null)
            return true;
        return false;
    }
}
