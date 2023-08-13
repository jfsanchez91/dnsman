FROM openjdk:17-alpine as build
WORKDIR /src
COPY . /src/
RUN ./gradlew --no-daemon assemble

FROM openjdk:17-alpine as runtime
WORKDIR /app
COPY --from=build /src/build/docker/main/layers/libs /app/libs
COPY --from=build /src/build/docker/main/layers/classes /app/classes
COPY --from=build /src/build/docker/main/layers/resources /app/resources
COPY --from=build /src/build/docker/main/layers/application.jar /app/application.jar
EXPOSE 8080
EXPOSE 1053/UDP
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
