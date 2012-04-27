package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json.Format._
import models._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def status = Action {
    Async {
      Coco.find("chris4403", "243276837852647047") map { coco =>
        Ok(coco.toString)
      }
    }
  }
}
