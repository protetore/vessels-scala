package connectors

import javax.inject.{Inject, Named}

import scala.concurrent.{ExecutionContext, Future}
import com.sksamuel.elastic4s.{ElasticDsl, IndexAndType}
import com.evojam.play.elastic4s.configuration.ClusterSetup
import com.evojam.play.elastic4s.{PlayElasticFactory, PlayElasticJsonSupport}
import dao.VesselDao
import dao.DaoError
import models.{GeoBox, Vessel}

import scala.util.{Failure, Success}

class EsVesselDao @Inject()(cs: ClusterSetup, elasticFactory: PlayElasticFactory, @Named("vessel") indexAndType: IndexAndType)
  extends ElasticDsl with PlayElasticJsonSupport with VesselDao {

  import models.Vessel._
  import formatters.EsFormats._

  private[this] lazy val client = elasticFactory(cs)

  override def find()(implicit ec: ExecutionContext): Future[List[Vessel]] = client execute {
    search in indexAndType
  } map (_.as[Vessel].toList)

  override def read(vesselId: String)(implicit ec: ExecutionContext): Future[Option[Vessel]] = client execute {
    get id vesselId from indexAndType
  } map (_.as[Vessel])

  override def insert(data: Vessel)(implicit ec: ExecutionContext): Future[Option[DaoError]] = client execute {
    index into indexAndType source data
  } map { r => None } recover {
    case e => Some(DaoError("Error removing vessel: " + e.getMessage))
  }

  override def update(vesselId: String, data: Vessel)(implicit ec: ExecutionContext): Future[Option[DaoError]] = client execute  {
    index into indexAndType source data id vesselId
  } map { r => None } recover {
    case e => Some(DaoError("Error removing vessel: " + e.getMessage))
  }

  override def remove(vesselId: String)(implicit ec: ExecutionContext): Future[Option[DaoError]] = client execute {
    delete id vesselId from indexAndType
  } map { r => None } recover {
    case e => Some(DaoError("Error removing vessel: " + e.getMessage))
  }

  // Not working, couldn't find how to pass the corners :/
  override def area(area: GeoBox)(implicit ec: ExecutionContext): Future[List[Vessel]] = client execute {
    search in indexAndType query geoBoxQuery("position")
  } map (_.as[Vessel].toList)

  // It's searching by anything
  override def named(name: String)(implicit ec: ExecutionContext): Future[List[Vessel]] = client execute {
    search in indexAndType query name
  } map (_.as[Vessel].toList)
}
