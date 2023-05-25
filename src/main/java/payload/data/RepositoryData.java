package payload.data;

import com.github.javafaker.Faker;
import constants.Constants;
import org.json.simple.JSONObject;
import payload.pojo.RepositoryPayload;

public class RepositoryData {
    public static Faker faker = new Faker();
    static JSONObject requestParams = new JSONObject();
    static RepositoryPayload repositoryPojo= new RepositoryPayload();;
    public static JSONObject createRepoData(){
        requestParams.put(Constants.NAME, faker.name().username());
        requestParams.put(Constants.DESCRIPTION, faker.name().title());
        requestParams.put(Constants.HOMEPAGE, "https://github.com");
        requestParams.put(Constants.PRIVATE, "false");
        requestParams.put(Constants.IS_TEMPLATE, "true");
        return requestParams;
    }

    public static JSONObject updateRepoData(){
        JSONObject requestParams = new JSONObject();
        requestParams.put(Constants.NAME, faker.name().username());
        requestParams.put(Constants.DESCRIPTION, faker.name().title());
//        requestParams.put(Constants.HOMEPAGE, "https://github.com");
//        requestParams.put(Constants.PRIVATE, "false");
//        requestParams.put(Constants.IS_TEMPLATE, "true");
        return requestParams;
    }



}
