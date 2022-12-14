###### Sin Build #######################################
#FROM openjdk:17-alpine
#COPY target/service-neumatico*.jar service-neumatico.jar
#ENTRYPOINT ["java","-jar","/service-neumatico.jar"]


###### Build #######################################
FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /workdir/server
COPY pom.xml /workdir/server/pom.xml
#RUN mvn dependency:go-offline

COPY src /workdir/server/src
RUN mvn package

#FROM builder as dev-envs

# lightweight image
FROM openjdk:17-alpine

COPY  --from=builder /workdir/server/target/service-neumatico*.jar service-neumatico.jar
ENTRYPOINT ["java","-jar","/service-neumatico.jar"]