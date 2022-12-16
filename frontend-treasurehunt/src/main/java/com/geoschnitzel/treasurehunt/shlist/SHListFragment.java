package com.geoschnitzel.treasurehunt.shlist;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.utils.BottomSheetListView;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class SHListFragment extends BottomSheetDialogFragment implements SHListContract.View, View.OnClickListener {
    private SHListContract.Presenter mPresenter = null;
    public BottomSheetBehavior mBottomSheetBehavior = null;
    private FloatingActionButton mAddFab = null;
    private BottomSheetListView mSHList = null;
    private LinearLayout mFilterInfo = null;
    private ImageView mSHListArrowUp = null;
    private boolean wasCompletelyExpanded = false;

    public static SHListFragment newInstance() {
        return new SHListFragment();
    }

    @Override
    public void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_shlist, container, false);
        this.mSHList = root.findViewById(R.id.sh_list);
        this.mAddFab = getActivity().findViewById(R.id.floatingAddButton);
        this.mFilterInfo = root.findViewById(R.id.filter_info);
        this.mSHListArrowUp = root.findViewById(R.id.sh_list_arrow_up);
        this.mSHListArrowUp.setImageResource(R.drawable.avd_arrow_up);
        this.mFilterInfo.setAlpha(1.0f);
        final int mFilterInfoHeight = (int) getResources().getDimension(R.dimen.bottom_sheet_height_collapsed);

        mPresenter.retrieveSHListItems();
        this.refreshSHListAdapter(new ArrayList<>());

        this.mBottomSheetBehavior = BottomSheetBehavior.from(root.findViewById(R.id.main_sh_list_fragment));

        if (this.mBottomSheetBehavior != null) {
            this.mBottomSheetBehavior.setHideable(false);
            this.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            this.mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull final View bottomSheet, final int newState) {
                    getActivity().invalidateOptionsMenu();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_COLLAPSED:
                                if (wasCompletelyExpanded) {
                                    AnimatedVectorDrawable arrow_down = (AnimatedVectorDrawable) mSHListArrowUp.getDrawable();

                                    arrow_down.registerAnimationCallback(new Animatable2.AnimationCallback() {
                                        @Override
                                        public void onAnimationEnd(Drawable drawable) {
                                            mSHListArrowUp.setImageResource(R.drawable.avd_arrow_up);
                                            wasCompletelyExpanded = false;
                                        }
                                    });
                                    arrow_down.start();
                                }
                                break;
                            case BottomSheetBehavior.STATE_EXPANDED:
                                wasCompletelyExpanded = true;
                                mSHListArrowUp.setImageResource(R.drawable.avd_arrow_down);
                                break;
                            default:
                                break;
                        }
                    }
                }

                @Override
                public void onSlide(@NonNull final View bottomSheet, final float slideOffset) {

                    final float scaleFactor = 1 - slideOffset;
                    if (mAddFab != null && scaleFactor <= 1) {
                        mAddFab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
                    }

                    if (mFilterInfo != null) {
                        if (scaleFactor <= 1) {
                            mFilterInfo.getLayoutParams().height = mFilterInfoHeight - (int) (slideOffset * mFilterInfoHeight);
                            mFilterInfo.requestLayout();
                        }
                    }
                }
            });
        }

        this.mFilterInfo.setOnClickListener(this);
        return root;
    }

    public void closeBottomSheet() {
        this.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shlist, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void setPresenter(SHListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        this.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void refreshSHListAdapter(List<SHListItem> items) {

        SHListAdapter adpater = new SHListAdapter(items, getActivity().getApplicationContext());
        mSHList.setAdapter(adpater);
        mSHList.setOnItemClickListener(adpater);
    }
}
