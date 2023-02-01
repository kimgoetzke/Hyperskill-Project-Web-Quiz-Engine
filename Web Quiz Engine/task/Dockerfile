# Build stage
FROM gradle:7.6 AS BUILD
WORKDIR /usr/app/
COPY . .
RUN gradle build

# Package stage
FROM openjdk:17
ENV JAR_NAME=Web_Quiz_Engine-task.jar
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME/
COPY --from=BUILD $APP_HOME/ .
EXPOSE 8080
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME