package com.example.weatherapp.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.Service.Model.ApiResponseField.SearchResult;
import com.example.weatherapp.Service.Model.SearchResponse;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by bhavyadeeppurswani on 2020-03-02.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapterViewHolder> {

    private List<SearchResult> searchResults;
    private SearchResultsAdapterViewHolder.ClickListener clickListener;

    public SearchResultsAdapter(SearchResultsAdapterViewHolder.ClickListener clickListener) {
        this.clickListener = clickListener;
        searchResults = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearchResultsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.search_list_item, parent, false);
        return new SearchResultsAdapterViewHolder(listItem, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsAdapterViewHolder holder, int position) {
        holder.bind(searchResults.get(position));
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        if (searchResults == null) {
            this.searchResults.clear();
        } else {
            this.searchResults = searchResults;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }
}
