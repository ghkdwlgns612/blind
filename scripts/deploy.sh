#!/bin/bash

export MYSQL_URL="jdbc:mysql://blind-db.crgwtx9lu2zf.ap-northeast-2.rds.amazonaws.com:3306/blind?characterEncoding=UTF-8&serverTimezone=Asia/Seoul"
export MYSQL_ID="admin"
export MYSQL_PW="ahstmxj1!"
export OAUTH2_42_ID="d117132071d486ad864ed53b90a14fb2cc0d8ec90465e5fafb1e56a232c6dc34"
export OAUTH2_42_SECRET="4459b3f7144edf7af62a02865b4c930fe70332827b0b52957060be38a8d55a36"
export OAUTH2_42_URL="https://api.42blind.com/login/oauth2/code/intra42"

BUILD_JAR=$(ls /home/ec2-user/jenkins/build/libs/blind-*-SNAPSHOT.jar)     # jar가 위치하는 곳
JAR_NAME=$(basename $BUILD_JAR)

echo "> build 파일명!! : $JAR_NAME"

echo "> build 파일 복사"
DEPLOY_PATH=/home/ec2-user/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인!!"
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
nohup java -jar $DEPLOY_JAR &