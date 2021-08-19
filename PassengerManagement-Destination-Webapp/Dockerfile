FROM openjdk:11-jre-slim

RUN apt-get update; apt-get install -y curl unzip

RUN curl -o /opt/newrelic-java.zip https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip
RUN unzip /opt/newrelic-java.zip -d /opt
COPY newrelic.yml /opt/newrelic/newrelic.yml

ENV JAVA_OPTS="$JAVA_OPTS -Dnewrelic.config.app_name='pm-destinations'"
ENV JAVA_OPTS="$JAVA_OPTS -Dnewrelic.config.license_key='423d6c3b8839eb7d0479f24c541da0ea4414NRAL'"

COPY target/pm-destinations.jar pm-destinations.jar

EXPOSE 8080

ENTRYPOINT ["java", "-javaagent:/opt/newrelic/newrelic.jar", "-jar", "/pm-destinations.jar"]