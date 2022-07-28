FROM guiyunweb/openjdk:11
RUN mkdir /opt/app && mkdir /opt/shareclasses
COPY build/libs/app-monitor-1.0.jar /opt/app/app.jar
CMD ["java", "-Xshareclasses:cacheDir=/opt/shareclasses","-jar", "/opt/app/app.jar"]
