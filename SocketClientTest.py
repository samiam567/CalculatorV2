import jpysocket
import socket
import math
import subprocess
import threading
from time import sleep

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

def launch_calculator():
    subprocess.run('java -jar CalculatorV2.jar --socket-server 45623', shell=True)

if __name__ == '__main__':
    print("launching calculator");
    a = threading.Thread(target=launch_calculator, args=())
    a.start()

    sleep(3);

    print("attempting to connect");
    connectToCalculator(45623);

    try:
    
        while True:
            inp = input("what do you wanna calculate?")
            print(calculate(inp))
            
            if (inp == "quit" or input == "exit" or input == "q"):
                break;
    except KeyboardInterrupt:
        pass;

    calculate("quit")
    disconnectFromCalculator()
    a.join()