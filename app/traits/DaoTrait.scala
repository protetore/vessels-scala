package traits
 
import javax.inject.Inject
 
import play.api.libs.json.{JsObject, Json}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.commands.MultiBulkWriteResult
import reactivemongo.bson.{BSONDocument, BSONObjectID}
 
import scala.concurrent.{ExecutionContext, Future}

// Make this trait more generic (not dependant on reactivemongo, 
// leaves the decison to whoever uses this trait)
trait DaoTrait {

  def find()(implicit ec: ExecutionContext): Future[List[JsObject]]
 
  def read(id: String)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def select(conditions: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def insert(data: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]
 
  def update(id: String, data: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]
 
  def remove(id: String)(implicit ec: ExecutionContext): Future[WriteResult]
}