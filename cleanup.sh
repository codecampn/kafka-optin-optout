kubectl delete postgresql sink-database-cluster
kubectl delete poddisruptionbudgets.policy postgres-sink-database-cluster-pdb
kubectl delete service sink-database-cluster sink-database-cluster-config sink-database-cluster-repl postgres-operator
kubectl delete secret sinkuser.sink-database-cluster.credentials.postgresql.acid.zalan.do
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
