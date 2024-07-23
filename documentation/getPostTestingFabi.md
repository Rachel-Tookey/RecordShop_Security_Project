| method | test endpoint | parameters                                  | expected response                          | status          |
|--------|---------------|---------------------------------------------|--------------------------------------------|-----------------|
| GET    | /records      | none                                        | list of all records                        | 200             |
| GET    | /records      | name :  back in black                       | 1 record with title "Back in Black"        | 200             |
| GET    | /records      | artist :  pink floyd                        | 2 records by pink floyd                    | 200             |
| GET    | /records      | artist :  pink floyd , name: the wall       | 1 record "The Wall" by Pink Floyd          | 200             |
| GET    | /records      | wrong keyname any                           | Invalid Parameters used in the request     | 400 Bad Request |
| GET    | /records      | artist: not in database artist              | No record found having artist {}           | 404 Not Found   |
| GET    | /records      | name: not in database record                | No record found with name {}               | 404 Not Found   |
| GET    | /records      | artist + name (not combination found in db) | No record found with name {} and artist {} | 404 Not Found   |

### Note:

This is a supplemental file provided by the developer as she documented her manual testing for the GET posts.