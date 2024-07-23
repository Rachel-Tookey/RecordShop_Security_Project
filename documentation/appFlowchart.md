# Application Flowchart

```mermaid
flowchart TD;
    User -->|Choose Operation|Operation
    Operation-->API{API endpoint controller}
    API -->|INSERT|DB[(Database)]
    API -->|UPDATE|DB
    API <-->|SELECT|DB
```