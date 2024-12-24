**API Endpoints**:

Base URL: http://localhost:8080

| Route            | Method | Requirements                                                                                                           | Example                                                                                                     | Description |
|------------------|--------|------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|-------------|
| `/login`         | POST   | Body: <br/>- username<br/>- password                                                                                   | ```{"username":"GillyT10","password":"goodbye"}```                                                          |             |
| `/auth/register` | POST   | Body: <br/>- firstname<br/>- lastname<br/>- username(unique)<br/>- password<br/>- role(ADMIN, STAFF or USER)           | ```{"firstname":"Sara","lastname":"Roger","username":"sara_rog","password":"my_password","role":"STAFF"}``` |             |
|                  |        |                                                                                                                        |                                                                                                             |             |
|                  |        |                                                                                                                        |                                                                                                             |             |
