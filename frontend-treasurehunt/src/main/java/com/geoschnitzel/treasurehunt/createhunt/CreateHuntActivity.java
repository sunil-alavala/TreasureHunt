package com.geoschnitzel.treasurehunt.createhunt;

import android.content.Intent;
import android.os.Bundle;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityWithBackButton;
import com.geoschnitzel.treasurehunt.main.MainActivity;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class CreateHuntActivity extends BaseActivityWithBackButton {
    private CreateHuntContract.Presenter mPresenter = null;
    private CreateHuntFragment mCreateHuntFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mCreateHuntFragment =
                (CreateHuntFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (this.mCreateHuntFragment == null) {
            // Create the fragment
            this.mCreateHuntFragment = CreateHuntFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), this.mCreateHuntFragment, R.id.contentFrame, getString(R.string.fragment_tag_create_sh));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }
}
