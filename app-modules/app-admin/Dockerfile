FROM adoptopenjdk:11-jdk
RUN mkdir /opt/app
COPY build/libs/app-admin-1.0.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]
