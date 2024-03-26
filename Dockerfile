FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar .
EXPOSE 5050
ENTRYPOINT [ "java", "-jar", "demo-0.0.1-SNAPSHOT.jar" ]