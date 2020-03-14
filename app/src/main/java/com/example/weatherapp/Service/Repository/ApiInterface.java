package com.example.weatherapp.Service.Repository;


import com.example.weatherapp.Service.Model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
 * Created by bhavyadeeppurswani on 2020-03-02.
 */

public interface ApiInterface {
    @GET("search/movie")
    Call<SearchResponse> getSearchResults(@Query("api_key") String apiKey, @Query("query") String searchString);

	@GET("search/movie")
	Call<SearchResponse> getSearchResultsPage(@Query("api_key") String apiKey, @Query("query") String searchString, @Query("page") int pageNo);
}
