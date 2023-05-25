package endpoints;

import assertion.Verify;
import com.relevantcodes.extentreports.LogStatus;
import constants.Constants;
import constants.FilePathConstants;
import constants.UrlConstants;
import io.restassured.response.Response;
import payload.data.RepositoryData;
import utils.APIUtils;
import utils.PropertyParser;

import static base.TestBase.report;

public class RepositoryEndpoint {
    PropertyParser propertyParser;

    public RepositoryEndpoint() {
        propertyParser = new PropertyParser(FilePathConstants.REPOSITORY_TESTDATA_PATH);
    }


    public String verifyCreateRepository() {
        String token = propertyParser.getPropertyValue("bearer_token");
        Response response = APIUtils.postRepo(token, UrlConstants.CREATE_REPO_URL, RepositoryData.createRepoData());

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_201);

        report.log(LogStatus.INFO, "Verify JSON schema");
        boolean actualSchema = APIUtils.validateJsonSchema(response, FilePathConstants.CREATE_REPO_SCHEMA_PATH);
        Verify.verifyBooleanAndStopTest(actualSchema, true);

        String repoName = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(),Constants.NAME);
        return repoName;
    }

    public void verifyGetRepository(String repoName) {
        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.getRepo(token,UrlConstants.REPOSITORY_URL,owner,repoName);

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_200);
        report.log(LogStatus.INFO,"Verify response body");
        String actualRepoName = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(),Constants.NAME);
        String login = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(),"owner.login");

        report.log(LogStatus.INFO,"Verify login username");
        Verify.verifyString(login,owner);
        report.log(LogStatus.INFO,"Verify repository name");
        Verify.verifyString(actualRepoName,repoName);

    }

    public String verifyUpdateRepository(String repoName) {
        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.patchRepo(token,UrlConstants.REPOSITORY_URL,owner,repoName,RepositoryData.updateRepoData());

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_200);
        report.log(LogStatus.INFO,"Verify response body");
        String actualRepoName = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(),Constants.NAME);
        String login = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(),"owner.login");

        report.log(LogStatus.INFO,"Verify login username");
        Verify.verifyString(login,owner);
        return actualRepoName;

    }

    public void verifyVulnerablityEnabled(String repoName){
        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.getRepo(token, UrlConstants.VULNERABLITY_URL,owner,repoName);
        Verify.verifyInteger(response.getStatusCode(),Constants.STATUS_204);
    }

//    public void enableVulnerablity(String repoName){
//        String token = propertyParser.getPropertyValue("bearer_token");
//        String owner = propertyParser.getPropertyValue("repo_owner");
//        Response response = APIUtils.(token, UrlConstants.VULNERABLITY_URL,owner,repoName);
//        Verify.verifyInteger(response.getStatusCode(),Constants.STATUS_204);
//    }
    public void disableVulnerablity(String repoName){
        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.deleteRepo(token, UrlConstants.VULNERABLITY_URL,owner,repoName);
        Verify.verifyInteger(response.getStatusCode(),Constants.STATUS_204);
    }
    public void verifyDeleteRepository(String repoName) {
        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.deleteRepo(token, UrlConstants.REPOSITORY_URL,owner,repoName);

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_204);
    }
}
