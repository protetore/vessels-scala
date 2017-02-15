package models

case class GeoPoint(lng: Double, lat: Double)

case class GeoBox(topLeft: GeoPoint, bottomRight: GeoPoint)