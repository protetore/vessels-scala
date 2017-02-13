package controllers
 
import javax.inject.Inject
 
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONObjectID, BSONDocument}
import com.wix.accord._
import services.DaoMongo
import models.Vessel

// TODO: Remove all dependecies to ReactiveMongo from this controller
class VesselsController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller
    with MongoController with ReactiveMongoComponents {

  import models.Vessel._
 
  def dao = new DaoMongo(reactiveMongoApi, "vessels")
 
  def index = Action.async { implicit request =>
    dao.find().map(vessels => Ok(Json.toJson(vessels)))
  }
 
  def read(id: String) = Action.async { implicit request =>
    dao.read(id).map(vessel => Ok(Json.toJson(vessel)))
  }
 
  def create = Action.async(BodyParsers.parse.json) { implicit request =>
    val vessel = Vessel.parse(request.body)
    assert(validate(vessel) == Success)

    // TODO: Pass the object to the dao method and convert to BSONDocument there
    dao.insert(BSONDocument(
      Name -> vessel.name,
      Width -> vessel.width,
      Length -> vessel.length,
      Draft -> vessel.draft,
      Lat -> vessel.lat,
      Lng -> vessel.lng,
      DtLastPosition -> vessel.dtLastPosition
    )).map(result => Created)
  }
 
  def update(id: String) = Action.async(BodyParsers.parse.json) { implicit request =>
    val vessel = Vessel.parse(request.body)
    println("Result: " + validate(vessel))

    // TODO: Pass the object to the dao method and convert to BSONDocument there
    dao.update(
      id,
      BSONDocument(
      	Name -> vessel.name,
        Width -> vessel.width,
        Length -> vessel.length,
        Draft -> vessel.draft,
        Lat -> vessel.lat,
        Lng -> vessel.lng,
        DtLastPosition -> vessel.dtLastPosition
      )).map(result => Accepted)
  }
 
  def delete(id: String) = Action.async {
    dao.remove(id).map(result => Accepted)
  }
}