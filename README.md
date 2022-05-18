<!---
carroch97/carroch97 is a ✨ special ✨ repository because its `README.md` (this file) appears on your GitHub profile.
You can click the Preview link to take a look at your changes.
--->

<h1>Daniele Carrozzino</h1>

<h3>Android developer - Java, Kotlin</h3>

<b>This is the presentation of all my personal project</b>

Over the last years, I worked as a mobile developer for a tech product company that provides messaging and videoconferencing services.
Along with my professional career, I developed some personal projects (of questionable seriousness and usefulness) to enhance my hard skills.

During my career, I mostly worked in an Android stack: indeed, the programming languages I know the most are <b>Java, Kotlin and C++</b>. Moreover, I am very used to markup languages like <b>XML</b> and data formats like JSON.

<h1>Personal project</h1>

<h2>First project - Sprinkler!</h2>

<h4>Have you ever been on vacation for too long and all your plants have dried up when you return?</h4>

WELL! You need an <b>automatic sprinkler</b>

<p>
<img src="https://user-images.githubusercontent.com/51740054/169001661-53c53d8e-6f5e-48bd-8a17-1e9388462ff3.jpg" width="148.8" height="307.2" title="WET">
<img src="https://user-images.githubusercontent.com/51740054/168990977-76850f6d-8770-4523-9633-7091405d5b20.jpg" width="148.8" height="307.2" title="DRY">
  </p>

Uao! but wait, what is an Automatic sprinkler?

<h4>Project description</h4>

I started this project at the end of 2019 at the beginning of my work experience.
It was born from the idea of being able to have fun with a practical and usable implementation in the everyday routine.
Water the plants automatically thanks to a hardware support developed with arduino, when the humidity sensor exceeds the threshold set by the user, the sprinkler automatically waters the plant

<h4>Technical description</h4>

The project is divided into 2 parts, the hardware part and the application part.
I will not explain too much the hardware part also because it is not my field and I don't think it is too interesting for a mobile developer.

