package com.fragilebytes.asos.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fragilebytes.asos.MainPatch;
import com.fragilebytes.asos.R;
import com.fragilebytes.asos.adapters.ListingRecyclerViewAdapter;

import com.fragilebytes.asos.handler.RxUtils;

import com.fragilebytes.asos.models.category.CatalogModel;
import com.fragilebytes.asos.services.ClickListener;
import com.fragilebytes.asos.services.OnDataSend;
import com.fragilebytes.asos.services.observables.API;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ListingFragment extends Fragment {

    Activity activity;


    @Inject
    API apiListing;

    @Bind(R.id.list)
    RecyclerView listingRecyclerView;
    ListingRecyclerViewAdapter listingRecyclerViewAdapter;

    CompositeSubscription subscription;

    OnDataSend onDataSend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listing_fragment, container, false);
//==================================================================================================CREATE & SET RECYCLER VIEW
        ButterKnife.bind(this, view);

        listingRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        listingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        listingRecyclerView.setHasFixedSize(false);
//==================================================================================================CREATE & SET SUBSCRIPTION
        subscription = new CompositeSubscription();
//==================================================================================================CALL FOR DATA
        String id = getArguments().getString("id");

        callData(id);
        return view;
    }

    void callData(String id) {
//==================================================================================================SHOW DIALOG WHILE FETCHING DATA
        //final ProgressDialog loading = ProgressDialog.show(getContext(), "Loading", "Please wait...", false, false);
//==================================================================================================INJECT RETROFIT AND API COMPONENTS
        ((MainPatch) getActivity().getApplication()).getApiComListing().inject(this);
//==================================================================================================SET SUBSCRIBERS & OBSERVERS
        subscription.add(apiListing.getCategories(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .subscribe(new Observer<CatalogModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final CatalogModel catalogModel) {
                        if (catalogModel.getItemCount() > 0 && listingRecyclerView != null) {

                            listingRecyclerViewAdapter = new ListingRecyclerViewAdapter(catalogModel, R.layout.listing_recycler_row, getActivity());
                            listingRecyclerView.setAdapter(listingRecyclerViewAdapter);
                            //loading.dismiss();
                            listingRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), listingRecyclerView, new ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    onDataSend.onSendId(catalogModel.getListings().get(position).getProductId().toString(), 2);
                                    Log.i("LISTING FRAGMENT",catalogModel.getListings().get(position).getProductId().toString());
                                }

                                @Override
                                public void onLongClick(View view, int position) {


                                }
                            }));
                        }
                    }
                }));
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

