package models

import com.wix.accord._
import dsl._
import org.joda.time.DateTime

case class Vessel(
 id: Option[String],
 name: String,
 width: Double,
 length: Double,
 draft: Double,
 dtLastPosition: DateTime,
 position: GeoPoint
)

object Vessel {
  val Id = "_id"
  val Name = "name"
  val Width = "width"
  val Length = "length"
  val Draft = "draft"
  val DtLastPosition = "dtLastPosition"
  val Position = "position"

  // TODO: Better validations, validate datetime, etc
  implicit val vesselValidator: Validator[Vessel] = validator[Vessel] { vessel =>
    vessel.name is notEmpty
    vessel.width > 0 is true
    vessel.length > 0 is true
    vessel.draft > 0 is true
  }
}
