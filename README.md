# Feature-API
An API to serve requests about features.

## Build & run locally 

Create an `.env`-file based on the `.env.template`-file. Then execute `docker-compose up app`.

### Health-Check

You can check if the application is up and running by executing:

```
curl localhost:8085/actuator/health
```

### Inspecting the API 

If you want to inspect the API or try things out, you can use the Swagger UI at http://localhost:8085/swagger-ui/

#### Debugging

Remote debugging during application execution can be set up by attaching to the port 5010 using your favourite IDE, i.e. IntelliJ IDEA.


## Run tests

Execute `docker-compose up tests`.