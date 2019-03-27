package haiphat.tech.support.customers.Utills;

import com.google.gson.Gson;

public class GsonUtils {
    /**
     *
     * @param json
     * @param returnType
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> returnType) {
        T ret = null;
        Gson gson = new Gson();
        if(json != null) {
            try {
                ret = gson.fromJson(json, returnType);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * create json string from an object
     * @param pObject
     * @return
     */
    public static String toJson(Object pObject){
        String ret = null;
        Gson gson = new Gson();
        try {
            ret = gson.toJson(pObject);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ret;
    }
}