package com.example.weatherapp.Service.Model;

import com.example.weatherapp.Service.Model.ApiResponseField.SearchResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * Created by bhavyadeeppurswani on 2020-03-02.
 */
public class SearchResponse {
    @SerializedName("results")
    private List<SearchResult> searchResults;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

	@SerializedName("page")
	private int currentPage;

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
