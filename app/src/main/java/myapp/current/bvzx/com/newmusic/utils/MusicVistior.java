package myapp.current.bvzx.com.newmusic.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import myapp.current.bvzx.com.newmusic.bean.MusicSet;


/**
 * Created by wuhao on 2016/1/4.
 */
public class MusicVistior {



    public static MusicSet getMusicSet(String keyword,int limit,int page){
        String json =getJson(keyword,limit,page);
        Gson gson=new Gson();
        Log.e("111", "11111" + json);
        return gson.fromJson(json,MusicSet.class);
    }



    public static String getJson(String keyword,int limit,int page) {
        String httpUrl = "http://apis.baidu.com/geekery/music/query";
        String httpArg = null;
        try {
            httpArg = "s="+ URLEncoder.encode(keyword, "UTF-8")+"&limit="+limit+"&p="+page+"";
            return request(httpUrl,httpArg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) throws UnsupportedEncodingException {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "2376e90a31f721b6fe4fe77b64a6ab19");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] arg){
        System.out.println(getJson("苏打绿",10,1));
    }

}
