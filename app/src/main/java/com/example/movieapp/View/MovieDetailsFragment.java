package com.example.movieapp.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.Service.Model.ApiResponseField.SearchResult;
import com.example.movieapp.Service.Model.Constants;
import com.example.movieapp.databinding.FragmentMovieDetailsBinding;

/*
 * Created by bhavyadeeppurswani on 14/03/20.
 */
public class MovieDetailsFragment extends Fragment {
	private FragmentMovieDetailsBinding binding;
	private SearchResult searchResult;

	public MovieDetailsFragment(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
		init();
		return binding.getRoot();
	}
	private void init() {
		binding.movieTitle.setText(searchResult.getTitle());
		binding.movieOverview.setText(searchResult.getOverview());
		binding.movieReleaseDate.setText(searchResult.getReleaseDate());
		Glide.with(this).load(Constants.IMAGE_API_BASE_URL + searchResult.getPosterPath()).placeholder(R.drawable.ic_no_image).error(R.drawable.ic_no_image).into(binding.moviePoster);
	}
}
