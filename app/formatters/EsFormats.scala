package formatters

import models.{GeoBox, GeoPoint, Vessel}
import org.joda.time.DateTime

object EsFormats {

  import play.api.libs.functional.syntax._
  import play.api.libs.json._

  val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss"
  implicit val dateTimeReads = play.api.libs.json.Reads.jodaDateReads(dateTimePattern)
  implicit val dateTimeWrites = play.api.libs.json.Writes.jodaDateWrites(dateTimePattern)

  implicit def geoPointFormats: Format[GeoPoint] = Json.format[GeoPoint]
  implicit val geoPointFormat = Json.format[GeoPoint]

  implicit def geoBoxFormats: Format[GeoBox] = Json.format[GeoBox]
  implicit val geoBoxFormat = Json.format[GeoBox]

  implicit val esVesselReads: Reads[Vessel] = (
    (JsPath \ "_id").readNullable[String] and
    (JsPath \ "name").read[String] and
    (JsPath \ "width").read[Double] and
    (JsPath \ "length").read[Double] and
    (JsPath \ "draft").read[Double] and
    (JsPath \ "dtLastPosition").read[DateTime] and
    (JsPath \ "position").read[GeoPoint]
  )(Vessel.apply _)

  implicit val esVesselWrites: OWrites[Vessel] = (
    (JsPath \ "id").writeNullable[String] and
    (JsPath \ "name").write[String] and
    (JsPath \ "width").write[Double] and
    (JsPath \ "length").write[Double] and
    (JsPath \ "draft").write[Double] and
    (JsPath \ "dtLastPosition").write[DateTime] and
    (JsPath \ "position").write[GeoPoint]
  )(unlift(Vessel.unapply))
}

