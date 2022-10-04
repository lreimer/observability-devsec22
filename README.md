# Observability: Der Schlüssel für Threat Detection, Mitigation und Analyse

Demos for Observability: Der Schlüssel für Threat Detection, Mitigation und Analyse Talk @ devSec() 2022

## Local Development

```bash
mvn package
curl --silent --fail -L "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.18.0/opentelemetry-javaagent.jar" -o "target/opentelemetry-javaagent.jar"
java -javaagent:target/opentelemetry-javaagent.jar -jar target/spring-boot-app-0.0.1.jar
```

## Local Kubernetes Setup

```bash
helm repo add grafana https://grafana.github.io/helm-charts
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

helm install --create-namespace -n kube-prometheus-stack kube-prometheus-stack prometheus-community/kube-prometheus-stack
helm install --create-namespace -n tempo tempo grafana/tempo
helm install --create-namespace -n promtail promtail grafana/promtail
helm install --create-namespace -n loki loki grafana/loki

kubectl port-forward -n kube-prometheus-stack deployment/kube-prometheus-stack-grafana 3000:3000
open http://localhost:3000
```

## GKE Setup

```bash
make create-gke-cluster

# follow the local setup instructions 

make destroy-gke-cluster
```

## Maintainer

M.-Leander Reimer (@lreimer), <mario-leander.reimer@qaware.de>

## License

This software is provided under the MIT open source license, read the `LICENSE`
file for details.