<h4>Hardware part</h4>
  Components used: <b>Arduino uno, humidity sensor, bluetooth module, small engine.</b>
  This part includes the Arduino code for the management of all the hardware and sensors
  
  ```
  #include <SoftwareSerial.h> 

//motore d'avviamaneto 
#define STEPPER_PIN_1 11 
#define STEPPER_PIN_2 10 
#define STEPPER_PIN_3 9 
#define STEPPER_PIN_4 8 
 
//sensore d'umidità del terreno 
const int airValue = 431; 
const int waterValue = 244; 
int soilMoistureValue = 0; 
int soilMoisturePercent = 0; 
 
//sensore bluetooth BLE 
SoftwareSerial mySerial(2,3); 
 
//switch dei 4 pin del motore 
int step_number = 0; 
 
//durata del giro del motore 
int num_laps = 0; 
 
//booleano di start o stop del motore 
bool go = false; 
 
//valore di umidità minimo 
int min_umidita = 0; 
 
//delay dopo aver innaffiato una volta 
int num_laps_delay1 = 0; 
int num_laps_delay2 = 0; 
int num_laps_delay3 = 0; 
 
//numero di innaffiature dall'ultimo log 
int num_innaffiature = 0; 
 
//valore massimo di percentuale di idratazione 
int num_max_idra = 100; 
 
//change data to send 
int changeData = 0; 
 
//media di idratazione 
int average = 0; 
 
//numero di letture del sensore 
int num_lect = 0; 
 
//verso del motore 
bool version_engine = false; 
 
//numero di giri che deve fare il motore 
int num_laps_engine = 1000; 
 
//max and min 
int MAX = 0; 
int MIN = 101; 
 
 
void setup() { 
  pinMode(STEPPER_PIN_1, OUTPUT); 
  pinMode(STEPPER_PIN_2, OUTPUT); 
  pinMode(STEPPER_PIN_3, OUTPUT); 
  pinMode(STEPPER_PIN_4, OUTPUT); 
 
  num_innaffiature = 0; 
 
  Serial.begin(9600); 
  mySerial.begin(9600); 
} 
 
//check umidità 
bool checkSensorUmidita(){ 
  version_engine = false; 
  Serial.println(min_umidita); 
  if(soilMoisturePercent < min_umidita){ 
    return true; 
  } 
  return false; 
} 
 
void resetData(){ 
  num_innaffiature = 0; 
  num_lect = 0; 
  average = 0; 
  MAX = 0; 
  MIN = 101; 
} 
 
//elevazione a potenza 
int elev(int x, int y){ 
  int res = 1; 
  for(int i = 0; i < y;i++){ 
    res = res * x; 
  } 
  return res; 
} 
 
int calcAverage(int newValue){ 
  Serial.print("Average : "); 
  Serial.print(average); 
  Serial.print(" newValue : "); 
  Serial.print(newValue); 
  Serial.print(" num_lect : "); 
  Serial.print(num_lect); 
  if(average == 0){ 
    average ++; 
  } 
  return average + ((newValue - average) / num_lect); 
} 
 
void loop() { 
 
  //se mi è arrivato un W1 allora innaffio (starto il motore) oppure se il sensore è minore di 40 
  if(go){ 
    OneStep(version_engine); 
    delay(2); 
    return; 
  } 
 
  //sensore umidità 
  if(num_laps_delay1 > 5000){ 
    num_laps_delay1 = 0; 
    soilMoistureValue = analogRead(A1);//risultato del sensore 
    Serial.println(soilMoistureValue); 
    Serial.print("Valore umidita minima : "); 
    Serial.println(min_umidita); 
    soilMoisturePercent = map(soilMoistureValue, airValue, waterValue, 0, 100); 
    if(soilMoisturePercent > 100){ 
      soilMoisturePercent = 100; 
      Serial.println("100%"); 
    } 
    else if(soilMoisturePercent < 0){ 
      soilMoisturePercent = 0; 
      Serial.println("0%"); 
    } 
    else{ 
      Serial.print(soilMoisturePercent); 
      Serial.println("%"); 
    } 
    if(soilMoisturePercent > MAX){ 
      MAX = soilMoisturePercent; 
    } 
    else if(soilMoisturePercent < MIN){ 
      MIN = soilMoisturePercent; 
    } 
    if(num_lect == 0){ 
      average = soilMoisturePercent; 
    } 
    else{ 
      average = calcAverage(soilMoisturePercent); 
    } 
    num_lect ++; 
    //faccio fare due giri di check per vedere se il valore di prima fosse attendibile 
    if(!go && num_laps_delay2 > 15000){ 
      num_laps_delay2 = 0; 
      go = checkSensorUmidita(); 
    } 
  } 
  num_laps_delay1 ++; 
  num_laps_delay2 ++; 
  num_laps_delay3 ++; 
  //fine sensore umidità 
 
  //scrivo quello che ho sul seriale sulla scheda bluetooth   
  if(num_laps_delay3 > 1000){
      num_laps_delay3 = 0; 
      char buff[5]; 
      sprintf(buff, "M%d-%d-%d-%d-%d", MAX, MIN, num_innaffiature, soilMoisturePercent, average); 
      Serial.println(buff); 
      mySerial.println(buff); 
      Serial.println("SEND DATA"); 
  } 
 
  //se la scheda bluetooth è disponibile e il motore non sta girando allora faccio il check di quello che mi è arrivato 
  int i = 0; 
  if(mySerial.available() && !go){
    Serial.println("Data received"); 
    String buff; 
    char buff2[3]; 
    buff = mySerial.readString(); 
    Serial.println(buff); 
    if(buff[0] == 'M'){ 
      int sizeBuff = buff[1] - '0'; 
      for (int j = 0; j < sizeBuff;j++){ 
        i += (buff[j + 2] - '0') * elev(10, (sizeBuff-j-1)); 
      } 
      Serial.println(sizeBuff); 
      Serial.println(i); 
      Serial.println("Numero minimo di idratazione"); 
      min_umidita = i; 
      go = checkSensorUmidita(); 
    } 
 
    if(buff[0] == 'R'){ 
      //reset data 
      resetData(); 
    } 
    else if(buff[0] == 'B'){ 
      //back motore (il motore gira nel verso opposto) 
      Serial.println("BACK"); 
      version_engine = true; 
      go = true; 
    } 
    else if(buff[0] == 'G' && buff[1] == 'A'){ 
      int sizeBuff = buff[2] - '0'; 
      num_laps_engine = 1000 * sizeBuff; 
      Serial.print("NUM LAPS "); 
      Serial.println(num_laps_engine); 
    } 
    else if(buff[0] == 'W'){ 
      Serial.println("SI"); 
      version_engine = false; 
      go = true; 
    } 
    else if(buff[0] == 'U' && buff[1] == 'P'){ 
      //back motore (il motore gira nel verso opposto) 
      Serial.println("BACK"); 
      version_engine = true; 
      go = true; 
    } 
  } 
  delay(2); 
} 
 
void stopEngine(){ 
  go = false; 
  num_laps = 0; 
  digitalWrite(STEPPER_PIN_1, LOW); 
  digitalWrite(STEPPER_PIN_2, LOW); 
  digitalWrite(STEPPER_PIN_3, LOW); 
  digitalWrite(STEPPER_PIN_4, LOW); 
} 
 
void OneStep(bool dir){ 
  if(!dir){ 
    switch(step_number){ 
      case 0: 
        digitalWrite(STEPPER_PIN_1, HIGH); 
        digitalWrite(STEPPER_PIN_2, LOW); 
        digitalWrite(STEPPER_PIN_3, LOW); 
        digitalWrite(STEPPER_PIN_4, LOW); 
      break; 
      case 1: 
        digitalWrite(STEPPER_PIN_1, LOW); 
        digitalWrite(STEPPER_PIN_2, HIGH); 
        digitalWrite(STEPPER_PIN_3, LOW); 
        digitalWrite(STEPPER_PIN_4, LOW); 
      break; 
      case 2: 
        digitalWrite(STEPPER_PIN_1, LOW); 
        digitalWrite(STEPPER_PIN_2, LOW); 
        digitalWrite(STEPPER_PIN_3, HIGH); 
        digitalWrite(STEPPER_PIN_4, LOW); 
      break; 
      case 3: 
        digitalWrite(STEPPER_PIN_1, LOW); 
        digitalWrite(STEPPER_PIN_2, LOW); 
        digitalWrite(STEPPER_PIN_3, LOW); 
        digitalWrite(STEPPER_PIN_4, HIGH); 
      break; 
    } 
  } 
  else{ 
    switch(step_number){ 
      case 3: 
        digitalWrite(STEPPER_PIN_1, HIGH); 
        digitalWrite(STEPPER_PIN_2, LOW); 
        digitalWrite(STEPPER_PIN_3, LOW); 
        digitalWrite(STEPPER_PIN_4, LOW); 
      break; 
      case 2: 
        digitalWrite(STEPPER_PIN_1, LOW); 
        digitalWrite(STEPPER_PIN_2, HIGH); 
        digitalWrite(STEPPER_PIN_3, LOW); 
        digitalWrite(STEPPER_PIN_4, LOW); 
      break; 
      case 1: 
        digitalWrite(STEPPER_PIN_1, LOW); 
        digitalWrite(STEPPER_PIN_2, LOW); 
        digitalWrite(STEPPER_PIN_3, HIGH); 
        digitalWrite(STEPPER_PIN_4, LOW); 
      break; 
      case 0: 
        digitalWrite(STEPPER_PIN_1, LOW); 
        digitalWrite(STEPPER_PIN_2, LOW); 
        digitalWrite(STEPPER_PIN_3, LOW); 
        digitalWrite(STEPPER_PIN_4, HIGH); 
      break; 
    } 
  } 
  step_number ++; 
  num_laps++; 
  if(num_laps > num_laps_engine){ 
    Serial.println("STOP"); 
    if(!dir){ 
      num_innaffiature ++; 
    } 
    stopEngine(); 
  } 
  if(step_number > 3){ 
    step_number = 0; 
  } 
}
 
  ```
  
  <h4>Aplication part</h4>
  
  ```
  TODO
  Insert Best code
  
  ```
  
  and link to the code... [My personal code DanieleProject Msauce Project](https://github.com/msauceproject)

<h2>Second project - Scraper</h2>

Born as a web scraper to get information on the covid in a single application, it has evolved as a notification manager.

<p>
<img src="https://user-images.githubusercontent.com/51740054/169076121-59b75152-1df7-4441-9dce-2f41be826adc.jpg" width="158.8" height="307.2" title="WET">
<img src="https://user-images.githubusercontent.com/51740054/169076176-e76339d3-2ccc-453c-8240-03fd1d0259e8.jpg" width="218.8" height="307.2" title="DRY">
  </p>



