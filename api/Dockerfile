ARG TOMCAT_VERSION
ARG FILENAME

FROM tomcat:$TOMCAT_VERSION
WORKDIR /usr/local/tomcat
COPY ./build/libs/${FILENAME}.war ./webapps/

# required for Oracle DB client
ENV TZ=EST

CMD ["catalina.sh", "run"]
