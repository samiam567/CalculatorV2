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

def launch_calculator(port):
    subprocess.run(f"java -jar CalculatorV2.jar --socket-server {port} python", shell=True)

if __name__ == '__main__':

    succesfulconnection = False;

    port = 23414;


    
    a = threading.Thread(target=launch_calculator, args=([port]))
    
    print("attempting to connect");
    
    try:
        connectToCalculator(port);
        succesfulconnection = True;
    except  (ConnectionRefusedError, OSError) as e:
        print("connection failed. launching calculator and retrying...")
        a.start()

    if not succesfulconnection:
        for i in range(0,10):
            try:
                connectToCalculator(port);
                succesfulconnection = True;
                break;
            except ConnectionRefusedError: # calculator has not yet established the server
                print("connection failed. retrying...")
                sleep(1);

    

    if (succesfulconnection):
        try:
            while True:
                inp = input("what do you wanna calculate?")
                print(calculate(inp))
                
                if (inp == "quit" or inp == "exit" or "a" == "q"):
                    break;
        except KeyboardInterrupt:
            pass;

        calculate("quit")
        disconnectFromCalculator()
    else:
        print("connection timed out")

    a.join()