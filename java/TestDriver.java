package test;

import org.junit.runner.*;
import GSON.JSONDecoder;

public class TestDriver {
    public static void main(String[] args) {
        /*Initial test must be run with a populated database for DAO tests to be valid and pass
        Every subsequent test will be fine because the load and fill tests will populate the database
         */
        /*
        JSONDecoder jsonDecoder = new JSONDecoder();
        jsonDecoder.loadLocalFiles();
        */
        JUnitCore.main(
		       "test.AuthTokenTest",
                "test.PersonTest",
                "test.EventTest",
                "test.UserTest",
                "test.AuthTokenDAOTest",
                "test.EventDAOTest",
                "test.PersonDAOTest",
                "test.UserDAOTest",
                "test.ClearTest",
                "test.LoadTest",
                "test.EventServiceTest",
                "test.LoginTest",
                "test.PersonServiceTest",
                "test.FillTest",
                "test.RegisterTest"
		       );

    }

}
