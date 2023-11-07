FROM java:8

ENV TZ=Asia/Shanghai

COPY /target/backend-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]