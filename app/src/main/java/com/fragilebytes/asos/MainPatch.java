package com.fragilebytes.asos;

import android.app.Application;

import com.fragilebytes.asos.handler.Constants;
import com.fragilebytes.asos.modules.APIModule;
import com.fragilebytes.asos.modules.AppModule;
import com.fragilebytes.asos.modules.NetModule;
import com.fragilebytes.asos.services.components.APIComListing;
import com.fragilebytes.asos.services.components.APIComSideMenu;

import com.fragilebytes.asos.services.components.APIItemView;
import com.fragilebytes.asos.services.components.DaggerAPIComListing;
import com.fragilebytes.asos.services.components.DaggerAPIComSideMenu;
import com.fragilebytes.asos.services.components.DaggerAPIItemView;
import com.fragilebytes.asos.services.components.DaggerNetComponent;
import com.fragilebytes.asos.services.components.NetComponent;

/**
 * Created by Windows on 10/04/2016.
 */
public class MainPatch extends Application{
    private NetComponent netComponent;
    private APIComSideMenu apiComSideMenu;
    private APIComListing apiComListing;
    private APIItemView apiItemView;
    @Override
    public void onCreate() {
        super.onCreate();
        netComponent= DaggerNetComponent.builder()
                .netModule(new NetModule(Constants.BASE_URL))
                .appModule(new AppModule(this))
                .build();

        apiComSideMenu = DaggerAPIComSideMenu.builder()
                .netComponent(netComponent)
                .aPIModule(new APIModule())
                .build();

        apiComListing = DaggerAPIComListing.builder()
                .netComponent(netComponent)
                .aPIModule(new APIModule())
                .build();

        apiItemView = DaggerAPIItemView.builder()
                .netComponent(netComponent)
                .aPIModule(new APIModule())
                .build();
    }

    public APIComListing getApiComListing() {
        return apiComListing;
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public APIComSideMenu getApiComponent() {
        return apiComSideMenu;
    }

    public APIItemView getApiItemView() {
        return apiItemView;
    }

}

