package com.example.movieapp.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.Service.Model.ApiResponseField.SearchResult;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by bhavyadeeppurswani on 2020-03-02.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchResult> searchResults;
    private SearchResultsAdapterViewHolder.ClickListener clickListener;
    private final int VIEW_TYPE_LOADING = 0;
    private final int VIEW_TYPE_ITEM = 1;


    public SearchResultsAdapter(SearchResultsAdapterViewHolder.ClickListener clickListener) {
        this.clickListener = clickListener;
        searchResults = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_LOADING) {
			View listItem = layoutInflater.inflate(R.layout.loading_view_item, parent, false);
			return new LoadingViewHolder(listItem);
		} else {
			View listItem = layoutInflater.inflate(R.layout.search_list_item, parent, false);
			return new SearchResultsAdapterViewHolder(listItem, clickListener);
		}
    }


	@Override
	public int getItemViewType(int position) {
		return searchResults.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
	}

	@Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    	if (holder instanceof SearchResultsAdapterViewHolder) {
			((SearchResultsAdapterViewHolder)holder).bind(searchResults.get(position));
		}
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        if (searchResults == null) {
            this.searchResults.clear();
        } else {
            this.searchResults = new ArrayList<>(searchResults);
        }
        notifyDataSetChanged();
    }

    public void addToSearchResults(SearchResult searchResult) {
    	this.searchResults.add(searchResult);
	}

    @Override
    public int getItemCount() {
        return searchResults.size();
    }
}
