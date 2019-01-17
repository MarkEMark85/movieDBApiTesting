import Helpers.Helpers;
import org.testng.annotations.Test;

import static Helpers.CommonKeys.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PeopleEndPointTests extends Helpers {

    @Test
    public void testValidPerson(){
        given().when().get(urlBuilder(ENDPOINTS.PERSON.getValue() + "/9657")).then()
                .statusCode(STATUS.STATUS_OKAY.getStatusCode())
                .body(BIRTHDAY, equalTo("1964-11-14")
                        , KNOWN_FOR_DEPARTMENT, equalTo("Acting")
                        , NAME, equalTo("Patrick Warburton"));
    }

    @Test
    public void testInvalidLargePersonId(){
        given().when().get(urlBuilder(ENDPOINTS.PERSON.getValue() + "/123456789987456321123456789147852366")).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.NOT_FOUND.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.NOT_FOUND.getErrorMessage()));
    }

    @Test
    public void testInvalidApiKey(){
        given().when().get(BASE_URL + ENDPOINTS.PERSON.getValue() + "/9657?api_key=" + MULTI_BYTE).then()
                .statusCode(STATUS.BAD_REQUEST.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.BAD_REQUEST.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.BAD_REQUEST.getErrorMessage()));
    }
}
