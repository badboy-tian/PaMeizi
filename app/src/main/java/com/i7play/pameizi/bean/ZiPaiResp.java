package com.i7play.pameizi.bean;

import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

public class ZiPaiResp {
    @Selector("li.comment")
    public List<Item> items;
    @Selector("span.current")
    public int pageNum;

    public static class Item{
        @Selector(value = "p img", attr = "src")
        public String imgUrl;
        @Selector("div.commentmetadata")
        public String time;

        @Override
        public String toString() {
            return "Item{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ZiPaiResp{" +
                "items=" + items +
                ", pageNum=" + pageNum +
                '}';
    }
}
