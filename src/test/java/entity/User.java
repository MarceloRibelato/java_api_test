package entity;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.XML;


public record User(
        String name,
        String gender,
        String email,
        String status
)
{
    public JSONObject getJson() {
        return new JSONObject(new Gson().toJson(this));
    }

    public String getXml() {
        return XML.toString(getJson());
    }
}
