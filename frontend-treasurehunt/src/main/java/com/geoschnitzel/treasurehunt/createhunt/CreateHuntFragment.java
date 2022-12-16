package com.geoschnitzel.treasurehunt.createhunt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geoschnitzel.treasurehunt.R;

public class CreateHuntFragment extends Fragment implements CreateHuntContract.View {
    private CreateHuntContract.Presenter mPresenter = null;

    public static CreateHuntFragment newInstance() {
        return new CreateHuntFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_createhunt, container, false);
    }

    @Override
    public void setPresenter(CreateHuntContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
