# Vessels API in Scala

This is a sample API to perform basic CRUD actions to manage Vessels using Scala with connector for **MongoDB** and **ElasticSearch**.

To change connectors you have to uncomment it's section in conf/application.cong and change the "@ImplementedBy" in app/dao/VesselDao.scala.


## Work in progress


**Elasticsearch**

The elasticsearch connector is missing some details:

- It's not showing the id field on the results;
- The search by area(box) isn't working yet;
- The search by name is currently searching by any field.


**Validators**


There are some simple validators implemented using wix/Accord in the Vessels model just to demonstrate, but they aren't currently in use. There are no treatment for the validate function on the controller.


**Unit Testing**

Unfortunately, there are no unit tests at this moment.


# Actions


* [GET] /vessels 

Retrieves all the vessels


* [GET] /vessels/:id 

Retrieves a vessel by its id


* [POST] /vessels 

Creates a new vessel. This is a sample vessel json:


```json
{
    "name": "Black Pearl",
    "width": 12,
    "length": 62,
    "draft": 22,
    "position": {
        "lng": -42.87654,
        "lat": -45.87654
    },
    "dtLastPosition": "2017-02-06T00:12:41"
}
```

* [DELETE] /vessels/:id 

Removes a vessel by its id


* [PATCH] /vessels/:id 

Updates an existing vessel by its id. This is a sample vessel json:

```json
{
    "name": "Black Pearl",
    "width": 10,
    "length": 60,
    "draft": 20,
    "position": {
        "lng": -40.13784,
        "lat": -42.80984
    },
    "dtLastPosition": "2017-02-06T00:12:41"
}
```

* [POST] /vessels/area 

Retrieves all vessels within a boxed area. Receives the top left point and the bottom right one.
This is a sample vessel json:

```json
{
	"topLeft": {
		"lat": 0,
		"lng": 0
	},
	"bottomRight": {
		"lat": 48,
		"lng": 48
	}
}
```

* [GET] /vessels/named/:name 

Retrieves all vessels the contains the received words.

This is a sample request that returns any ship with a name containing the words "red" or "walrus":

```
http://localhost:9000/vessels/named/red+walrus
```

This is a sample request that returns any ship with a name containing the exact phrase "black pearl":

```
http://localhost:9000/vessels/named/%22black%20pearl%22
```
