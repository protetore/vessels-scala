package controllers
 
import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import com.wix.accord._
import dao.{DaoError, VesselDao}
import models.{GeoBox, Vessel}

class VesselsController @Inject()(val dao: VesselDao) extends Controller {

  import models.Vessel._
  import formatters.JsonFormats._

  def index = Action.async { implicit request =>
    dao.find().map(vessels => Ok(Json.toJson(vessels)))
  }
 
  def read(id: String) = Action.async { implicit request =>
    dao.read(id).map {
      case Some(vessel) => Ok(Json.toJson(vessel))
      case None => NotFound
    }
  }
 
  def create = Action.async(BodyParsers.parse.json) { implicit request =>
    val vessel = request.body.as[Vessel]
    assert(validate(vessel) == Success)

    dao.insert(vessel).map {
      case Some(error) => NotFound(error.message)
      case None => Created
    }
  }
 
  def update(id: String) = Action.async(BodyParsers.parse.json) { implicit request =>
    val vessel = request.body.as[Vessel]
    println("Result: " + validate(vessel))

    dao.update(id, vessel).map {
      case Some(error) => NotFound(error.message)
      case None => Accepted
    }
  }
 
  def delete(id: String) = Action.async {
    dao.remove(id).map {
      case Some(error) => NotFound(error.message)
      case None => Accepted
    }
  }

  def inArea = Action.async(BodyParsers.parse.json)  { implicit request =>
    val geobox = request.body.as[GeoBox]
    dao.area(geobox).map(vessels => Ok(Json.toJson(vessels)))
  }

  def byName(name: String) = Action.async  { implicit request =>
    dao.named(name).map(vessels => Ok(Json.toJson(vessels)))
  }
}