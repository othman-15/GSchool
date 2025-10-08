# Build Stage
FROM openjdk:17 AS builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean package -DskipTests


FROM openjdk:17 AS final
EXPOSE 8089
COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar
VOLUME /tmp
COPY target/*.jar  app.jar

ENTRYPOINT ["java","-jar", "app.jar"]
#FROM openjdk:21-oracle
#VOLUME /tmp
#COPY target/*.jar  app.jar
#EXPOSE 8074
#ENTRYPOINT ["java","-jar", "app.jar"]