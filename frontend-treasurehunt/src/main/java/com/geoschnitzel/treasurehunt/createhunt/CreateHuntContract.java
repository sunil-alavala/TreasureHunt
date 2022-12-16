package com.geoschnitzel.treasurehunt.createhunt;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;

public interface CreateHuntContract {

    interface View extends IBaseView<CreateHuntContract.Presenter> {
    }

    interface Presenter extends IBasePresenter {
    }
}
