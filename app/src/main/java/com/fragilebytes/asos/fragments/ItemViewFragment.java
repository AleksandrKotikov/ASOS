package com.fragilebytes.asos.fragments;

import android.app.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;


import com.fragilebytes.asos.MainPatch;
import com.fragilebytes.asos.R;
import com.fragilebytes.asos.adapters.SwipeAdapter;
import com.fragilebytes.asos.models.product.ProductModel;
import com.fragilebytes.asos.services.observables.API;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ItemViewFragment extends Fragment {

    SwipeAdapter swipeAdapter;

    @Bind(R.id.item_view) ViewPager viewPager;
    @Bind(R.id.itemViewTitle) TextView textViewTitle;
    @Bind(R.id.itemViewDescription) TextView textViewDescription;
    @Bind(R.id.btnAddToCart) Button btnAddToCart;

    @Inject
    API apiItemView;

    CompositeSubscription subscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_view_fragment, container, false);

        ButterKnife.bind(this, rootView);

        //==================================================================================================CREATE & SET SUBSCRIPTION
        subscription = new CompositeSubscription();
//==================================================================================================CALL FOR DATA

        String id = getArguments().getString("p_id");
        callData(id);
        return rootView;
    }

    void callData(String id) {
//==================================================================================================SHOW DIALOG WHILE FETCHING DATA
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Loading", "Please wait...", false, false);
//==================================================================================================INJECT RETROFIT AND API COMPONENTS
        ((MainPatch) getActivity().getApplication()).getApiItemView().inject(this);
//==================================================================================================SET SUBSCRIBERS & OBSERVERS
        subscription.add(apiItemView.getProductDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .subscribe(new Observer<ProductModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final ProductModel productModel) {
                        textViewTitle.setText(productModel.getTitle());
                        textViewDescription.setText(productModel.getDescription());
                        btnAddToCart.setText(btnAddToCart.getText()+ " " + productModel.getCurrentPrice());
                        swipeAdapter = new SwipeAdapter(productModel, getActivity());
                        viewPager.setAdapter(swipeAdapter);
                        loading.dismiss();
                        }
                }));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
