FROM quay.io/strimzi/kafka:0.33.0-kafka-3.3.2

USER root:root

# Install JDBC Connector
RUN mkdir -p /opt/kafka/plugins/jdbc
ADD ./confluentinc-kafka-connect-jdbc-10.6.0.zip /opt/kafka/plugins/jdbc/jdbc-connect.zip
RUN unzip /opt/kafka/plugins/jdbc/jdbc-connect.zip -d /opt/kafka/plugins/jdbc
RUN rm /opt/kafka/plugins/jdbc/jdbc-connect.zip
RUN chown -R 1001:1001 /opt/kafka/plugins/jdbc

USER 1001
