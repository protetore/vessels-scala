package dao

import com.google.inject.ImplementedBy

import scala.concurrent.{ExecutionContext, Future}
import connectors.MongoVesselDao
import models.{GeoBox, Vessel}

@ImplementedBy(classOf[MongoVesselDao])
trait VesselDao {

  def find()(implicit ec: ExecutionContext): Future[List[Vessel]]
 
  def read(id: String)(implicit ec: ExecutionContext): Future[Option[Vessel]]

  def insert(data: Vessel)(implicit ec: ExecutionContext): Future[Option[DaoError]]
 
  def update(id: String, data: Vessel)(implicit ec: ExecutionContext): Future[Option[DaoError]]
 
  def remove(id: String)(implicit ec: ExecutionContext): Future[Option[DaoError]]

  def area(area: GeoBox)(implicit ec: ExecutionContext): Future[List[Vessel]]

  def named(name: String)(implicit ec: ExecutionContext): Future[List[Vessel]]
}