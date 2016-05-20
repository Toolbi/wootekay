package com.tukkeendoo.app.models;

import com.tukkeendoo.app.config.Tukkeendoo;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.Webservice;
import com.tukkeendoo.app.view.Base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fallou on 11/05/2016.
 */
public class User {
    private String token;

    public static void loginUser(BaseActivity context, String userName, String password){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("email", userName);
        parameters.put("password", password);
        parameters.put("remember", true);
        parameters.put("redirect", "home");
        parameters.put("submitted", true);
        HTTPRequest request = new HTTPRequest(Tukkeendoo.TEST_LOGIN_URL,HTTPRequest.POST, parameters);
        Webservice.executeRequestWithListener(request, context);
    }
}
