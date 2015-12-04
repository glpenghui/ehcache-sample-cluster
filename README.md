
# Intro

This is a sample application using Spring Boot and EHCache clustering using JGroups.

This project has a sample RESTful service which manages user records. 

# Build

To build, execute commands
```
git clone https://github.com/sbanal/ehcache-sample-cluster.git
cd ehcache-sample-cluster
mvn clean install docker:build
```

A docker image is built after the commands above succeeds. Check the images by opening a docker terminal.
Run command
```
docker images
```

This should output an entry similar to output below:
```
REPOSITORY                           TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
sbanal/ehcache-sample-cluster        latest              1b49ff04f7ee        21 minutes ago      676.5 MB
```

# Run

To run a cluster manually, run the image using different ports so you can access the endpoints and test EHCache. 
Commands below runs two new instances.
```
export web1=`docker run -p 9110:8080 -d sbanal/ehcache-sample-cluster`
export web2=`docker run -p 9111:8080 -d sbanal/ehcache-sample-cluster`
```

Check the logs of the machine by running commands below on separate terminals
```
docker logs -f $web1
docker logs -f $web2
```

# Testing

To check the endpoints, get the ip of your docker machine
```
docker-machine ip default
```

Then hit the endpoints using the custom port you provided in docker run command and output IP of docker ip command.
```
http://192.168.99.100:9110/users/1
http://192.168.99.100:9111/users/1
```
