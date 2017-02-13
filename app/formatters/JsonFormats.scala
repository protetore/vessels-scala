package formatters

import play.api.libs.json.Json
import play.api.libs.json.Format
import models.Vessel

object JsonFormats {
  implicit def vesselFormats: Format[Vessel] = Json.format[Vessel]
}