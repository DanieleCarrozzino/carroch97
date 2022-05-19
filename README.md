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
  
  void setup() { 
    pinMode(STEPPER_PIN_1, OUTPUT); 
    pinMode(STEPPER_PIN_2, OUTPUT); 
    pinMode(STEPPER_PIN_3, OUTPUT); 
    pinMode(STEPPER_PIN_4, OUTPUT); 

    num_innaffiature = 0; 

    Serial.begin(9600); 
    mySerial.begin(9600); 
  }
  
  ...
  ```
  
  [Full arduino code](https://github.com/DanieleCarrozzino/carroch97/blob/main/arduinoCode.txt)
  
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



