FROM guiyunweb/openjdk:11
RUN mkdir /opt/app && mkdir /opt/shareclasses
COPY build/libs/app-gateway-1.0.jar /opt/app/app.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

CMD /wait-for-it.sh app-admin:16201 && java -Xshareclasses:cacheDir=/opt/shareclasses -jar /opt/app/app.jar