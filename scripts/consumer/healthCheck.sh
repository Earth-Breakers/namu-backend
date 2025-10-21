#!/bin/bash
echo "> Health check 시작"

for RETRY_COUNT in {1..15}
do
  HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/health)

  if [ "$HTTP_STATUS" -eq 200 ]
  then
    echo "> Health check 성공. 상태 코드: $HTTP_STATUS"
    break
  else
    echo "> Health check 실패. 상태 코드: $HTTP_STATUS"
  fi

  if [ "$RETRY_COUNT" -eq 10 ]
  then
    echo "> Health check 실패. "
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done

CURRENT_TIME=$(date +%c)
EXECUTED_PROCESS_PID=$(lsof -t -i tcp:8080)
echo "$CURRENT_TIME > 현재 애플리케이션이 $EXECUTED_PROCESS_PID pid 에서 실행중입니다."
exit 0
