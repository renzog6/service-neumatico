FROM openjdk:17-alpine
COPY target/service-neumatico*.jar service-neumatico.jar
ENTRYPOINT ["java","-jar","/service-neumatico.jar"]
