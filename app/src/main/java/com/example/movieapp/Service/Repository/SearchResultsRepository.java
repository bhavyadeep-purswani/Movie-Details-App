package com.example.movieapp.Service.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.Service.Model.ApiResponseField.SearchResult;
import com.example.movieapp.Service.Model.Constants;
import com.example.movieapp.Service.Model.SearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created by bhavyadeeppurswani on 2020-03-02.
 */
public class SearchResultsRepository {
    private ApiInterface searchService;
    private static SearchResultsRepository searchResultsRepository;
    private MutableLiveData<SearchResponse> searchResultMutableLiveData;
    private String searchQuery;
    private int currentPage = 0;
    private int totalPages = 0;

    private SearchResultsRepository() {
        searchService = ApiClient.getClient().create(ApiInterface.class);
        searchResultMutableLiveData = new MutableLiveData<>();
    }

    public static SearchResultsRepository getInstance() {
        if (searchResultsRepository == null) {
            searchResultsRepository = new SearchResultsRepository();
        }
        return searchResultsRepository;
    }

    public LiveData<SearchResponse> getSearchResultObservable() {
        return searchResultMutableLiveData;
    }

    public void updateSearchResults(String searchQuery) {
    	if (searchQuery.length() >= 3) {
			this.searchQuery = searchQuery;
			searchService.getSearchResults(Constants.API_KEY, searchQuery).enqueue(new Callback<SearchResponse>() {
				@Override
				public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
					currentPage = response.body().getCurrentPage();
					totalPages = response.body().getTotalPages();
					searchResultMutableLiveData.setValue(response.body());
				}

				@Override
				public void onFailure(Call<SearchResponse> call, Throwable t) {

				}
			});
		} else {
    		searchResultMutableLiveData.setValue(null);
		}
    }

    public void clearData() {
    	searchResultMutableLiveData.setValue(null);
	}

	public boolean hasNextPage() {
    	return currentPage < totalPages;
	}

	public void getNextPage() {
		currentPage++;
		searchService.getSearchResultsPage(Constants.API_KEY, searchQuery, currentPage).enqueue(new Callback<SearchResponse>() {
			@Override
			public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
				List<SearchResult> updatedResults = searchResultMutableLiveData.getValue().getSearchResults();
				updatedResults.addAll(response.body().getSearchResults());
				response.body().setSearchResults(updatedResults);
				searchResultMutableLiveData.setValue(response.body());
			}

			@Override
			public void onFailure(Call<SearchResponse> call, Throwable t) {

			}
		});
	}
}
