GITHUB_USER ?= lreimer

.PHONY: spring-boot-app

prepare-gke-cluster:
	@gcloud config set compute/zone europe-west1-b
	@gcloud config set container/use_client_certificate False

create-gke-cluster:
	@gcloud container clusters create gke-observability --num-nodes=3 --enable-autoscaling --min-nodes=3 --max-nodes=7 --cluster-version=1.22
	@kubectl create clusterrolebinding cluster-admin-binding --clusterrole=cluster-admin --user=$$(gcloud config get-value core/account)
	@kubectl cluster-info

spring-boot-app:
	cd spring-boot-app && mvn package
	docker build -t spring-boot-app:0.0.1 spring-boot-app/

tag-and-push: spring-boot-app
	docker tag spring-boot-app:0.0.1 lreimer/spring-boot-app:0.0.1
	docker push lreimer/spring-boot-app:0.0.1

delete-gke-cluster:
	@gcloud container clusters delete gke-observability --async --quiet
