#!/bin/bash

set -e  # 오류 발생 시 스크립트 중지

echo "Terminate Local Kafka..."
docker-compose docker down
