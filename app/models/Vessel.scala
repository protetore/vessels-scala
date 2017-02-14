package models

import com.wix.accord._
import dsl._

case class Vessel(
 id: Option[String],
 name: String,
 width: String,
 length: String,
 draft: String,
 lat: String,
 lng: String,
 dtLastPosition: String //Option[DateTime]
)

object Vessel {
  val Id = "_id"
  val Name = "name"
  val Width = "width"
  val Length = "length"
  val Draft = "draft"
  val Lat = "lat"
  val Lng = "lng"
  val DtLastPosition = "dtLastPosition"

  // TODO: Better number validator (check if accord has one builtin)
  implicit val vesselValidator: Validator[Vessel] = validator[Vessel] { vessel =>
    vessel.name is notEmpty
    vessel.width is matchRegex("""\A([0-9.]{1,})\z""")
    vessel.length is matchRegex("""\A([0-9.]{1,})\z""")
    vessel.draft is matchRegex("""\A([0-9.]{1,})\z""")
    vessel.lat is matchRegex("""\A([0-9.-]{1,})\z""")
    vessel.lng is matchRegex("""\A([0-9.-]{1,})\z""")
  }
}
