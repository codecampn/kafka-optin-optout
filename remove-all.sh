helm uninstall kafka-cluster
helm uninstall kafka-connect
helm uninstall postgres-operator
helm uninstall ingress-nginx
helm uninstall strimzi
helm uninstall streams
helm uninstall frontend
helm uninstall opt-in-out
helm repo remove postgres-operator-charts
helm repo remove strimzi
helm repo remove nginx-stable
docker stop registry
docker rm registry
