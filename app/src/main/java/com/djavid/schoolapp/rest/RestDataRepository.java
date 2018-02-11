package com.djavid.schoolapp.rest;

import com.djavid.schoolapp.App;


public class RestDataRepository {

    private Api apiInterface;


    public RestDataRepository(Api apiInterface) {
        this.apiInterface = apiInterface;
    }

    public RestDataRepository() {
        this(App.getAppInstance().getApi());
    }


}
