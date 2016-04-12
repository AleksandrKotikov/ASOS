package com.fragilebytes.asos.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import com.fragilebytes.asos.MainPatch;
import com.fragilebytes.asos.R;
import com.fragilebytes.asos.adapters.SideMenuAdapter;
import com.fragilebytes.asos.handler.RxUtils;
import com.fragilebytes.asos.models.btn.MenuModel;
import com.fragilebytes.asos.services.ClickListener;
import com.fragilebytes.asos.services.OnDataSend;
import com.fragilebytes.asos.services.observables.API;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class SideMenuFragment extends Fragment {

    @Inject
    API apiMenu;

    CompositeSubscription subscription;

    @Bind(R.id.sideMenuList) RecyclerView sideMenuRecyclerView;
    @Bind(R.id.btnShopMen) Button btnShopMen;
    @Bind(R.id.btnShopWomen) Button btnShopWomen;



    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private SideMenuAdapter sideMenuAdapter;
    private View containerView;
    OnDataSend onDataSend;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.side_menu_fragment, container, false);

//==================================================================================================BIND VIEW
        ButterKnife.bind(this, view);
//==================================================================================================BUTTONS CLICK LISTENER
        btnShopMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopMenPressed();
            }
        });
        btnShopWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopWomenPressed();
            }
        });
//==================================================================================================CREATE & SET LAYOUT MANAGER

        sideMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//==================================================================================================CREATE & SET SUBSCRIPTION
        subscription = new CompositeSubscription();
//==================================================================================================CALL FOR DATA, SET ADAPTER & CLICK LISTENER

//LOAD DEFAULT SIDE MENU ELEMENTS
        shopWomenPressed();

        return view;
    }

    void shopWomenPressed() {
//==================================================================================================SHOW DIALOG WHILE FETCHING DATA
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Loading", "Please wait...", false, false);
//==================================================================================================INJECT RETROFIT AND API COMPONENTS
        ((MainPatch) getActivity().getApplication()).getApiComponent().inject(this);
//==================================================================================================SET SUBSCRIBERS & OBSERVERS
        subscription.add(apiMenu.getWomens()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .subscribe(new Observer<MenuModel>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "on Error");

                    }

                    @Override
                    public void onNext(final MenuModel menuModel) {
                        Log.i("Retrofit", "onNext");
                        if (menuModel.getListing().size() > 0 && sideMenuRecyclerView != null) {
//==================================================================================================SET ADAPTER TO RECYCLER VIEW
                            sideMenuAdapter = new SideMenuAdapter(getActivity(), menuModel);
                            sideMenuRecyclerView.setAdapter(sideMenuAdapter);
                            loading.dismiss();
//==================================================================================================SET CLICK LISTENER
                            sideMenuRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), sideMenuRecyclerView, new ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    onDataSend.onSendId(menuModel.getListing().get(position).getCategoryId(), 1);
                                    Log.i("SIDE MENU FRAGMENT, SHOP WOMEN", menuModel.getListing().get(position).getCategoryId());
                                    mDrawerLayout.closeDrawer(containerView);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));

                        }
                    }
                }));
    }

    public void shopMenPressed(){
//==================================================================================================SHOW DIALOG WHILE FETCHING DATA
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Loading", "Please wait...", false, false);
//==================================================================================================INJECT RETROFIT AND API COMPONENTS
        ((MainPatch) getActivity().getApplication()).getApiComponent().inject(this);
//==================================================================================================SET SUBSCRIBERS & OBSERVERS
        subscription.add(apiMenu.getMens()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .subscribe(new Observer<MenuModel>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "on Error");

                    }

                    @Override
                    public void onNext(final MenuModel menuModel) {
                        Log.i("Retrofit", "onNext");

                        if (menuModel.getListing().size() > 0 && sideMenuRecyclerView != null) {
//==================================================================================================SET ADAPTER TO RECYCLER VIEW
                            sideMenuAdapter = new SideMenuAdapter(getActivity(), menuModel);
                            sideMenuRecyclerView.setAdapter(sideMenuAdapter);
                            loading.dismiss();
//==================================================================================================SET CLICK LISTENER (NOT THE BEST PLACE FOR DOING THIS...)
                            sideMenuRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), sideMenuRecyclerView, new ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    onDataSend.onSendId(menuModel.getListing().get(position).getCategoryId(), 1);
                                    mDrawerLayout.closeDrawer(containerView);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                        }
                    }
                }));
    }

//SETTING UP SIDE MENU
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        subscription = RxUtils.getNewCompositeSubIfUnsubscribed(subscription);
    }

    @Override
    public void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(subscription);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        onDataSend = (OnDataSend) a;
    }
}
