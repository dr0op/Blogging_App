package com.dailyraven.tradebrains;
/**
 * Created by Shubham.
 */
public class blogData {
    public String blogImage;
    public String blogTitle;
    public String blogtime;
    public String blogContent;
    public int blogId;

    public blogData() {
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public void setBlogImage(String blogImage) {
        this.blogImage = blogImage;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public void setBlogtime(String blogtime) {
        this.blogtime = blogtime;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogImage() {
        return blogImage;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public String getBlogtime() {
        return blogtime;
    }

    public int getBlogId() {
        return blogId;
    }
}
