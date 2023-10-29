# Run app

Use IDE to run app at `src/main/java/com/kt/springbootsecurityjwt/SpringBootSecurityJwtApplication.java`

or with gradle
```
./gradlew bootRun
```

# Endpoints

## Public endpoints

```
http://localhost:8080/api/test
```

## Private endpoints

```
http://localhost:8080/api/secret
```

# How to access private endpoints

**It does not work now. I believe it is because of version change**
Include Bearer Token Authorization header in request
