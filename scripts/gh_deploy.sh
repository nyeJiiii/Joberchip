#!/bin/bash

PROJECT_NAME="joberchip-be-demo"
DEPLOY_PATH=/home/ubuntu/$PROJECT_NAME/
DEPLOY_LOG_PATH="/home/ubuntu/$PROJECT_NAME/deploy.log"
DEPLOY_ERR_LOG_PATH="/home/ubuntu/$PROJECT_NAME/deploy_err.log"

#####################################################################################################

######################
# AUTH SERVER 배포 부분 #
######################

AUTH_SERVER_LOG_PATH="/home/ubuntu/$PROJECT_NAME/api-auth.log"
AUTH_SERVER_JAR_PATH="/home/ubuntu/$PROJECT_NAME/api/auth-v1/build/libs/*.jar"
AUTH_SERVER_BUILD_JAR=$(ls $AUTH_SERVER_JAR_PATH)
AUTH_SERVER_JAR_NAME=$(basename $AUTH_SERVER_BUILD_JAR)

echo "==== auth server 배포 시작 : $(date +%c) ====" >> $DEPLOY_LOG_PATH

echo "> auth server build 파일명 : $AUTH_SERVER_JAR_NAME" >> $DEPLOY_LOG_PATH

echo "> build 파일 복사" >> $DEPLOY_LOG_PATH
cp $AUTH_SERVER_BUILD_JAR $DEPLOY_PATH

echo "> 현재 동작 중인 애플리케이션 pid 체크" >> $DEPLOY_LOG_PATH
AUTH_SERVER_CURRENT_PID=$(pgrep -f $AUTH_SERVER_JAR_NAME)

if [ -z $AUTH_SERVER_CURRENT_PID ]
then
  echo "> 현재 동작중인 AUTH SERVER 존재 X" >> $DEPLOY_LOG_PATH
else
  echo "> 현재 동작중인 AUTH SERVER 존재 O" >> $DEPLOY_LOG_PATH
  echo "> 현재 동작중인 AUTH SERVER 강제 종료 진행" >> $DEPLOY_LOG_PATH
  echo "> kill -9 $AUTH_SERVER_CURRENT_PID" >> $DEPLOY_LOG_PATH
  kill -9 $AUTH_SERVER_CURRENT_PID
fi

AUTH_SERVER_DEPLOY_JAR=$DEPLOY_PATH$AUTH_SERVER_JAR_NAME

echo "> AUTH_SERVER_DEPLOY_JAR 배포" >> $AUTH_SERVER_LOG_PATH
nohup java -Xms128m -Xmx196m -jar -Dspring.profiles.active=dev $AUTH_SERVER_DEPLOY_JAR >> $AUTH_SERVER_LOG_PATH 2> $DEPLOY_ERR_LOG_PATH &

sleep 3

echo "> 배포 종료 : $(date +%c)" >> $DEPLOY_LOG_PATH

#####################################################################################################


######################
# API SERVER 배포 부분 #
######################

API_SERVER_LOG_PATH="/home/ubuntu/$PROJECT_NAME/api-server.log"
API_SERVER_JAR_PATH="/home/ubuntu/$PROJECT_NAME/api/server-v1/build/libs/*.jar"
API_SERVER_BUILD_JAR=$(ls $API_SERVER_JAR_PATH)
API_SERVER_JAR_NAME=$(basename $API_SERVER_BUILD_JAR)

echo "==== api server 배포 시작 : $(date +%c) ====" >> $DEPLOY_LOG_PATH

echo "> api server build 파일명 : $API_SERVER_JAR_NAME" >> $DEPLOY_LOG_PATH

echo "> build 파일 복사" >> $DEPLOY_LOG_PATH
cp $API_SERVER_BUILD_JAR $DEPLOY_PATH

echo "> 현재 동작 중인 애플리케이션 pid 체크" >> $DEPLOY_LOG_PATH
API_SERVER_CURRENT_PID=$(pgrep -f $API_SERVER_JAR_NAME)

if [ -z $API_SERVER_CURRENT_PID ]
then
  echo "> 현재 동작 중인 API SERVER 존재 X" >> $DEPLOY_LOG_PATH
else
  echo "> 현재 동작 중인 API SERVER 존재 O" >> $DEPLOY_LOG_PATH
  echo "> 현재 동작 중인 API SERVER 강제 종료 진행" >> $DEPLOY_LOG_PATH
  echo "> kill -9 $API_SERVER_CURRENT_PID" >> $DEPLOY_LOG_PATH
  kill -9 $API_SERVER_CURRENT_PID
fi

API_SERVER_DEPLOY_JAR=$DEPLOY_PATH$API_SERVER_JAR_NAME

echo "> API_SERVER_DEPLOY_JAR 배포" >> $API_SERVER_LOG_PATH
nohup java -Xms128m -Xmx196m -jar -Dspring.profiles.active=dev $API_SERVER_DEPLOY_JAR >> $API_SERVER_LOG_PATH 2> $DEPLOY_ERR_LOG_PATH &

sleep 3

echo "> 배포 종료 : $(date +%c)" >> $DEPLOY_LOG_PATH
