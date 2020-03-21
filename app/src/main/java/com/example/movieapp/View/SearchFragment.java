package com.example.movieapp.View;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.Service.Model.ApiResponseField.SearchResult;
import com.example.movieapp.ViewModel.SearchViewModel;
import com.example.movieapp.databinding.FragmentSearchBinding;

import io.reactivex.subjects.PublishSubject;

/*
 * Created by bhavyadeeppurswani on 2020-03-02.
 */
public class SearchFragment extends Fragment implements SearchResultsAdapterViewHolder.ClickListener {

    private FragmentSearchBinding binding;
    private SearchResultsAdapter adapter;
    private SearchViewModel searchViewModel;
    private boolean isLoading = false;

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
        initAdapter();
        setDataObserver();
        setTextChangeListener();
    }

    @Override
    public void onDestroy() {
        searchViewModel.clearData();
        super.onDestroy();
    }

    private void initRecyclerView() {
        adapter = new SearchResultsAdapter(this);
        binding.searchResults.setHasFixedSize(true);
        binding.searchResults.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.searchResults.setAdapter(adapter);
    }

    private void initAdapter() {
    	binding.searchResults.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
				if (!isLoading) {
					if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1) {
						loadNextPage();
						isLoading = true;
					}
				}
			}
		});
	}

    private void setDataObserver() {
        searchViewModel.getSearchResponseLiveData()
                .observe(getViewLifecycleOwner(), searchResponse -> {
                	if (searchResponse != null) {
						adapter.setSearchResults(searchResponse.getSearchResults());
					} else {
                		adapter.setSearchResults(null);
					}
					isLoading = false;
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
        searchViewModel.monitorTextView(subject);
    }

    private void loadNextPage() {
    	if (searchViewModel.checkHasNextPage()) {
			adapter.addToSearchResults(null);
			adapter.notifyItemInserted(adapter.getItemCount() - 1);
			searchViewModel.loadNextPage();
		}
	}

    @Override
    public void onItemClicked(SearchResult searchResult) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.searchFragmentContainer, new MovieDetailsFragment(searchResult));
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
    }
}
