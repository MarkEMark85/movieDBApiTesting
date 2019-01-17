import Helpers.Helpers;
import org.testng.annotations.Test;

import static Helpers.CommonKeys.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MovieEndPointTests extends Helpers {

    @Test
    public void testValidMovieInfo() {
        given().when().get(urlBuilder(ENDPOINTS.MOVIE.getValue() + "/22586")).then()
                .statusCode(STATUS.STATUS_OKAY.getStatusCode())
                .body(TITLE, equalTo("The Swan Princess")
                        , RUNTIME, equalTo(89)
                        , RELEASE_DATE, equalTo("1994-11-18"));
    }

    @Test
    public void testInvalidMovieID() {
        given().when().get(urlBuilder(ENDPOINTS.MOVIE.getValue() + "/" + MULTI_BYTE)).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.NOT_FOUND.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.NOT_FOUND.getErrorMessage()));
    }

    @Test
    public void testValidMovieNoApiKey() {
        given().when().get(BASE_URL + ENDPOINTS.MOVIE.getValue() + "/22586?api_key=").then()
                .statusCode(STATUS.BAD_REQUEST.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.BAD_REQUEST.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.BAD_REQUEST.getErrorMessage()));
    }

    @Test
    public void testInvalidMovieLargeId(){
            given().when().get(urlBuilder(ENDPOINTS.MOVIE.getValue() + "" +
                    "")).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.NOT_FOUND.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.NOT_FOUND.getErrorMessage()));
    }
}
