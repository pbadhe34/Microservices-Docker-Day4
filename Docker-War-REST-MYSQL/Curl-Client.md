#API available
#Get http://ip:9090/app/users/update
#Get http://ip:9090/app/users/getUser/{id}
 #Post http://ip:9090/app/users/add


 


curl -d "@data.json"  -H "Content-Type: application/json" -X POST http://192.168.99.100:9090/app/users/add

 

To get the record
curl http://192.168.99.100:9090/app/users/getUser/{id}


  

 
 

 