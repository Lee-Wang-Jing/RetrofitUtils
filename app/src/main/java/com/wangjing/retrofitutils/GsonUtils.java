package com.wangjing.retrofitutils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class GsonUtils {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtils() {
    }

    /**
     * 对象转成jsonString
     *
     * @param object 对象
     * @return String
     */
    public static String gsonObjectToString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }


    /**
     * String转成bean
     *
     * @param gsonString String
     * @param cls        Class
     * @return bean对象
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * JsonString转成list
     *
     * @param gsonString String
     * @param type       Type
     *                   new TypeToken<List<T>>() {}.getType()
     * @return List数组
     */
    public static <T> List<T> gsonToList(String gsonString, Type type) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,type);
        }
        return list;
    }

    /**
     * JsonString转成JsonObject
     *
     * @param json String
     * @return JSONObject
     */
    public static JsonObject gsonToJsonObject(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        return jsonObject;
    }

    /**
     * JsonString转成JsonObject
     *
     * @param json Reader
     * @return JSONObject
     */
    public static JsonObject gsonToJsonObject(JsonReader json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        return jsonObject;
    }

    /**
     * String转成list中有map的
     *
     * @param gsonString String
     * @return List<Map               <               String               ,                               T>>
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * String转成Map的
     *
     * @param gsonString String
     * @return Map<String               ,                               T>
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

}
