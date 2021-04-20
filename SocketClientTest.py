import jpysocket
import socket
import math
import os

s=socket.socket() #Create Socket

def connectToCalculator(port=45623):
    host='localhost' #Host Name
        #Port Number
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


if __name__ == '__main__':

    out = subprocess.run('java -jar CalculatorV2 --verbose-output --socket-server 45623', shell=True)

    connectToCalculator(45623);

    try:
    
        while True:
            inp = input("what do you wanna calculate?")
            print(calculate(inp))
            
            if (inp == "quit" or input == "exit" or input == "q"):
                break;
    except KeyboardInterrupt:
        pass;

    disconnectFromCalculator()