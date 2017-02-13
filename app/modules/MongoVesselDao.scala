package modules
 
import javax.inject.Inject

import models.Vessel
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.api.ReadPreference
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.{ExecutionContext, Future}
import traits.VesselDao

class MongoVesselDao @Inject() (reactiveMongoApi: ReactiveMongoApi) extends VesselDao {

  import formatters.JsonFormats._
  import formatters.BsonFormats._

  def collection = reactiveMongoApi.db.collection[JSONCollection]("vessels");
 
  override def find()(implicit ec: ExecutionContext): Future[List[Vessel]] = {
    val genericQueryBuilder = collection.find(Json.obj());
    val cursor = genericQueryBuilder.cursor[Vessel](ReadPreference.Primary);
    cursor.collect[List]()
  }

  override def read(id: String)(implicit ec: ExecutionContext): Future[Option[Vessel]] = {
    collection.find(BSONDocument("_id" -> BSONObjectID(id))).one[Vessel]
  }
 
  override def insert(data: Vessel)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.update(
      BSONDocument("_id" -> BSONObjectID.generate),
      BSONDocument("$set" -> `data`),
      upsert = true
    )
  }
 
  override def update(id: String, data: Vessel)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.update(
      BSONDocument("_id" -> BSONObjectID(id)), 
      BSONDocument("$set" -> `data`)
    )
  }
 
  override def remove(id: String)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.remove(BSONDocument("_id" -> BSONObjectID(id)))
  }

  //  override def select(conditions: BSONDocument)(implicit ec: ExecutionContext): Future[Option[Vessel]] = {
  //    collection.find(conditions).one[Vessel]
  //  }
 
}