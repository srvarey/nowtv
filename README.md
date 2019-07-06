##########################################
# Sky Watchlist
##########################################


##########################################
## How to run the service

### Clone the repository:
   git clone https://github.com/srvarey/nowtv-watchlist.git

### Get to the `nowtv-watchlist` folder:
   cd nowtv-watchlist

### Run the service:
   sbt run

### The service runs on port 5000 by default.

## Usage

## Customer entity:
### case class Customer(id: model.UserId, watchlist: List[model.ContentID])

## Watchlist entity:
### case class Watchlist(watchlist: List[model.ContentID])


####################################################################################
### Examples from technical test

### Given a customer with userId 12345 and an empty Watchlist
    curl -v -H "Content-Type: application/json"  -X POST http://localhost:5000/customers  -d '{"id": "12345", "watchlist":[]}'

### When the customer adds ContentIDs 'crid1', 'crid2', 'crid3', 'crid4', 'crid5' to their watchlist
    curl -v -H "Content-Type: application/json"  -X PUT http://localhost:5000/watchlist/12345  -d  '{"watchlist":["crid1", "crid2", "crid3", "crid4", "crid5"]}'

### Then their Watchlist should only contain ContentIDs 'crid1', 'crid2', 'crid3', 'crid4', 'crid5'
    curl -v http://localhost:5000/watchlist/12345

### Given a customer with userId '12345' and a Watchlist containing ContentIDs 'crid1'', 'crid2', 'crid3','crid4', 'crid5'
### When they remove ContentID 'crid1' from their Watchlist
    curl -v -H "Content-Type: application/json"  -X DELETE http://localhost:5000/watchlist/12345?crid=crid1

### Then their Watchlist should only contain ContentIDs 'crid2', 'crid3', 'crid4', 'crid5'
    curl -v http://localhost:5000/watchlist/12345

### Given two customers exist in the system, one with userId '12345' and one with userId '54321'
### And user '12345' has a watchlist containing 'crid1', 'crid2', 'crid3'
    curl -v -H "Content-Type: application/json"  -X PUT http://localhost:5000/customers/12345  -d '{"id": "12345", "watchlist":["crid1", "crid2", "crid3"]}'

### And user '54321' has a watchlist containing 'crid1'
    curl -v -H "Content-Type: application/json"  -X POST http://localhost:5000/customers  -d '{"id": "54321", "watchlist":["crid1"]}'

### When customer with userId '12345' requests to get their Watchlist
    curl -v http://localhost:5000/watchlist/12345

### Then they should only be provided with the following ContentIDs "crid1", "crid2", "crid3"
    curl -v http://localhost:5000/watchlist/12345




