FROM openjdk:17-alpine
COPY target/service-neumatico-V01.jar service-neumatico.jar
ENTRYPOINT ["java","-jar","/service-neumatico.jar"]



#FROM eclipse-temurin:17-jdk-focal
#WORKDIR /app
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY src ./src
#CMD ["./mvnw", "spring-boot:run"]