package api;

import com.github.javafaker.Faker;
import entity.User;
import org.json.JSONObject;

public  class ApiBody {

    public static JSONObject gerarBodyDefault(){
        return new User(
                Faker.instance().name().fullName(),
                "Male",
                Faker.instance().internet().emailAddress(),
                "Active")
                .getJson();
    }
}
