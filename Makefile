web_install:
	cd reconciliation-web-front && npm i && npm run start

web:
	cd reconciliation-web-front && npm run start

web_open:
	cd reconciliation-web-front && ng serve --open

docker_build:
	 mvn clean install && docker-compose build --no-cache

docker_run:
	docker-compose up -d && watch "docker ps -a"

docker_rm_run:
	docker-compose rm -f -s -v && docker-compose up -d && watch "docker ps -a"
