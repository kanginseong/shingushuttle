package com.example.terms_of_use;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Terms_Of_Use_CheckRequest extends StringRequest {

    // php파일 연동
    final static private String URL = "http://101.101.218.76/devices_check.php";

    private Map<String, String> map;

    public Terms_Of_Use_CheckRequest(String devices_id, Response.Listener<String> listener){

        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("devices_id", devices_id);

    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
