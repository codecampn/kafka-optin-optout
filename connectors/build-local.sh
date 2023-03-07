#!/bin/sh

docker build -t localhost:5001/optin-poc-kafka-connector:4.2.0 .
docker push localhost:5001/optin-poc-kafka-connector:4.2.0
