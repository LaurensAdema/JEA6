FROM payara/micro:5.181
ENV TZ Europe/Amsterdam

RUN wget -O $PAYARA_PATH/database-connector.jar http://repo2.maven.org/maven2/com/microsoft/sqlserver/mssql-jdbc/6.2.2.jre8/mssql-jdbc-6.2.2.jre8.jar

COPY --chown=payara config/payara/domain.xml $PAYARA_PATH
RUN ls -al $PAYARA_PATH

COPY kwetter2.war $PAYARA_PATH

EXPOSE 8080 8081

ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--domainConfig", "/opt/payara/domain.xml", "--addJars", "/opt/payara/database-connector.jar", "--deploy", "/opt/payara/kwetter2.war"]