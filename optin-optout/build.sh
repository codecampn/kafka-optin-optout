mvn install
mvn spring-boot:build-image

docker push localhost:5001/opt-out-poc/opt-in-out
