package formatters

import models.Vessel
import org.joda.time.DateTime

object MongoFormats {

  import play.api.libs.functional.syntax._
  import play.api.libs.json._

  val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss"
  implicit val dateTimeReads = play.api.libs.json.Reads.jodaDateReads(dateTimePattern)
  implicit val dateTimeWrites = play.api.libs.json.Writes.jodaDateWrites(dateTimePattern)

  implicit val vesselReads: Reads[Vessel] = (
    (JsPath \ "_id" \ "$oid").readNullable[String] and
    (JsPath \ "name").read[String] and
    (JsPath \ "width").read[String] and
    (JsPath \ "length").read[String] and
    (JsPath \ "draft").read[String] and
    (JsPath \ "lat").read[String] and
    (JsPath \ "lng").read[String] and
    (JsPath \ "dtLastPosition").readNullable[DateTime]
  )(Vessel.apply _)

  implicit val vesselWrites: OWrites[Vessel] = (
    (JsPath \ "id").writeNullable[String] and
    (JsPath \ "name").write[String] and
    (JsPath \ "width").write[String] and
    (JsPath \ "length").write[String] and
    (JsPath \ "draft").write[String] and
    (JsPath \ "lat").write[String] and
    (JsPath \ "lng").write[String] and
    (JsPath \ "dtLastPosition").writeNullable[DateTime]
  )(unlift(Vessel.unapply))
}

