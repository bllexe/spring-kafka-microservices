FROM amazoncorretto:17-alpine
RUN mkdir /app
WORKDIR /app
COPY target/order-service.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
