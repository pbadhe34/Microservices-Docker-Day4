Step by step docker with Tomcat 8.0

For Tomcat8 with Java8 version image 

docker run -d -p 8090:8080 --name tomcat8 tomcat:8.0-jre8

docker cp tomcat-users.xml tomcat8:/usr/local/tomcat/conf/tomcat-users.xml
 
docker cp ./user-service.war tomcat8:/usr/local/tomcat/webapps/user-service.war


*********************************

**********************************


docker exec -t -i tomcat8 /bin/bash

->java -version -->1.8.0_151
->cd conf

->cat tomcat-users.xml

For attach to tomcat container 

docker attach tomcat

apt-get update
apt-get install vim

cd /usr/local/tomcat/conf
vim tomcat-users.xml

********************************

Build the docker image from DockerFile in current dir

docker build -t tomcat-server .

Run the tomcat insoide the docker container
docker run -d -p 8090:8080 --name tomcat tomcat-server
docker run -d -p 127.0.0.1:8090:8080 --name tomcat tomcat-server

docker run -d -p 9090:8080 --name tomcat tomcat-server

The host ip is 192.168.99.100

curl http://192.168.99.100:9090
 
To deploy a new web app to Tomcat server in the Conatiner.

docker cp TestApp.war tomcat:/usr/local/tomcat/webapps/TestApp.war


docker inspect --format '{{ .NetworkSettings.Networks.IPAddress }}' tomcat

docker exec -t -i tomcat /bin/bash

->cd conf
->cat tomcat-users.xml 

To exit the container's terminal without stopping it?

exit and CTR+C both stop the container.

To detach the tty without exiting the shell,
# use the escape sequence Ctrl-p + Ctrl-q
# note: This will continue to exist in a stopped state once exited (see "docker ps -a")

Docker stop all containers
Here are some handy shortcuts.
List all containers (only IDs) docker ps -aq.
Stop all running containers. docker stop $(docker ps -aq)
Remove all containers. docker rm $(docker ps -aq)
Remove all images. docker rmi $(docker images -q)

**************************************************************************
********************************************************************
Or to define and build the image

FROM tomcat:8.0-alpine
ADD tomcat-users.xml $CATALINA_HOME/conf

Tomcat 8.5 needs manager.xml to be set in conf/Catalina/localhost
 
Use the image directly, and copy the .war files directly into the appBase directory.  

docker run -d davidcaste/alpine-tomcat:tomcat8

docker run -it --rm davidcaste/alpine-tomcat /opt/tomcat/bin/catalina.sh run

docker run -d --name tomcat8 davidcaste/alpine-tomcat /opt/tomcat/bin/catalina.sh run

docker exec -t -i tomcat8 /bin/bash

java -version -->1.8.092

docker cp ./sample.war tomcat-ci:/opt/tomcat/webapps/sample.war


*************************************************************

Docker ps

Commit the modified container as new image

docker commit <container id>  repository/app:tag

docker commit tomcat8  pbadhe34/my-apps:tomcat-web

Later on you can push this image to docker hub


docker login

docker push  pbadhe34/my-apps:tomcat-web

docker stop tomcat8
docker rm tomcat8

docker run -d -p 8090:8080 --name tomcat8 pbadhe34/my-apps:tomcat-web

************************************************************************************
docker-machine env 
The docker-machine env command outputs the environment variables needed by the docker.exe client binary to know how to connect to a remote docker daemon.

One of the env variables displkayed is

DOCKER_HOST="tcp://192.168.99.100:2376

The host IP in this acse is the ip returned by 
'docker-machine ip'  command

The Docker Toolbox doesn't get as many conveniences as Docker for Windows,.
In Toolbox, nothing will be localhost, and will be 192.168.99.100 by default, since it's running a Linux VM in VirtualBox. and the hopst ip is as returned by 'docker-machine ip' command.

ON Windows with Docker ToolBox with Oracle VM,the host ip is NOT the localhost or 127.0.0.1.It is the docke machine ip as 
 192.168.99.100.

To access the boot2docker VM that docker-machine creates, you can do that by running the 'docker-machine ssh default' command.

-->docker-machine ssh default
-->ifconfig
eth1: inet addr : 192.168.99.100

To exit from docker-machine ssh  shell,type 'exit'.


pwd-->Displays the curremnt directory with path.

On windows the root path is /c/users/Prakash
*********************************

PORT forwading with VirtualBox in Docker ToolBox

To map this host ip 192.168.99.100 to the localhost or 127.0.0.1 or the physical ethernet ip,port forrwarding can be done with 'nginx 'or Docker ToolBox port forwrading configiuation.

In VirtualBox:
Settings
Network
Adapter 1 Advanced Port Forwarding
You will want to add a new Rule
Set HostPort 9090 & GuestPort 8080, be sure to leave HostIP and Guest IP empty.

