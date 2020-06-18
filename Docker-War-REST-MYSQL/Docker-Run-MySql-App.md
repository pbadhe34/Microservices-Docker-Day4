 

Run MySQL 5.6 database server in Docker container:

 
docker run --name app-mysql -e MYSQL_ROOT_PASSWORD=MyRootPass123 -e MYSQL_DATABASE=user_data -e MYSQL_USER=app-user -e MYSQL_PASSWORD=MyRootPass123 -d mysql:5.6
 


docker run --name app-mysql -d mysql:5.6


Check the log to make sure the mysql server is running OK:
 
docker logs app-mysql


To create some initial records and tables in mysql container
 
 
Copy the files from host system into the Docker container context
The cp command can be used to copy files from host to docker container and reverse.
Copy the file from host machine to the container

docker cp table.sql app-mysql:/table.sql
docker cp records.sql app-mysql:/records.sql 

Multiple files contained by the folder src can be copied into the target folder using:

docker cp src/. mycontainer:/target
docker cp mycontainer:/src/. target

 
Login to mysql container
docker exec -it app-mysql bash

 
#ls
#pwd

Run mysql commands on the MySQl database inside the container

mysql -uroot -p user_data -e "SELECT DATABASE()"

mysql -uroot -p user_data -e "SELECT VERSION()"


To import and execute sql  script in database container.
Run these from bash terminal to import sql script file into Database

mysql -uroot -p user_data < table.sql

mysql -uroot -p user_data < records.sql  
 

Query the databse and records added
mysql -uroot -p user_data -e "describe appusers"  

mysql -uroot -p user_data -e "describe hibernate_sequence"  

mysql -uroot -p user_data  -e "select * from appusers"  

mysql -uroot -p user_data  -e "select * from user_data.appusers"  


mysql -uroot -p user_data  -e "select * from hibernate_sequence"  


mysql -uroot -p user_data -e "commit"  

 ///mysql -uroot -p user_data  -e "DROP table users"   

mysql -uroot -p user_data -e "SELECT COUNT(Id) AS Id FROM user_data.appusers"


exit

********************************
 

Now stop the mysql container database server and remove the container.
Rerun thje mysql container again ad verify the records created earlier.

docker stop app-mysql

docker rm app-mysql


docker run --name app-mysql -e MYSQL_ROOT_PASSWORD=MyRootPass123 -e MYSQL_DATABASE=user_data -e MYSQL_USER=app-user -e MYSQL_PASSWORD=MyRootPass123 -d mysql:5.6
 

Check the log to make sure the mysql server is running OK:
 
docker logs app-mysql

 
Login to mysql container
docker exec -it app-mysql bash

 
#ls
#pwd

To import and execute sql  script in database container.
Run these from bash terminal to import sql script file into Database

 
Query the databse for earlier  records added
mysql -uroot -p user_data -e "describe appusers"  

mysql -uroot -p user_data -e "describe hibernate_sequence"  

mysql -uroot -p user_data  -e "select * from appusers"  

mysql -uroot -p user_data  -e "select * from hibernate_sequence"  


The records doesnot exist..They were lost when container was stopped abd destroyed.!
The container is an In Memory object which doesnots ave the values across re-initialization.

Stop and remove the MySQL cotainer.

docker stop app-mysql

docker rm app-mysql


To maintain the data/state of container across the initialization cycle use external file system from host as mounted storage. This is called as Volume in docker.

Run MySQL 5.6 in Docker container with external volume: 

On windows map/Mount the drive or file system to docker as shared dirctory.

Create a data directory on a suitable volume on your host
 system, e.g. /c/Users/Admin/mysql/mysqldata.


The Volume specified here is used across the containers start and stop and will maintiain the database state across containers and across start and stop and removal operations.

Volume dir : /c/Docker-Share/data/mysqldata

On Windows to run with mounted dir as volume

docker run --name app-mysql --volume /c/Docker-Share/data/mysqldata:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=MyRootPass123 -e MYSQL_DATABASE=user_data -e MYSQL_USER=app-user -e MYSQL_PASSWORD=MyRootPass123 -d mysql:5.6
 



On Linux : The /mysql/data volume directory from ROOT directory gets created

docker run --name app-mysql -V /mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=MyRootPass123 -e MYSQL_DATABASE=user_data -e MYSQL_USER=app-user -e MYSQL_PASSWORD=MyRootPass123 -d mysql:5.6

docker logs app-mysql

Repeat the Database records addition process

Copy the file from host machine to the container

docker cp table.sql app-mysql:/table.sql
docker cp records.sql app-mysql:/records.sql 

Multiple files contained by the folder src can be copied into the target folder using:

docker cp src/. mycontainer:/target
docker cp mycontainer:/src/. target

 
Login to mysql container
docker exec -it app-mysql bash

 
#ls
#pwd

