FROM gradle:jdk15 as builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle clean build -x test

FROM openjdk:15-jdk-alpine as finalApp

ENTRYPOINT ["sudo", "mkdir", "/app/"]

WORKDIR app

COPY --from=builder /home/gradle/src/build/libs/api-0.0.1.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/api-0.0.1.jar"]