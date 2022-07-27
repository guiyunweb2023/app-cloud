FROM guiyunweb/openjdk:11
RUN mkdir /opt/app && mkdir /opt/shareclasses
COPY build/libs/app-gateway-1.0.jar /opt/app/app.jar

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

CMD /wait && java -Xshareclasses:cacheDir=/opt/shareclasses -jar /opt/app/app.jar
#CMD ["/wait","&&","java", "-Xshareclasses:cacheDir=/opt/shareclasses","-jar", "/opt/app/app.jar"]