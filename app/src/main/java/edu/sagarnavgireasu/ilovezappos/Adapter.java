package edu.sagarnavgireasu.ilovezappos;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.sagarnavgireasu.ilovezappos.databinding.ProductBinding;

/**
 * Created by sagarnavgire on 2/8/17.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ProductHolder>
{

    List<Result> results=new ArrayList<>();

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("oncreate","inside");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductBinding binding= DataBindingUtil.inflate(layoutInflater,R.layout.product,parent,false);
        Log.d("bind",binding+"");
        return new ProductHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        holder.bind(results.get(position));
    }

    public void  addItem(Result result)
    {
        this.results.add(result);
        Log.d("additem","inside");
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.results.size();
    }



    public class ProductHolder extends RecyclerView.ViewHolder{
        ProductBinding binding;

        public ProductHolder(View itemView,ProductBinding binding)
        {
            super(itemView);
            this.binding=binding;
        }

        public  void bind(Result result)
        {
            this.binding.setResult(result);
            this.binding.executePendingBindings();
        }

        public ProductBinding getBinding() {
            return binding;
        }

    }

    @BindingAdapter("bind:Url")
    public static void loadThumbnail(ImageView imageView,String s)
    {
        StringBuilder sb = new StringBuilder(s);
        sb.setCharAt(51,'p');

        sb.replace(53,61,"MULTIVIEW");
        sb.deleteCharAt(62);

        Picasso.with(imageView.getContext()).load(String.valueOf(sb)).into(imageView);
    }

}
