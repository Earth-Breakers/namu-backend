#!/bin/bash

# 현재 스크립트가 위치한 디렉토리로 이동
# shellcheck disable=SC2164
cd "$(dirname "$0")"

docker-compose stop
