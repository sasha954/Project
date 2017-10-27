package ua.nure.fomenko.final_project.util;

import org.json.JSONObject;

/**
 * Created by fomenko on 22.09.2017.
 */
public class JsonConverter {
    public static String objectToJson(Object object) {
        JSONObject jsonObject = new JSONObject(object);
        return jsonObject.toString();
    }
}
