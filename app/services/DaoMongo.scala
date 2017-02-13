package services
 
import javax.inject.Inject
 
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.api.ReadPreference
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.commands.MultiBulkWriteResult
import reactivemongo.bson.{BSONDocument, BSONObjectID}
 
import scala.concurrent.{ExecutionContext, Future}

import traits.DaoTrait

class DaoMongo @Inject() (reactiveMongoApi: ReactiveMongoApi, collectionName: String) extends DaoTrait {
 
  def collection = reactiveMongoApi.db.collection[JSONCollection](collectionName);
 
  override def find()(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    val genericQueryBuilder = collection.find(Json.obj());
    val cursor = genericQueryBuilder.cursor[JsObject](ReadPreference.Primary);
    cursor.collect[List]()
  }

  override def read(id: String)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.find(BSONDocument("_id" -> BSONObjectID(id))).one[JsObject]
  }
 
  override def select(conditions: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.find(conditions).one[JsObject]
  }
 
  override def insert(data: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.update(
      BSONDocument("_id" -> data.get("_id").getOrElse(BSONObjectID.generate)),
      data, 
      upsert = true
    )
  }

  // override def insertAll(data: BSONDocument)(implicit ec: ExecutionContext): Future[MultiBulkWriteResult] = {
  //   TODO: must use a ImplicitlyDocumentProducer to produce to pass to bulk insert
  //   collection.bulkInsert(ordered = false)()
  // }
 
  override def update(id: String, data: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.update(
      BSONDocument("_id" -> BSONObjectID(id)), 
      BSONDocument("$set" -> data)
    )
  }
 
  override def remove(id: String)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.remove(BSONDocument("_id" -> BSONObjectID(id)))
  }
 
}