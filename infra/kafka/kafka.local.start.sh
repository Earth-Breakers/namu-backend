#!/bin/bash

set -e  # 오류 발생 시 스크립트 중지

echo "Starting Local Kafka..."
docker-compose -f docker-compose.kafka.local.yml up -d
