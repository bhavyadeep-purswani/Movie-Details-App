package com.example.weatherapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weatherapp.Service.Model.SearchResponse;
import com.example.weatherapp.Service.Repository.SearchResultsRepository;

/*
 * Created by bhavyadeeppurswani on 2020-03-02.
 */
public class SearchViewModel extends AndroidViewModel {

    private final LiveData<SearchResponse> searchResponseLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        searchResponseLiveData = SearchResultsRepository.getInstance().getSearchResultObservable();
    }

    public LiveData<SearchResponse> getSearchResponseLiveData() {
        return searchResponseLiveData;
    }

    public void updateResults(String searchQuery) {
        SearchResultsRepository.getInstance().updateSearchResults(searchQuery);
    }

}
