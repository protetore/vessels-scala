package models

import play.api.libs.json.{Json, JsValue}

import org.joda.time.DateTime
import org.joda.time.format._
import com.wix.accord._
import dsl._

case class Vessel(
  id: Option[String] = None, 
  name: String, 
  width: String, 
  length: String, 
  draft: String, 
  lat: String,
  lng: String, 
  dtLastPosition: String
)   

// TODO: Move to a common package, can be used anywhere
// def parseDate(input: String) = try {
//     Right(fmt parseDateTime input)
//   } catch {
//     case e: IllegalArgumentException => Left(e)
// }

// def isValidDate(input: String) = {
//   parseDate(input).left map (_.getMessage) fold (
//     errMsg => S.error("birthdate", errMsg), //if failure (Left by convention)
//     dt => successFunc(dt) //if success (Right by convention)
//   )
// }

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
