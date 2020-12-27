FROM nginx

COPY webserver/site/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

FROM openjdk:8-jdk-alpine

COPY webserver/api/target/api-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar

EXPOSE 80