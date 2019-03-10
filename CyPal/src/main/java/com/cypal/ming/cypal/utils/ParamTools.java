package com.cypal.ming.cypal.utils;

import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.cypal.ming.cypal.config.Const;

/**
 * @author WuJianHua 参数配置工具类
 */
public class ParamTools {
	/** 生成参数 */
	public static StringRequest packParam(String url,
			Listener<String> listener, ErrorListener errorListener,
			final Map<String, String> map) {
		if (!url.contains("http")) {
			url = Const.BASE_URL + url;
		}
        map.put( "os", "android" );
        map.put( "version", "1001" );
		StringRequest stringRequest = new StringRequest(Method.POST, url,
				listener, errorListener) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return map;
			}
		};
		stringRequest.setCharset("UTF-8");
		stringRequest
				.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		return stringRequest;
	}
	/** 生成参数 */
	public static StringRequest packParam(String url,
										  Listener<String> listener, ErrorListener errorListener,
										  final Map<String, String> map,int method) {
		map.put( "os", "android" );
		map.put( "version", "1001" );
		if (!url.contains("http")) {
			url = Const.BASE_URL + url;
		}
		for (Map.Entry<String, String> entry : map.entrySet()) {
			url = url + "&" + entry.getKey() + "=" + entry.getValue();
		}
		StringRequest stringRequest = new StringRequest(method, url,
				listener, errorListener) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return map;
			}
		};
		stringRequest.setCharset("UTF-8");
		stringRequest
				.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		return stringRequest;
	}
	private static long lastClickTime;

	/**
	 * 
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

}
