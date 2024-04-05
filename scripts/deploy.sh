BASE_PATH="/home/ubuntu"

BUILD_JAR_FILE=$(ls $BASE_PATH/namu-server/build/libs/*.jar)

LOG_PATH="$BASE_PATH/log"
DEPLOY_LOG="$LOG_PATH/deploy.log"
APP_LOG="$LOG_PATH/nohup.out"

CURRENT_TIME=$(date +%c)

echo "$CURRENT_TIME >  build 파일 복사" >> $DEPLOY_LOG
DEPLOY_PATH=$BASE_PATH/deploy-jar/
cp "$BUILD_JAR_FILE" $DEPLOY_PATH

SPRING_PROFILES_ACTIVE="dev"
IMAGE_ACCESS_URL="https://namu-bucket.s3.ap-northeast-2.amazonaws.com/"
LOG4J_CONTEXT_SELECTOR="org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"

SENTRY_ENVIRONMENT=$SPRING_PROFILES_ACTIVE nohup java -jar -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -Dimage.access.url=$IMAGE_ACCESS_URL -DLog4jContextSelector=$LOG4J_CONTEXT_SELECTOR -Dlog4j2.enable.threadlocals=true -Dlog4j2.enable.direct.encoders=true "$BUILD_JAR_FILE" > $APP_LOG 2>&1 &
echo "$CURRENT_TIME > build jar 파일 실헹" >> $DEPLOY_LOG

EXECUTED_PROCESS_PID=$(lsof -t -i tcp:8080)
echo "$CURRENT_TIME > 현재 애플리케이션이 $EXECUTED_PROCESS_PID pid 에서 실행중입니다." >> $DEPLOY_LOG

HEALTH_CHECK=$(curl -s http://localhost/health)
echo "$CURRENT_TIME > $HEALTH_CHECK 통과"
