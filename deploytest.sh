#!/bin/bash

# ----------------------------------------------------------
#
# 功能：进行部署测试的工程名称
# 将.tar.gz包解压到target/deploytest目录，并启动
# 如果之前已经启动了，会杀掉进程，删除目录，重新解压部署
#
# ----------------------------------------------------------

# 部署测试的目标工程
PROJ_NAME=""
# 部署目录
DEPLOY_PATH="deploytest"

function usage(){
    echo "INFO:必须指定工程名称！"
    echo "deploytest.sh {project name}"
    echo "for example：deploytest.sh tools-service-abf"
}

if [ $# -eq 0 ]; then
    usage ;
    exit ;
else
    PROJ_NAME=$1
fi

echo "对工程[$PROJ_NAME]进行部署测试"


# 定位到 target目录
cd $PROJ_NAME/target

# 先清理之前的部署，杀掉进程，删除部署目录($DEPLOY_PATH)
PIDS=`ps -ef | grep java | grep "$PROJ_NAME" | grep -v grep |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "INFO: The $PROJ_NAME not started!"
else
    echo "INFO: The $PROJ_NAME is started! PID is : $PIDS , now kill and remove it ."
    kill -9 $PIDS > /dev/null 2>&1
    rm -rf $DEPLOY_PATH  
fi

# (重新)解压tar.gz
echo "INFO: build deploy test path : $DEPLOY_PATH "
mkdir $DEPLOY_PATH
chmode 775 $DEPLOY_PATH
tar -zxvf $PROJ_NAME*.tar.gz -C $DEPLOY_PATH

# 到bin目录
cd $DEPLOY_PATH/$PROJ_NAME*/bin

# 启动start.sh
./start.sh

echp "INFO: current path is : `pwd` "
echo "INFO: test deploy [$PROJ_NAME] OK!"

tail -f ../logs/stdout.log
