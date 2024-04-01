BASE_PATH="/home/ubuntu"

LOG_PATH="$BASE_PATH/log"
DEPLOY_LOG="$LOG_PATH/deploy.log"
CURRENT_TIME=$(date +%c)

CURRENT_PID=$(lsof -t -i tcp:8080)

if [ -z "$CURRENT_PID" ]; then
    echo "$CURRENT_TIME > $CURRENT_PID pid 에서 실행중인 애플리케이션이 없습니다" >> $DEPLOY_LOG
else
  kill -15 "$CURRENT_PID"
  echo "$CURRENT_TIME > $CURRENT_PID pid 에서 실행중인 애플리케이션을 종료합니다" >> $DEPLOY_LOG
fi
