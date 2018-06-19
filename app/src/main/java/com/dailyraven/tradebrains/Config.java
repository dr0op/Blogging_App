package com.dailyraven.tradebrains;
/*
Created by Shubham
 */
public class Config {



    //Data URL
    public static final String DATA_URL = "http://www.tradebrains.in/wp-json/wp/v2/posts/?filter[posts_per_page]=10&fields=id,date,content,title,better_featured_image.source_url&page=";
    public static final String Data_fetch="http://www.tradebrains.in/wp-json/wp/v2/posts/\"+id+\"?fields=title,content,better_featured_image.source_url";
}
