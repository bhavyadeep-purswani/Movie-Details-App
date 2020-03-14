package com.example.movieapp.View;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.Service.Model.ApiResponseField.SearchResult;
import com.example.movieapp.databinding.SearchListItemBinding;

import static com.example.movieapp.Service.Model.Constants.IMAGE_API_BASE_URL;

/*
 * Created by bhavyadeeppurswani on 2020-03-02.
 */
public class SearchResultsAdapterViewHolder extends RecyclerView.ViewHolder {

    private SearchListItemBinding binding;
    private ClickListener clickListener;

    public SearchResultsAdapterViewHolder(@NonNull View itemView, ClickListener clickListener) {
        super(itemView);
        binding = SearchListItemBinding.bind(itemView);
        this.clickListener = clickListener;
    }

    public void bind(SearchResult searchResult) {
        binding.itemText.setText(searchResult.getTitle());
        binding.item.setOnClickListener(v -> clickListener.onItemClicked(searchResult));
        Glide.with(binding.getRoot()).load(IMAGE_API_BASE_URL + searchResult.getPosterPath()).error(R.drawable.ic_no_image).placeholder(R.drawable.ic_no_image).into(binding.itemImage);
    }

    public interface ClickListener {
    	void onItemClicked(SearchResult searchResult);
    }
}
