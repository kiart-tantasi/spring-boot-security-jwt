# Run app

Use IDE to run app at `src/main/java/com/kt/springbootsecurityjwt/SpringBootSecurityJwtApplication.java`

or with gradle
```
./gradlew bootRun
```

# Endpoints

## Public endpoints

```
curl http://localhost:8080/api/public
```

## Private endpoints

Include Bearer Token Authorization header in request

USER authority
```
curl http://localhost:8080/api/user -H 'authorization: Bearer USER'
```

ADMIN authority
```
curl http://localhost:8080/api/admin -H 'authorization: Bearer ADMIN'
```
