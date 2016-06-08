package controllers

import javax.inject.Inject

import com.kenshoo.play.metrics.Metrics
import org.joda.time.DateTime
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ApiController @Inject() (metrics: Metrics) extends Controller {

  def jsonLogger: Logger = LoggerFactory.getLogger("Inventory")

  def api = Action(parse.json) { request =>
    metrics.defaultRegistry.counter("api-count").inc()

    writeToFile(request.body)
    Ok("")
  }

  def api2 = ActionWithErrorHandling(parse.json) { request =>
    println("hello")
    writeToFile(request.body)
    Ok("")
  }

  private def writeToFile(inventoryJson: JsValue) {
    val json = Json.obj(
      "time" -> new DateTime().toString("dd-MM-yyyy'T'HH:mm:ss"),
      "payload" -> inventoryJson)
    jsonLogger.info(json.toString())
  }

  object ActionWithErrorHandling extends ActionBuilder[Request] {
    override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
      block(request) recover {
        case e: Throwable => BadRequest(e.getMessage)
      }
    }
  }
}