/*

GPS Shield Example

This example shows an application on 1Sheeld's GPS shield.

By using this example, you can send an SMS when the smartphone
is in a hundred meters distance from a fixed coordinates you
provide.

OPTIONAL:
To reduce the library compiled size and limit its memory usage, you
can specify which shields you want to include in your sketch by
defining CUSTOM_SETTINGS and the shields respective INCLUDE_ define. 

*/

#define CUSTOM_SETTINGS
#define INCLUDE_SMS_SHIELD
#include <OneSheeld.h>
 // Define a variable to save the ADC value 
const int ledPin = 9; // The number of the LED pin
const int ldrPin = A0;
void setup() {
Serial.begin(9600);
OneSheeld.begin();
pinMode(ledPin, OUTPUT); // Set ledPin into output mode
pinMode(ldrPin,INPUT);
}

void loop(){
int convertValue = analogRead(A0); // Read analog voltage value of A0 port, and save
if(convertValue <=300){
digitalWrite(ledPin, HIGH);
Serial.println("LED ON");
SMS.send("+353876847990", "Your Car has Arrived.");
}
else{
 digitalWrite(ledPin, LOW);
 Serial.println(); 
  }
// Map analog to the 0-255 range, and works as ledPin duty cycle setting
}
