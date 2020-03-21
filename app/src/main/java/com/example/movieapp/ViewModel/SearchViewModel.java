package com.example.movieapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieapp.Service.Model.SearchResponse;
import com.example.movieapp.Service.Repository.SearchResultsRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/*
 * Created by bhavyadeeppurswani on 2020-03-02.
 */
public class SearchViewModel extends AndroidViewModel {

    private final LiveData<SearchResponse> searchResponseLiveData;
    private CompositeDisposable compositeDisposable;
    private SearchResultsRepository searchResultsRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
        searchResultsRepository = SearchResultsRepository.getInstance();
        searchResponseLiveData = SearchResultsRepository.getInstance().getSearchResultObservable();
    }

    public LiveData<SearchResponse> getSearchResponseLiveData() {
        return searchResponseLiveData;
    }

    public void loadNextPage() {
		searchResultsRepository.getNextPage();
	}

	public void monitorTextView(Subject<String> textViewSubject) {
		textViewSubject.debounce(300, TimeUnit.MILLISECONDS)
				.distinctUntilChanged()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new io.reactivex.Observer<String>() {
					@Override
					public void onSubscribe(Disposable d) {
						compositeDisposable.add(d);
					}

					@Override
					public void onNext(String s) {
						searchResultsRepository.updateSearchResults(s);
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onComplete() {

					}
				});
	}

	public boolean checkHasNextPage() {
    	return searchResultsRepository.hasNextPage();
	}

	public void clearData() {
		searchResultsRepository.clearData();
	}

	@Override
	protected void onCleared() {
		super.onCleared();
		compositeDisposable.dispose();
	}
}
