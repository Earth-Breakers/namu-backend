BASE_PATH="/home/ubuntu"
BASE_SERVER_PATH=$BASE_PATH/namu-server

CURRENT_TIME=$(date +%c)

BUILD_JAR_FILE=$(ls $BASE_SERVER_PATH/*.jar)
JAR_NAME=$(basename "$BUILD_JAR_FILE")
echo "$CURRENT_TIME > build 파일명: $JAR_NAME"

LOG_PATH="$BASE_SERVER_PATH/log"
DEPLOY_LOG="$LOG_PATH/deploy.log"
APP_LOG="$LOG_PATH/nohup.out"

CURRENT_TIME=$(date +%c)

echo "$CURRENT_TIME >  build 파일 복사" >> $DEPLOY_LOG
DEPLOY_PATH=$BASE_SERVER_PATH/deploy-jar/
cp "$BUILD_JAR_FILE" $DEPLOY_PATH

echo "$CURRENT_TIME > namu-api-server-deploy.jar 교체" >> $DEPLOY_LOG
CP_JAR_PATH=$DEPLOY_PATH$JAR_NAME
APPLICATION_JAR_NAME=namu-api-server-deploy.jar
APPLICATION_JAR=$DEPLOY_PATH$APPLICATION_JAR_NAME

echo "$CURRENT_TIME > 심볼릭 링크 설정" >> $DEPLOY_LOG
ln -Tfs "$CP_JAR_PATH" $APPLICATION_JAR

source $BASE_PATH/.profile

SPRING_PROFILES_ACTIVE="dev"
IMAGE_ACCESS_URL="https://namu-bucket.s3.ap-northeast-2.amazonaws.com/"
LOG4J_CONTEXT_SELECTOR="org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"

SENTRY_ENVIRONMENT=$SPRING_PROFILES_ACTIVE nohup java -jar \
  -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE \
  -Dimage.access.url="$IMAGE_ACCESS_URL" \
  -DSTORAGE_DATABASE_CORE_DB_URL="$STORAGE_DATABASE_CORE_DB_URL" \
  -DSTORAGE_DATABASE_CORE_DB_NAME="$STORAGE_DATABASE_CORE_DB_NAME" \
  -DSTORAGE_DATABASE_CORE_DB_USERNAME="$STORAGE_DATABASE_CORE_DB_USERNAME" \
  -DSTORAGE_DATABASE_CORE_DB_PASSWORD="$STORAGE_DATABASE_CORE_DB_PASSWORD" \
  -DSENTRY_DSN="$SENTRY_DSN" \
  -DLog4jContextSelector=$LOG4J_CONTEXT_SELECTOR \
  -Dlog4j2.enable.threadlocals=true \
  -Dlog4j2.enable.direct.encoders=true \
  "$APPLICATION_JAR" > $APP_LOG 2>&1 &

echo "$CURRENT_TIME > build jar 파일 실헹" >> $DEPLOY_LOG
