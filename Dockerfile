FROM docker.nexusguard.net/base/openjdk:8-jre-alpine3.7
VOLUME /tmp
ADD target/*.jar app.jar
#RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8088
