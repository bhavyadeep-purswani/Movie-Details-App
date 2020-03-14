package com.example.weatherapp.View;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.Service.Model.ApiResponseField.SearchResult;
import com.example.weatherapp.databinding.SearchListItemBinding;

import static com.example.weatherapp.Service.Model.Constants.IMAGE_API_BASE_URL;

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
        binding.itemText.setOnClickListener(v -> clickListener.onItemClicked(searchResult));
        Glide.with(binding.getRoot()).load(IMAGE_API_BASE_URL + searchResult.getPosterPath()).error(R.drawable.ic_no_image).placeholder(R.drawable.ic_no_image).into(binding.itemImage);
    }

    public interface ClickListener {
    	void onItemClicked(SearchResult searchResult);
    }
}
