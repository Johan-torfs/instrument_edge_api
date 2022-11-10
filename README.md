# Instrument Recognition Back-end Microservices
This repository contains all the microservices that will be used by our brand new Augemented Reality app. This app will allow you to scan instruments in the real world, giving you extra information about the instrument, known musicians and popular music pieces.

You will find all relevant documentation, as well as a link to the front-end repository, down below.

## Architecture of the microservices
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/MicroserviceArchitecture.png "Microservice Architecture")

## Postman Tests
In this section you will find a description of all the endpoint together with a screenshot of the relevant postman request

### Instrument All
GET: [https://api-edge-johantorfs.cloud.okteto.net/instrument](https://api-edge-johantorfs.cloud.okteto.net/instrument)
This endpoint returns a list of all the Instruments in the database. The Instruments are returned without Musicians or Pieces
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/InstrumentAll.png "Postman Instrument All")

### Instrument By Name
GET: [https://api-edge-johantorfs.cloud.okteto.net/instrument/Piano](https://api-edge-johantorfs.cloud.okteto.net/instrument/{name})
This endpoint returns one Instrument by name. The name of the Instrument is unique. The Instrument is returned with Musicians and Pieces (and Parts)
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/InstrumentByName.png "Postman Instrument By Name")

### Musician All
GET: [https://api-edge-johantorfs.cloud.okteto.net/musician](https://api-edge-johantorfs.cloud.okteto.net/musician)
This endpoint returns a list of all the Musicians in the database. The Musicians are returned without Instrument
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/MusicianAll.png "Postman Musician All")

### Musician By Name
GET: [https://api-edge-johantorfs.cloud.okteto.net/musician/Bartolo%20Alvarez](https://api-edge-johantorfs.cloud.okteto.net/musician/{name})
This endpoint returns one Musician by name. The name of the Musician is unique. The Musician is returned with Instrument
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/MusicianByName.png "Postman Musician By Name")

### Piece All
GET: [https://api-edge-johantorfs.cloud.okteto.net/piece](https://api-edge-johantorfs.cloud.okteto.net/piece)
This endpoint returns a list of all the Pieces in the database. The Pieces are returned without Instrument
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/PieceAll.png "Postman Piece All")

### Piece By Name
GET: [https://api-edge-johantorfs.cloud.okteto.net/piece/Sonata](https://api-edge-johantorfs.cloud.okteto.net/piece/{name})
This endpoint returns a list of Pieces by name. The Piece is returned with Parts, Instruments and Reviews. If multiple Pieces are found that (partially) match the given name, all those Pieces are returned
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/PieceByName.png "Postman Piece By Name")
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/PieceByNameMultiple.png "Postman Piece By Name Multiple")

### Review All
GET: [https://api-edge-johantorfs.cloud.okteto.net/review](https://api-edge-johantorfs.cloud.okteto.net/review)
This endpoint returns a list of all the Reviews in the database.
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/ReviewAll.png "Postman Review All")

### Review By Id
GET: [https://api-edge-johantorfs.cloud.okteto.net/review/636cef26c3f6fe766fec14bd](https://api-edge-johantorfs.cloud.okteto.net/review/{id})
This endpoint returns one Review by id. The id of the Review is unique.
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/ReviewById.png "Postman Review By Id")

### Review POST
POST: https://api-edge-johantorfs.cloud.okteto.net/review
This endpoint creates a new Review in the database. The given parameters are:
- pieceName
- rating
- comment
The newly created Review is returned.
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/ReviewPOST.png "Postman Review POST")

### Review PUT
PUT: https://api-edge-johantorfs.cloud.okteto.net/review/{id}
This endpoint updates an existing Review in the database by Id. The given parameters are:
- rating
- comment
The updated created Review is returned.
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/ReviewPUT.png "Postman Review PUT")

### Review DELETE
DELETE: https://api-edge-johantorfs.cloud.okteto.net/review/{id}
This endpoint deletes an existing Review in the database by Id.
The response is empty with status 200.
![alt text](https://github.com/JohanTorfs/instrument_edge_api/raw/main/images/ReviewDELETE.png "Postman Review DELETE")

## SwaggerUI


## Front-end Repository
[https://github.com/JohanTorfs/instrument_app](https://github.com/JohanTorfs/instrument_app)