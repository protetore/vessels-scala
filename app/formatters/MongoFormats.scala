package formatters

import play.api.libs.json._
import models.Vessel

object MongoFormats {

  implicit def vesselFormats: Format[Vessel] = Json.format[Vessel]
  implicit val vesselFormat = Json.format[Vessel]

  //val fromOID = __.json.update((__ \ '_id).json.copyFrom( (__ \ '_id \ '$oid).json.pick ))
  //json.transform(fromOID)

  //implicit val vesselJSONReads: Reads[Vessel] = __.json.update((__ \ 'id).json.copyFrom((__ \ '_id \ '$oid).json.pick[JsString] )) andThen Json.reads[Vessel]


  //def mongoReads[T](r: Reads[T]) = {
  //  __.json.update((__ \ 'id).json.copyFrom((__ \ '_id \ '$oid).json.pick[JsString] )) andThen r
  //}
  //def mongoWrites[T](w : Writes[T]) = {
  //  w.transform( js => js.as[JsObject] - "id"  ++ Json.obj("_id" -> Json.obj("$oid" -> js \ "id")) )
  //}
  //implicit val userRead: Reads[Vessel] = mongoReads[Vessel](Json.reads[Vessel])
  //implicit val userWrites: Writes[Vessel] = mongoWrites[Vessel](Json.writes[Vessel])
}

