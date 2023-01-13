package com.example.akafist.models;

public class SkypesConfs {
    private String blockName;
    private String skypeLink;

    public SkypesConfs(String blockName, String skypeLink) {
        this.blockName = blockName;
        this.skypeLink = skypeLink;
    }

    public String getBlockName() {
        return blockName;
    }

    public String getSkypeLink() {
        return skypeLink;
    }
}
