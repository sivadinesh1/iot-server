package com.tvs.iot.util;

public class ImageMeta {
    private String title;
    private String value;

    public ImageMeta(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
