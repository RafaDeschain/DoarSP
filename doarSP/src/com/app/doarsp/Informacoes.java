package com.app.doarsp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.doarsp.R;

public class Informacoes extends Fragment {
	TextView versionInfo;

	public Informacoes() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_informacoes,
				container, false);
		return rootView;
	}
}