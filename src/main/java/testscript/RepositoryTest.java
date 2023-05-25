package testscript;

import base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import endpoints.RepositoryEndpoint;
import org.testng.annotations.Test;

public class RepositoryTest extends TestBase {
    String reponame;
    RepositoryEndpoint repositoryEndpoint =new RepositoryEndpoint();

    @Test(priority = 1)
    public void createRepositoryTest(){
        report.log(LogStatus.INFO,"Create repository test");
        reponame =repositoryEndpoint.verifyCreateRepository();
    }
    @Test(priority = 2,dependsOnMethods = "createRepositoryTest")
    public void getRepositoryTest(){
        report.log(LogStatus.INFO,"Get repository test");
        repositoryEndpoint.verifyGetRepository(reponame);
    }
    @Test(priority = 3,dependsOnMethods = "createRepositoryTest")
    public void updateRepositoryTest(){
        report.log(LogStatus.INFO,"Update repository test");
        reponame=repositoryEndpoint.verifyUpdateRepository(reponame);
    }

    /***
     * Check if vulnerability alerts are enabled for a repository
     */
    @Test(priority = 4)
    public void checkVulnerability(){
        report.log(LogStatus.INFO,"Check vulnerablity repository test");
        repositoryEndpoint.verifyVulnerablityEnabled(reponame);
    }

    @Test(priority = 100,dependsOnMethods = "createRepositoryTest")
    public void deleteRepositoryTest(){
        report.log(LogStatus.INFO,"Delete repository test");
        repositoryEndpoint.verifyDeleteRepository(reponame);
    }
}
