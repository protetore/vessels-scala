package dao

import com.google.inject.ImplementedBy
import scala.concurrent.{ExecutionContext, Future}
import connectors.MongoVesselDao
import models.Vessel

@ImplementedBy(classOf[MongoVesselDao])
trait VesselDao {

  def find()(implicit ec: ExecutionContext): Future[List[Vessel]]
 
  def read(id: String)(implicit ec: ExecutionContext): Future[Option[Vessel]]

  def insert(data: Vessel)(implicit ec: ExecutionContext): Future[DaoResponse]
 
  def update(id: String, data: Vessel)(implicit ec: ExecutionContext): Future[DaoResponse]
 
  def remove(id: String)(implicit ec: ExecutionContext): Future[DaoResponse]

  //def select(conditions: Any)(implicit ec: ExecutionContext): Future[Option[Vessel]]
}