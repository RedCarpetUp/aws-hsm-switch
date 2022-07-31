# multi stage docker build
FROM gradle:jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17.0.2-jdk-slim

EXPOSE 16805

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar
COPY --from=build /home/gradle/src/Properties /app/Properties
COPY --from=build /home/gradle/src/redcpt.keystore /app/redcpt.keystore

ENTRYPOINT ["java", "-jar","/app/app.jar"]
