FROM eclipse-temurin:21
LABEL authors="vanderlei"
COPY target/*.jar app.jar
ENTRYPOINT java $JAVA_OPTIONS -jar app.jar