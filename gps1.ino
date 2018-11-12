  
/*
  Arduino GPS Shield Tutorial: Distance Calculator
 
  This project shows an application on 1Sheeld's GPS shield.
 
  By using this project, you can calculate the distance 
  between your current location and the desired destination 
  by using Accelerometer shield from 1Sheeld.
  
  OPTIONAL:
  To reduce the library compiled size and limit its memory usage, you
  can specify which shields you want to include in your sketch by
  defining CUSTOM_SETTINGS and the shields respective INCLUDE_ define.
*/
 
/* Include required shields */
#define CUSTOM_SETTINGS
#define INCLUDE_VOICE_RECOGNIZER_SHIELD
#define INCLUDE_TEXT_TO_SPEECH_SHIELD
#define INCLUDE_GPS_SHIELD
#define INCLUDE_TERMINAL_SHIELD
 
/* Include 1Sheeld library. */
#include <OneSheeld.h>
 
/* Variable to save number of voice commands received */
int counter = 0;
 
/* Char array to hold the voice recognized latitude */
char *voiceinput_latitude_char;
 
/* Char array to hold the voice recognized longitude */
char *voiceinput_longitude_char;
 
/* Float variable to hold the voice recognized latitude */
float voiceinput_latitude;
 
/* Float variable to hold the voice recognized longitude */
float voiceinput_longitude;
 
/* Float variable to hold the calculated distance in km */
float distance;
 
/* Char array variable to hold the calculated distance in km */
char distance_char[10];
 
/* String variable to hold the calculated distance in km */
String distance_string;
 
void setup () 
{
  //Initialize the communication between 1Sheeld's Arduino Voice Recognition Shield and Arduino
  OneSheeld.begin();
}
 
void loop ()
{       
        /* check if 1Sheeld's Arduino Voice Recognition Shield received a new command,
            this will be alwayes true after the first command received */
        if(VoiceRecognition.isNewCommandReceived())
        {
          /* Enter this block of code first to get the latitude */
          if(counter == 0)
          {
            /* Print to the Terminal shield */
            Terminal.println("getting latitude ....");
 
            /* Get the latitude value as char array */
            voiceinput_latitude_char = VoiceRecognition.getLastCommand();
 
            /* Convert the latitude char array value into float */
            voiceinput_latitude = atof(voiceinput_latitude_char);
 
            /* Without the counter, the code will go inside the next getLastCommand function 
              which will return the same previous received command */
            /* So, we need to increment the counter to force the code to enter the isNewCommandReceived 
               which will remove the previous command saved by the getLastCommand function */
            counter++;
 
            /* Print to the Terminal shield */
            Terminal.println("latitude received");
            /* now we got the latitude data and counter is 1 */
          }
 
          /* Enter this block of code secondly to get the latitude */
          else if(counter == 1)
          {
            /* Print to the Terminal shield */
            Terminal.println("getting longitude ....");
 
            /* Get the longitude value as char array */
            voiceinput_longitude_char = VoiceRecognition.getLastCommand();
 
            /* Convert the longitude char array value into float */
            voiceinput_longitude = atof(voiceinput_longitude_char);
            counter++;
 
             /* Print to the Terminal shield */
            Terminal.println("longitude received");
            /* now we got the longitude data and counter is 2*/
          }
        }
        if(voiceinput_latitude != 0 && voiceinput_longitude != 0 && counter == 2)
        {
          /* Print the latitude on the Terminal shield */
          Terminal.print("latitude: ");
          Terminal.println(voiceinput_latitude);
 
          /* Print the longitude on the Terminal shield */
          Terminal.print("longitude: ");
          Terminal.println(voiceinput_longitude);
 
          /* Increment the counter to prevent the program from repeating */
          counter = 3;
          
          /* Calculate the distance between the current location of the phone 
              and the voice-entered location */
          distance = GPS.getDistance(voiceinput_latitude,voiceinput_longitude);
 
          /* Convert distance into km unit */
          distance =  distance / 1000;
 
          /* Print the distance on the Terminal shield */
          Terminal.print("distance: ");
          Terminal.print(distance);
          Terminal.println(" km");
 
          /* Convert distance from float to char array */
          dtostrf(distance, 6, 3, distance_char);
 
          /* Convert distance from char array to string */
          distance_string = String(distance_char);
 
          /* Build this string and start the text-to-speech on it */
          TextToSpeech.say("you are about " + distance_string + " kilo meter from the ramsis train station");
 
          /* Delay 5 seconds to allow the speech be completed */
          OneSheeld.delay(5000);
        }
}
