BASE_PATH="/home/ubuntu"

BUILD_JAR_FILE="$BASE_PATH/namu-server/build/libs/*.jar"

DEPLOY_LOG="$BASE_PATH/deploy.log"
APP_LOG="$BASE_PATH/application.log"

CURRENT_TIME=$(date +%c)

echo "> build 파일 복사" >> $DEPLOY_LOG
DEPLOY_PATH=$BASE_PATH/deploy-jar/
cp "$BUILD_JAR_FILE" $DEPLOY_PATH

cd $BASE_PATH

SPRING_PROFILES_ACTIVE="dev"
IMAGE_ACCESS_URL="https://namu-bucket.s3.ap-northeast-2.amazonaws.com/"
LOG4J_CONTEXT_SELECTOR="org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"
LOG4J2_ENABLE_THREADLOCALS="true"
LOG4J2_ENABLE_DIRECT_ENCODERS="true"

COMMAND="java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
-DimageAccessUrl=${IMAGE_ACCESS_URL} \
-DLog4jContextSelector=${LOG4J_CONTEXT_SELECTOR} \
-Dlog4j2.enable.threadlocals=${LOG4J2_ENABLE_THREADLOCALS} \
-Dlog4j2.enable.direct.encoders=${LOG4J2_ENABLE_DIRECT_ENCODERS} \
${DEPLOY_PATH}"

nohup $COMMAND > $APP_LOG 2>&1 &
echo "> build jar 파일 실헹" >> $DEPLOY_LOG

CURRENT_PID=$(pgrep -f $DEPLOY_PATH)
echo "$TIME_NOW > 현재 애플리케이션이 $CURRENT_PID pid 에서 실행중입니다." >> $DEPLOY_LOG
