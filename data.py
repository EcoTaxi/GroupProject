import serial

from firebase import firebase
from serial import SerialException

ser = serial.Serial('COM6', baudrate=9600, timeout=1)

try:

    arduino = ser.readline();
    print(arduino)
    firebase = firebase.FirebaseApplication('https://ecotaxiphoneapp-9e80f.firebaseio.com/', None)
    result = firebase.post('/location', {'car': arduino})

# current location name

# insert record

except serial.SerialException:
    print("ERROR")