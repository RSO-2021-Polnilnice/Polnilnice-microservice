FROM adoptopenjdk:15-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./api/target/polnilnice-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "polnilnice-api-1.0.0-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "polnilnice-api-1.0.0-SNAPSHOT.jar"]
#CMD java -jar polnilnice-api-1.0.0-SNAPSHOT.jar