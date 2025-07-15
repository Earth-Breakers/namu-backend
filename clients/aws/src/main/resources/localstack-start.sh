#!/bin/bash

# shellcheck disable=SC2164
cd "$(dirname "$0")"

export SERVICES=s3 # 사용하는 서비스들을 필요 시 추가
export TMPDIR=/private$TMPDIR # temp directory 를 별도로 지정
export PORT_WEB_UI=8080

docker-compose up -d

docker-compose exec localstack bash
docker exec -it localstack bash
