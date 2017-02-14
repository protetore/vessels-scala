package connectors
 
import javax.inject.Inject

import play.api.libs.json.{Json, JsObject}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.api.ReadPreference
import reactivemongo.bson.BSONObjectID

import models.Vessel

import scala.concurrent.{ExecutionContext, Future}
import dao.VesselDao
import dao.DaoResponse

class MongoVesselDao @Inject() (reactiveMongoApi: ReactiveMongoApi) extends VesselDao {

  //import formatters.JsonFormats._
  import formatters.MongoFormats._
  import models.Vessel._

  def collection = reactiveMongoApi.db.collection[JSONCollection]("vessels");

  // Id transformation
  def oid(objectId: String): JsObject = oid(BSONObjectID(objectId))
  def oid(objectId: BSONObjectID): JsObject = Json.obj(Id -> objectId)

  // Set helper
  def set(data: Vessel): JsObject = Json.obj("$set" -> data)
 
  override def find()(implicit ec: ExecutionContext): Future[List[Vessel]] = {
    val genericQueryBuilder = collection.find(Json.obj());
    val cursor = genericQueryBuilder.cursor[Vessel](ReadPreference.Primary);
    cursor.collect[List]()
  }

  override def read(id: String)(implicit ec: ExecutionContext): Future[Option[Vessel]] = {
    collection.find(oid(id)).one[Vessel]
  }
 
  override def insert(data: Vessel)(implicit ec: ExecutionContext): Future[DaoResponse] = {
    collection.insert(data).map( r => new DaoResponse(r.ok, r.writeErrors.toString()))
  }
 
  override def update(id: String, data: Vessel)(implicit ec: ExecutionContext): Future[DaoResponse] = {
    collection.update(
      oid(id),
      set(data)
    ).map( r => new DaoResponse(r.ok, r.writeErrors.toString()))
  }
 
  override def remove(id: String)(implicit ec: ExecutionContext): Future[DaoResponse] = {
    collection.remove(
      oid(id)
    ).map( r => new DaoResponse(r.ok, r.writeErrors.toString()))
  }

  //  override def select(conditions: BSONDocument)(implicit ec: ExecutionContext): Future[Option[Vessel]] = {
  //    collection.find(conditions).one[Vessel]
  //  }

  //  def inArea(from: GeoPoint, to: GeoPoint)(implicit ec: ExecutionContext): Future[DaoResponse] = {
  //
  //  }
}