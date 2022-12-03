FROM amazoncorretto:18
RUN mkdir /app

WORKDIR /app

ADD ./api/target/product-catalog-api-1.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "product-catalog-api-1.0-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "image-catalog-api-1.0.0-SNAPSHOT.jar"]
#CMD java -jar image-catalog-api-1.0.0-SNAPSHOT.jar
