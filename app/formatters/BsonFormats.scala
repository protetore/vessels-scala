package formatters

import reactivemongo.bson.{BSONDocumentWriter, BSONDocumentReader, Macros}
import models.Vessel

object BsonFormats {
  implicit def vesselWriter: BSONDocumentWriter[Vessel] = Macros.writer[Vessel]
  implicit def vesselReader: BSONDocumentReader[Vessel] = Macros.reader[Vessel]
}