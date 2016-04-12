package com.fragilebytes.asos.services.observables;


import com.fragilebytes.asos.handler.Constants;
import com.fragilebytes.asos.models.btn.MenuModel;
import com.fragilebytes.asos.models.category.CatalogModel;
import com.fragilebytes.asos.models.product.ProductModel;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Windows on 01/04/2016.
 */
public interface API {
    @GET(Constants.SHOP_WOMEN_URL)
    Observable<MenuModel> getWomens();

    @GET(Constants.SHOP_MEN_URL)
    Observable<MenuModel> getMens();

    @GET(Constants.CATEGORIES_URL)
    Observable<CatalogModel> getCategories(@Query("id") String id);

    @GET(Constants.DETAILS_URL)
    Observable<ProductModel> getProductDetails(@Query("id") String id);
}
