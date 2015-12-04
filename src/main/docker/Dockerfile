FROM java:8
VOLUME /tmp
ADD lib/ehcache-sample-cluster-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
