# Movie Fun!

Smoke Tests require server running on port 8080 by default.

## Build WAR ignoring Smoke Tests

```
$ mvn clean package -DskipTests -Dmaven.test.skip=true
```

## Run Smoke Tests against specific URL

```
$ MOVIE_FUN_URL=http://moviefun.example.com mvn test
```

## Configuration Server setup

Following dependencies were added:

```
<dependency>
    <groupId>io.pivotal.spring.cloud</groupId>
    <artifactId>spring-cloud-services-starter-config-client</artifactId>
    <version>1.3.1.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Configuration file used by the app is registered in [Configuration repository URL](https://github.com/poprygun/platform-acceleration-movie-fun-config)

1. Checkout config-server branch

>git fetch

>git checkout config-server

2. Create Cloud Configuration Server service pointing to the properties source repository.

>cf create-service -c '{"git": { "uri": "https://github.com/poprygun/platform-acceleration-movie-fun-config"} }' p-config-server standard moviefun-config-server

3. Create db service

>cf create-service p-mysql 100mb-dev moviefun-db

4. Package and push the application to Cloud Foundry

>mvn clean package && cf push

5. Navigate to [actuator endpoint](http://platform-acceleration-movie-fun.cfapps.pez.pivotal.io/env)
grap password from the log output of application startup, use username - user

Below is the output of actuator endpoint highlighting the properties served by configuration server that we 
provisioned in step 2.  Notice the reference to configService pointing to github repo with application.yml


```
 "configService:configClient":{  
      "config.client.version":"169e49227460d341c3bd6911e91d3f67f88d1e2b"
   },
   "configService:https://github.com/poprygun/platform-acceleration-movie-fun-config/application.yml":{  
      "spring.jpa.generate-ddl":true,
      "spring.jpa.properties.hibernate.dialect":"org.hibernate.dialect.MySQL5Dialect",
      "spring.mvc.view.prefix":"/WEB-INF/",
      "spring.mvc.view.suffix":".jsp"
   }
 ```  