import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class ApiControllerSpec extends PlaySpec with OneAppPerTest {


  "The API controller" should {

    "write a valid JSON payload to a file and return 200" in {
      //in withApplication(Map.empty) { implicit app =>
      val Some(resultAdd) = route(FakeRequest(POST, "/api").withJsonBody(adslotCreatedJson))
      status(resultAdd) must equal(OK)
    }

    "return a 400 when receiving an invalid JSON request" in {
      val Some(resultAdd) = route(FakeRequest(POST, "/api").withHeaders("Content-type" -> "application/json").withTextBody("{\"data\":"))
      status(resultAdd) must equal(BAD_REQUEST)
    }
  }

  val adslotCreatedJson = Json.parse(
    """{
          "data" : {
               "entityType" : "AdSlot",
               "entityId" : {
                   "uuid" : "792645e6-08d4-435e-93b1-ec02cea2394d",
                   "naturalId" : 1,
                   "externalIds" : {
                       "interactiveMedia" : 102999198
                   }
               },
               "mediaType" : "MediaType.Display",
               "name" : "t-o_rei_reiseziele_deutschland_start_sb",
               "alias" : "t-o_rei_reiseziele_deutschland_start_sb",
               "websiteId" : { "uuid": "45070690-a2ba-427c-902b-1bd24cbe86df" },
               "pageGroupId" : { "uuid" : "19e0184f-9886-4d63-b864-0cf633225c2b" }
           }
       }""".mkString)
}
