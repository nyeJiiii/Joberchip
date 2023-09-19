#!/bin/bash

PROJECT_NAME="joberchip-be-demo"
DEPLOY_PATH=/home/ubuntu/$PROJECT_NAME/
DEPLOY_LOG_PATH="/home/ubuntu/$PROJECT_NAME/deploy.log"
DEPLOY_ERR_LOG_PATH="/home/ubuntu/$PROJECT_NAME/deploy_err.log"

#####################################################################################################

####################
# API AUTH 배포 부분 #
####################

#API_AUTH_LOG_PATH="/home/ubuntu/$PROJECT_NAME/api-auth.log"
#API_AUTH_JAR_PATH="/home/ubuntu/$PROJECT_NAME/api/auth-v1/build/libs/*.jar"
#API_AUTH_BUILD_JAR=$(ls "$API_AUTH_JAR_PATH")
#API_AUTH_JAR_NAME=$(basename "$API_AUTH_BUILD_JAR")
#
#echo "==== auth api server 배포 시작 : $(date +%c) ====" >> $DEPLOY_LOG_PATH
#
#echo "> auth api server build 파일명 : $API_AUTH_JAR_NAME" >> $DEPLOY_LOG_PATH
#
#echo "> build 파일 복사" >> $DEPLOY_LOG_PATH
#cp "$API_AUTH_BUILD_JAR" $DEPLOY_PATH
#
#echo "> 현재 동작 중인 auth api server pid 체크" >> $DEPLOY_LOG_PATH
#API_AUTH_CURRENT_PID=$(pgrep -f "$API_AUTH_JAR_NAME")
#
#if [ -z "$API_AUTH_CURRENT_PID" ]
#then
#  echo "> 현재 동작 중인 auth api server 존재 X" >> "$DEPLOY_LOG_PATH"
#else
#  echo "> 현재 동작 중인 auth api server 존재 O" >> $DEPLOY_LOG_PATH
#  echo "> 현재 동작 중인 auth api server 강제 종료 진행" >> "$DEPLOY_LOG_PATH"
#  echo "> kill -9 $API_AUTH_CURRENT_PID" >> "$DEPLOY_LOG_PATH"
#  kill -9 "$API_AUTH_CURRENT_PID"
#fi
#
#API_AUTH_DEPLOY_JAR=$DEPLOY_PATH$API_AUTH_JAR_NAME
#
#echo "> ADMIN_DEPLOY_JAR 배포" >> $API_AUTH_LOG_PATH
#nohup java -Xms128m -Xmx196m -jar -Dspring.profiles.active=prod "$API_AUTH_DEPLOY_JAR" >> $API_AUTH_LOG_PATH 2> $DEPLOY_ERR_LOG_PATH &
#
#sleep 3
#
#echo "> auth api server 배포 종료 : $(date +%c)" >> $DEPLOY_LOG_PATH
#

#####################################################################################################


######################
# API SERVER 배포 부분 #
######################

API_SERVER_LOG_PATH="/home/ubuntu/$PROJECT_NAME/api-server.log"
API_SERVER_JAR_PATH="/home/ubuntu/$PROJECT_NAME/api/server-v1/build/libs/*.jar"
API_SERVER_BUILD_JAR=$(ls $API_SERVER_JAR_PATH)
API_SERVER_JAR_NAME=$(basename $API_SERVER_BUILD_JAR)

echo "==== api server 배포 시작 : $(date +%c) ====" >> $DEPLOY_LOG_PATH

echo "> api-auth build 파일명 : $API_SERVER_JAR_NAME" >> $DEPLOY_LOG_PATH
echo "> application build 파일명 : $API_SERVER_JAR_NAME" >> $DEPLOY_LOG_PATH

echo "> build 파일 복사" >> $DEPLOY_LOG_PATH
cp $API_SERVER_BUILD_JAR $DEPLOY_PATH

echo "> 현재 동작중인 애플리케이션 pid 체크" >> $DEPLOY_LOG_PATH
API_SERVER_CURRENT_PID=$(pgrep -f $API_SERVER_JAR_NAME)

if [ -z $API_SERVER_CURRENT_PID ]
then
  echo "> 현재 동작중인 API SERVER 존재 X" >> $DEPLOY_LOG_PATH
else
  echo "> 현재 동작중인 API SERVER 존재 O" >> $DEPLOY_LOG_PATH
  echo "> 현재 동작중인 API SERVER 강제 종료 진행" >> $DEPLOY_LOG_PATH
  echo "> kill -9 $API_SERVER_CURRENT_PID" >> $DEPLOY_LOG_PATH
  kill -9 $API_SERVER_CURRENT_PID
fi

API_SERVER_DEPLOY_JAR=$DEPLOY_PATH$API_SERVER_JAR_NAME

echo "> API_SERVER_DEPLOY_JAR 배포" >> $API_SERVER_LOG_PATH
nohup java -Xms128m -Xmx196m -jar -Dspring.profiles.active=prod $API_SERVER_DEPLOY_JAR >> $API_SERVER_LOG_PATH 2> $DEPLOY_ERR_LOG_PATH &

sleep 3

echo "> 배포 종료 : $(date +%c)" >> $DEPLOY_LOG_PATH
