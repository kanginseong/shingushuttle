package com.example.terms_of_use;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Terms_Of_UseRequest extends StringRequest {

    // php파일 연동
    final static private String URL = "http://101.101.218.76/devices.php";

    private Map<String, String> map;

    public Terms_Of_UseRequest(String devices_id, String devices_agree_YN, Response.Listener<String> listener){

        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("devices_id", devices_id);
        map.put("devices_agree_YN", devices_agree_YN);

    }

    @Override
    protected Map<String, String> getParams() {

        return map;
    }
}
