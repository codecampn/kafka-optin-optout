#!/bin/sh

# Build spring boot modules
mvn -f optin-optout-library install
mvn -f streams install
mvn -f optin-optout install

# Build frontend
cd frontend
yarn install
yarn build
cd ..

# Start local docker-image-registry
docker run -d -p 5001:5000 --name registry registry:2.7

# Build docker images
mvn -f optin-optout spring-boot:build-image
mvn -f streams spring-boot:build-image
docker build -t localhost:5001/optin-poc-kafka-connector:4.2.0 ./connectors
docker build -t localhost:5001/opt-out-poc/frontend ./frontend

# Push docker images to local registry
docker push localhost:5001/opt-out-poc/streams:latest
docker push localhost:5001/opt-out-poc/opt-in-out
docker push localhost:5001/opt-out-poc/frontend
docker push localhost:5001/optin-poc-kafka-connector:4.2.0

# install operators
helm repo add strimzi https://strimzi.io/charts/
helm repo add postgres-operator-charts https://opensource.zalando.com/postgres-operator/charts/postgres-operator
helm install strimzi strimzi/strimzi-kafka-operator
helm install postgres-operator postgres-operator-charts/postgres-operator
helm upgrade --install ingress-nginx ingress-nginx \
--repo https://kubernetes.github.io/ingress-nginx \
--namespace ingress-nginx --create-namespace

# deploy
helm upgrade frontend ./helm/frontend --install
helm upgrade kafka-cluster ./helm/kafka-cluster --install
helm upgrade kafka-connect ./helm/kafka-connect --install
helm upgrade opt-in-out ./helm/opt-in-out --install
helm upgrade streams ./helm/streams --install
