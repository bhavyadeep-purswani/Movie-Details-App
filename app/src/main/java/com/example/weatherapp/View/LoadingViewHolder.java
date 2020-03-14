package com.example.weatherapp.View;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.databinding.LoadingViewItemBinding;

/*
 * Created by bhavyadeeppurswani on 14/03/20.
 */
public class LoadingViewHolder extends RecyclerView.ViewHolder {
	private LoadingViewItemBinding binding;
	public LoadingViewHolder(@NonNull View itemView) {
		super(itemView);
		binding = LoadingViewItemBinding.bind(itemView);
	}
}
