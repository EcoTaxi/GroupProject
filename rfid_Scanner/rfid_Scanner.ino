#include <SPI.h>
#include <RFID.h>

//D10:pin of card reader SDA.  D9:pin of card reader RST
RFID rfid(10, 9);
unsigned char status;
unsigned char str[MAX_LEN];  //MAX_LEN is 16: size of the array

void setup()
{
  Serial.begin(9600);
  SPI.begin();
  rfid.init(); //initialization
  Serial.println("Move over Tag...");
}

void loop()
{
  //Search card, return card types
  if (rfid.findCard(PICC_REQIDL, str) == MI_OK) {
    Serial.println("Find the card!");
    // Show card type
    ShowCardType(str);
    //Anti-collision detection, read card serial number
    if (rfid.anticoll(str) == MI_OK) {
      
      Serial.print("Location Passed Card has been Read");
      //Display card serial number
      for (int i = 0; i < 4; i++) {
        Serial.print(0x0F & (str[i] >> 4), HEX);
        Serial.print(0x0F & str[i], HEX);
      }
      Serial.println("");
    }
    //card selection (lock card to prevent redundant read, removing the line will make the sketch read cards continually)
    rfid.selectTag(str);
  }
  rfid.halt();  // command the card to enter sleeping state
}
void ShowCardType(unsigned char * type)
{
  Serial.print("Card type: ");
  if (type[0] == 0x04 && type[1] == 0x00)
    Serial.println("MFOne-S50");
    Serial.println("Co-Ords")
  else if (type[0] == 0x02 && type[1] == 0x00)
    Serial.println("MFOne-S70");
    Serial.println("CoOrds")
  else if (type[0] == 0x44 && type[1] == 0x00)
    Serial.println("MF-UltraLight");
    Serial.println("CoOrds")
  else if (type[0] == 0x08 && type[1] == 0x00)
    Serial.println("MF-Pro");
    Serial.println("CoOrds")
  else if (type[0] == 0x44 && type[1] == 0x03)
    Serial.println("MF Desire");
    Serial.println("CoOrds")
  else
    Serial.println("Unknown");
}
