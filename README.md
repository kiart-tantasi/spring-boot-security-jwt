# Run app

Use IDE to run app at `src/main/java/com/kt/springbootsecurityjwt/SpringBootSecurityJwtApplication.java`

or with gradle
```
./gradlew bootRun
```

# Endpoints

## Public endpoints

```
curl http://localhost:8080/api/test
```

## Private endpoints

```
curl http://localhost:8080/api/secret
```

# How to access private endpoints

Include Bearer Token Authorization header in request

ADMIN authority
```
curl http://localhost:8080/api/secret -H 'authorization: Bearer any-token'
```

USER authority
```
curl http://localhost:8080/api/secret -H 'authorization: Bearer ADMIN'
```
