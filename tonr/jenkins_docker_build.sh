
container_id=`docker ps -a | grep -i sparklr-auth | awk '{print $1}'`
if [ ! -n "$container_id" ];then
    echo "not fount container of sparklr-auth"
else
    cid=`docker ps | grep -i "$container_id"`
    if [ -n cid ];then
        echo "stop container $container_id"
        docker stop "$container_id"
    fi
    echo "remove container $container_id"
    docker rm "$container_id"
fi

image_id=`docker images | grep -i sparklr-auth | awk '{print $3}'`
if [ ! -n "$image_id" ];then
    echo "not found image of sparklr-auth"
else
    echo "remove image $image_id"
    docker rmi "$image_id" -f
fi

cd sparklr-auth-server/docker && docker build . -t sparklr-auth

docker run -d -p 8082:8080 --name=sparklr-auth-server sparklr-auth




container_id=`docker ps -a | grep -i sparklr-res | awk '{print $1}'`
if [ ! -n "$container_id" ];then
   echo "not fount container of sparklr-res"
else
    cid=`docker ps | grep -i "$container_id"`
    if [ -n cid ];then
        echo "stop container $container_id"
        docker stop "$container_id"
    fi
    echo "remove container $container_id"
    docker rm "$container_id"
fi

image_id=`docker images | grep -i sparklr-res | awk '{print $3}'`
if [ ! -n "$image_id" ];then
   echo "not found image of sparklr-res"
else
    echo "remove image $image_id"
    docker rmi "$image_id" -f
fi

cd sparklr-resource-server/docker && docker build . -t sparklr-res

docker run -d -p 8086:8080 --name=sparklr-res-server sparklr-res


container_id=`docker ps -a | grep -i tonr | awk '{print $1}'`
if [ ! -n "$container_id" ];then
    echo "not fount container of tonr"
else
    cid=`docker ps | grep -i "$container_id"`
    if [ -n cid ];then
        echo "stop container $container_id"
        docker stop "$container_id"
    fi
    echo "remove container $container_id"
    docker rm "$container_id"
fi

image_id=`docker images | grep -i tonr | awk '{print $3}'`
if [ ! -n "$image_id" ];then
    echo "not found image of tonr"
else
    echo "remove image $image_id"
    docker rmi "$image_id" -f
fi

cd tonr/docker && docker build . -t tonr

docker run -d -p 8083:8080 --name=tonr tonr
