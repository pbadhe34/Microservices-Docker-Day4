**************************

Another way to add records at startup of Mysql

Simply just put your Data.sql file (dbcreation.sql) in a folder (ie. /mysql_init) and add the folder as a volume like this:

volumes:
  - /mysql_init:/docker-entrypoint-initdb.d
The MySQL image will execute all .sql, .sh and .sql.gz files in /docker-entrypoint-initdb.d on startup.

*********************************

On Windows
docker run --name app-mysql -v /c/Users/Prakash/Docker-User-Service/mysql_init:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD=MyRootPass123 -e MYSQL_DATABASE=userservice -e MYSQL_USER=app-user -e MYSQL_PASSWORD=MyRootPass123 -d mysql:5.6

 On Linux
docker run --name app-mysql -v /mysql_init:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD=MyRootPass123 -e MYSQL_DATABASE=userservice -e MYSQL_USER=app-user -e MYSQL_PASSWORD=MyRootPass123 -d mysql:5.6

docker logs app-mysql
