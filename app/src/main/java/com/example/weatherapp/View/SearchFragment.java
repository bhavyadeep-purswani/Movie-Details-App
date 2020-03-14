package com.example.weatherapp.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weatherapp.R;
import com.example.weatherapp.Service.Model.ApiResponseField.SearchResult;
import com.example.weatherapp.Service.Model.SearchResponse;
import com.example.weatherapp.ViewModel.SearchViewModel;
import com.example.weatherapp.databinding.FragmentSearchBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/*
 * Created by bhavyadeeppurswani on 2020-03-02.
 */
public class SearchFragment extends Fragment implements SearchResultsAdapterViewHolder.ClickListener {

    private FragmentSearchBinding binding;
    private SearchResultsAdapter adapter;
    private SearchViewModel searchViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        init();
        return binding.getRoot();
    }

    private void init() {
        initRecyclerView();
        setDataObserver();
        setTextChangeListener();
    }

    @Override
    public void onDestroy() {
        adapter.setSearchResults(null);
        super.onDestroy();
    }

    private void initRecyclerView() {
        adapter = new SearchResultsAdapter(this);
        binding.searchResults.setHasFixedSize(true);
        binding.searchResults.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.searchResults.setAdapter(adapter);
    }

    private void setDataObserver() {
        searchViewModel.getSearchResponseLiveData()
                .observe(this, new Observer<SearchResponse>() {
                    @Override
                    public void onChanged(SearchResponse searchResponse) {
                        adapter.setSearchResults(searchResponse.getSearchResults());
                    }
                });
    }

    private void setTextChangeListener() {
        PublishSubject<String> subject = PublishSubject.create();
        binding.searchQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                subject.onNext(newText);
                return false;
            }
        });
        subject.debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (s.length() >= 3) {
                            searchViewModel.updateResults(s);
                        } else {
                            adapter.setSearchResults(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onItemClicked(SearchResult searchResult) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.searchFragmentContainer, new MovieDetailsFragment(searchResult));
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
    }
}