Run mysql commands on the MySQl database inside the container

mysql -uroot -p user_data -e "SELECT DATABASE()"

mysql -uroot -p user_data -e "SELECT VERSION()"


To import and execute sql  script in database container.
Run these from bash terminal to import sql script file into Database

mysql -uroot -p user_data < table.sql

mysql -uroot -p user_data < records.sql  
 

Query the databse and records added
mysql -uroot -p user_data -e "describe appusers"  

mysql -uroot -p user_data -e "describe hibernate_sequence"  

mysql -uroot -p user_data  -e "select * from appusers"  

mysql -uroot -p user_data  -e "select * from user_data.appusers"  


mysql -uroot -p user_data  -e "select * from hibernate_sequence"  


mysql -uroot -p user_data -e "drop table user_data.appusers"  

mysql -uroot -p user_data -e "commit"  

 ///mysql -uroot -p user_data  -e "DROP table users"   

mysql -uroot -p user_data -e "SELECT COUNT(Id) AS Id FROM user_data.appusers"


exit

******************************************

To build the api-db-war app as container image 

Run user-service application in Docker container tomcat and link to app-mysql:

  docker build -t user-db-app -f Dockerfile-Tomcat-WebApp.txt .

 To build with version tag

docker build -t user-db-app:v1 -f Dockerfile-Tomcat-WebApp.txt .

To Run the user-db-app image in detacheed mode with host port mapping/forwarding with link to Mysql container app-mysql as dbserver

docker run -p 9090:8090 --name user-app --link app-mysql:dbserver -d user-db-app  

Check the application log by
 
docker logs user-app 
 
docker exec -it user-app bash

Open in browser  

http://localhost:9090/app/users/update 

http://localhost:9090/app/users/getUser/1

To post new data with post method

http://localhost:9090/app/users/add

 After successful testing of user-db-app, push this image to docker-hub


 docker tag user-db-app pbadhe34/training:user-app
 docker login

 docker push pbadhe34/training:user-app

***********************************



Run the webapp and take snapshot of Tomcat contaier as image

Start the Tomcat8 server container  with host port mapping/forwarding   

docker run -d -p 8090:8080 --name tomcat8 tomcat:8.0-jre8

Copy the tomcat config files from host system into tomcat container 

docker cp tomcat-users.xml tomcat8:/usr/local/tomcat/conf/tomcat-users.xml

docker cp server.xml tomcat8:/usr/local/tomcat/conf/server.xml
 
docker cp ./rest-db-war.war tomcat8:/usr/local/tomcat/webapps/rest-db.war

docker stop tomcat8
docker start tomcat8

Commit the Docker container into an image
docker commit tomcat8  pbadhe34/training:rest-users

docker stop tomcat8

docker rm tomcat8


docker run -p 8090:8080 --name user-app --link app-mysql:localhost -d pbadhe34/my-apps:user-service
  
****************************************************************
To run commands on mysql container from Oustide the container from host system
docker exec app-mysql sh -c 'exec mysql user_data  -uroot -p"MyRootPass123" < table.sql'

docker exec app-mysql sh -c 'exec mysql -p user_data  -uroot < table.sql'

docker exec app-mysql sh -c 'exec mysql -p user_data  -uroot < /c/Users/Prakash/table.sql'

docker exec app-mysql sh -c 'exec pwd'

docker exec app-mysql sh -c 'exec mysql -V' 

Get the SQL DUMP to out file
docker exec app-mysql sh -c 'exec mysqldump user_data  -uroot -p"MyRootPass123" > /c/Users/Prakash/table.sql' 

 
docker exec app-mysql sh -c  "mysql -uroot -p user_data  -e 'create table appusers(id bigint not null, username varchar(255),  salary bigint , primary key (id)) engine=MyISAM;'"  


docker exec app-mysql sh -c   "mysql -uroot -p user_data  -e "create table hibernate_sequence;"

docker exec app-mysql sh -c  "mysql -uroot -p user_data  -e "insert into hibernate_sequence values ( 1 );"


Run select query
docker exec app-mysql sh -c 'exec mysql SELECT * from user_data.appusers AS Id FROM user_data .appusers -uroot -p"MyRootPass123"'
 

docker exec app-mysql sh -c 'exec mysqldump user_data -uroot -p"MyRootPass123"' > /c/Users/Prakash/records.sql

 

**************************
****************************************************************


Another way to add records at startup

Simply just put your Data.sql file (dbcreation.sql) in a folder (ie. /mysql_init) and add the folder as a volume like this:

volumes:
  - /mysql_init:/docker-entrypoint-initdb.d
The MySQL image will execute all .sql, .sh and .sql.gz files in /docker-entrypoint-initdb.d on startup.

*********************************



