package formatters

import play.api.libs.json._
import models.Vessel

object JsonFormats {
  val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss"
  implicit val dateTimeReads = play.api.libs.json.Reads.jodaDateReads(dateTimePattern)
  implicit val dateTimeWrites = play.api.libs.json.Writes.jodaDateWrites(dateTimePattern)

  implicit def vesselFormats: Format[Vessel] = Json.format[Vessel]
  implicit val vesselFormat = Json.format[Vessel]

}