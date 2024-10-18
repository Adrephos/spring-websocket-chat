FROM ibm-semeru-runtimes:open-21-jre-jammy

WORKDIR /app
COPY build/libs/spring-boot-chat-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Dspring.datasource.url=jdbc:postgresql://localhost:5432/db","-Dspring.datasource.username=user","-Dspring.datasource.password=password","-Dspring.datasource.driver-class-name=org.postgresql.Driver","-jar","/app/app.jar"]
