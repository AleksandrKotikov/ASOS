package com.fragilebytes.asos.services.components;

import com.fragilebytes.asos.fragments.ItemViewFragment;
import com.fragilebytes.asos.fragments.ListingFragment;
import com.fragilebytes.asos.modules.APIModule;
import com.fragilebytes.asos.scopes.UserScope;

import dagger.Component;

@UserScope
@Component(dependencies = NetComponent.class, modules = APIModule.class)
public interface APIItemView {

void inject(ItemViewFragment activity);

}
