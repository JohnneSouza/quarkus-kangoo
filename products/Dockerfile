## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/ubi-quarkus-native-image:21.1.0-java11 AS build
COPY pom.xml /project/
COPY mvnw /project/mvnw
COPY .mvn /project/.mvn
USER quarkus
WORKDIR /project
RUN ./mvnw -B org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
RUN ./mvnw package -Pnative
COPY target/*-runner /project/target/

## Stage 2 : create the docker final image
FROM registry.access.redhat.com/ubi8/ubi-minimal:8.4
WORKDIR /work/
COPY --from=build /project/target/*-runner /work/application

RUN chmod 775 /work /work/application \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work

EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]