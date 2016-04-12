package com.fragilebytes.asos.modules;


import com.fragilebytes.asos.scopes.UserScope;
import com.fragilebytes.asos.services.observables.API;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module
public class APIModule {

    @Provides
    @UserScope
    public API providesIContactsInterface(RestAdapter retrofit) {
        return retrofit.create(API.class);
    }
}
