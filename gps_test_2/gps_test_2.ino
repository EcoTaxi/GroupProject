

#define CUSTOM_SETTINGS
#define INCLUDE_GPS_SHIELD
#define INCLUDE_SMS_SHIELD

/* Include 1Sheeld library.*/
#include <OneSheeld.h>

/* Define a boolean flag. */
boolean isInRange = false;
?*
int ledPin = 13;
void setup() 
{
  /* Start communication.*/
  OneSheeld.begin();
  pinMode(ledPin,OUTPUT);
}

void loop()
{
   /* Always check if the smartphone's GPS and a given latitude and longitude are in a range of 100 meters. */
  if(GPS.isInRange(51.933837,-8.569217,100))
  { 
    if(!isInRange)
    {
      /* Send SMS. */
      SMS.send("+353851973623","Your Car is Close by.");
      
      digitalWrite(ledPin,HIGH);
      isInRange = true;
    }
    
  }
  else 
  {
    if(isInRange)
    {
      /* Send SMS. */ 
      SMS.send("+353851973623","Smartphone is not In Range.");
      digitalWrite(ledPin,LOW);
      isInRange = false;
    }
    
  }
}
