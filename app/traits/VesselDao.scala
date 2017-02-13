package traits

import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument}
import com.google.inject.ImplementedBy
import models.Vessel

import scala.concurrent.{ExecutionContext, Future}
import modules.MongoVesselDao

@ImplementedBy(classOf[MongoVesselDao])
trait VesselDao {

  def find()(implicit ec: ExecutionContext): Future[List[Vessel]]
 
  def read(id: String)(implicit ec: ExecutionContext): Future[Option[Vessel]]

  def select(conditions: BSONDocument)(implicit ec: ExecutionContext): Future[Option[Vessel]]

  def insert(data: Vessel)(implicit ec: ExecutionContext): Future[WriteResult]
 
  def update(id: String, data: Vessel)(implicit ec: ExecutionContext): Future[WriteResult]
 
  def remove(id: String)(implicit ec: ExecutionContext): Future[WriteResult]
}