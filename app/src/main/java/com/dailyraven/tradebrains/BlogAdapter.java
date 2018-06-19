package com.dailyraven.tradebrains;import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Shubham.
 */
public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder> {


    private Context mContext;
    private List<blogData> blogList;
    private int pid;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView blogTitle, blogTime;
        public ImageView blogImage;
        public int id;

        public MyViewHolder(View view) {
            super(view);
            blogTitle = view.findViewById(R.id.blogTitle);
            blogTime= view.findViewById(R.id.blogTime);
            blogImage = view.findViewById(R.id.blogImage);
        }
    }


    public BlogAdapter(Context mContext, List<blogData> blogList) {
        this.mContext = mContext;
        this.blogList = blogList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final blogData a = blogList.get(position);
        Spanned s1 = Html.fromHtml(a.getBlogTitle());
        holder.blogTitle.setText(s1.toString(), TextView.BufferType.SPANNABLE);
        holder.blogTime.setText(convertFormat(a.getBlogtime()));
        Glide.with(mContext).load(a.getBlogImage()).into(holder.blogImage);

        holder.blogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Toast.makeText(mContext, "clicked "+a.getBlogId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,blog.class);
                intent.putExtra("id", ""+a.getBlogId());
                intent.putExtra("title",""+a.getBlogTitle());
                intent.putExtra("content",""+a.getBlogContent());
                intent.putExtra("image",""+a.getBlogImage());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }
    private String convertFormat(String a){
        String yy= a.substring(0,4);
        String mm=a.substring(5,7);
        String dd=a.substring(8,10);
        String out="";
        String mm1="";
        if(mm.equalsIgnoreCase("01")){ mm1="January"; }
        else if(mm.equalsIgnoreCase("02")){ mm1="February"; }
        else if(mm.equalsIgnoreCase("03")){ mm1="March"; }
        else if(mm.equalsIgnoreCase("04")){ mm1="April"; }
        else if(mm.equalsIgnoreCase("05")){ mm1="May"; }
        else if(mm.equalsIgnoreCase("06")){ mm1="June"; }
        else if(mm.equalsIgnoreCase("07")){ mm1="July"; }
        else if(mm.equalsIgnoreCase("08")){ mm1="August"; }
        else if(mm.equalsIgnoreCase("09")){ mm1="September"; }
        else if(mm.equalsIgnoreCase("10")){ mm1="October"; }
        else if(mm.equalsIgnoreCase("11")){ mm1="November"; }
        else if(mm.equalsIgnoreCase("12")){ mm1="December"; }
        out=mm1+" "+dd+", "+yy;
        return out;
    }
}