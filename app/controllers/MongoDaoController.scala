package controllers
 
import javax.inject.Inject
 
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONObjectID, BSONDocument}
import services.DaoMongo

class MongoDaoController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller
    with MongoController with ReactiveMongoComponents {
 
  def dao = new DaoMongo(reactiveMongoApi, collectionName)
 
  
}