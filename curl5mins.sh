#!/bin/sh  
while true  
do  
  curl http://localhost:8080/api/endpoint
  curl http://localhost:8080/email
  curl http://localhost:8080/payment
  curl http://localhost:8080/stockavailability
  curl http://localhost:8080/refund
  curl http://localhost:8080/api/endpoint/generic
sleep 180
done
