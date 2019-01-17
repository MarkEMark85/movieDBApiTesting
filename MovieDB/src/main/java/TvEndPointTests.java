import Helpers.Helpers;
import org.testng.annotations.Test;

import static Helpers.CommonKeys.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TvEndPointTests extends Helpers {

    @Test
    public void testValidTvInfo(){
        given().when().get(urlBuilder(ENDPOINTS.TV.getValue() + "/1877")).then()
                .statusCode(STATUS.STATUS_OKAY.getStatusCode())
                .body(ORIGINAL_NAME, equalTo("Phineas and Ferb")
                        , FIRST_AIR_DATE, equalTo("2007-08-17") );
    }

    @Test
    //Test known to fail due to incorrect error handling returns a 500
    public void testInvalidLargeTvId(){
        given().when().get(urlBuilder(ENDPOINTS.TV.getValue() + "/123456789987456321123456789147852366")).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.NOT_FOUND.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.NOT_FOUND.getErrorMessage()));
    }

    @Test
    public void testInvalidTvId(){
        given().when().get(urlBuilder(ENDPOINTS.TV.getValue() + "/" + MULTI_BYTE )).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.NOT_FOUND.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.NOT_FOUND.getErrorMessage()));

    }

    @Test
    public void testNoTvId(){
        given().when().get(urlBuilder(ENDPOINTS.TV.getValue())).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.NOT_FOUND.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.NOT_FOUND.getErrorMessage()));
    }
}
