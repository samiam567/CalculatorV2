import jpysocket
import socket
import math

s=socket.socket() #Create Socket

def connectToCalculator():
    host='localhost' #Host Name
    port=12334    #Port Number
    s.connect((host,port)) #Connect to socket
    print("Socket Is Connected....")

def calculate(input):
    msgsend=jpysocket.jpyencode(input) #Encript The Msg
    s.send(msgsend) #Send Msg

    msgrecv=s.recv(1024) #Recieve msg
    msgrecv=jpysocket.jpydecode(msgrecv) #Decript msg
    return msgrecv

def disconnectFromCalculator():
    s.close() #Close connection
    print("Connection Closed.")


connectToCalculator();

try:
  
    while True:
        inp = input("what do you wanna calculate?")
        print(calculate(inp))
        
        if (inp == "quit" or input == "exit" or input == "q"):
            break;
except KeyboardInterrupt:
    pass;

disconnectFromCalculator()