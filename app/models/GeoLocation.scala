package models

case class GeoPoint(coordinates: List[Double])
case class GeoBox(topLeft: GeoPoint, bottomRight: GeoPoint)