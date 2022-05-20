package com.android.myapplication

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothHeadset
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.BluetoothProfile.ServiceListener
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

class AudioDevice(var context: Context) : ServiceListener, SensorEventListener {

    val TAG = "AudioDevice"
    var audioManager    : AudioManager
    var bluetoothAdapter: BluetoothAdapter
    var bluetoothManager: BluetoothManager
    var sensorManager   : SensorManager
    var proximitySensor : Sensor
    var intentFilter    : IntentFilter = IntentFilter()
    lateinit var audioBroadcastReceiver: BroadcastReceiver

    var bluetoothHeadsetConnected   : Boolean = false
    var bluetoothA2DPConnected      : Boolean = false;

    init {
        audioManager        = context.getSystemService(Context.AUDIO_SERVICE)       as AudioManager
        bluetoothManager    = context.getSystemService(Context.BLUETOOTH_SERVICE)   as BluetoothManager
        sensorManager       = context.getSystemService(Context.SENSOR_SERVICE)      as SensorManager
        proximitySensor     = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        bluetoothAdapter    = bluetoothManager.adapter

        bluetoothAdapter.getProfileProxy(context, this, BluetoothProfile.HEADSET)
        bluetoothAdapter.getProfileProxy(context, this, BluetoothProfile.A2DP)
        intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED)
        intentFilter.addAction(BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED)
        intentFilter.addAction(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED)
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG)

        initBroadcastReceiver()
        context.registerReceiver(audioBroadcastReceiver, intentFilter)
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        startOutputAudio()
    }

    /**
     * TITLE -
     * Start output audio.
     *
     * API NOTE -
     * Redirect the first exit.
     * @author Daniele Carrozzino
     * */
    fun startOutputAudio(){
        if (bluetoothAdapter.isEnabled()) {
            val headsetConnected : Boolean =
                bluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET)    == BluetoothProfile.STATE_CONNECTED
            val a2dpConnected    : Boolean =
                bluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP)       == BluetoothProfile.STATE_CONNECTED
            if(headsetConnected || a2dpConnected){
                Log.w(TAG, "Bluetooth connected with headset or a2dp")
            }
        }
    }

    fun close(){
        context.unregisterReceiver(audioBroadcastReceiver)
        sensorManager.unregisterListener(this)
    }

    fun initBroadcastReceiver(){
        audioBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Toast.makeText(context, "receivedBroadcastEvent", Toast.LENGTH_SHORT).show()
                if (intent.action != null) {
                    when (intent.action) {
                        BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED    -> {
                            Log.w(TAG, "ACTION_CONNECTION_STATE_CHANGED")
                            receivedBluetoothSCOHeadsetEvent(intent.getIntExtra(BluetoothProfile.EXTRA_STATE, BluetoothProfile.STATE_DISCONNECTED) == BluetoothProfile.STATE_CONNECTED)
                        }
                        BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED         -> {
                            Log.w(TAG, "ACTION_AUDIO_STATE_CHANGED")
                            receivedBluetoothCSOAudioEvent()
                        }
                        AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED         -> {
                            Log.w(TAG, "ACTION_SCO_AUDIO_STATE_UPDATED")
                            receivedBluetoothSCOAUdioUpdateEvent(intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, AudioManager.SCO_AUDIO_STATE_DISCONNECTED))
                        }
                        TelephonyManager.ACTION_PHONE_STATE_CHANGED         -> {
                            Log.w(TAG, "ACTION_PHONE_STATE_CHANGED")
                            receivedActionPhoneStateChangedEvent(intent.getStringExtra(TelephonyManager.EXTRA_STATE))
                        }
                        Intent.ACTION_HEADSET_PLUG                          -> {
                            Log.w(TAG, "ACTION_HEADSET_PLUG")
                            receivedHeadsetPluggedEvent(intent.getIntExtra("state", 0) == 1)
                        }
                    }
                }
            }
        }
    }

    fun receivedHeadsetPluggedEvent(headsetPlugged: Boolean){
        //TODO if headsetPlugged i have to exit my music from the headset
    }

    fun receivedBluetoothSCOHeadsetEvent(bluetoothHeadsetPlugged: Boolean){
        if(bluetoothHeadsetPlugged){
            Log.w(TAG, "BluetoothHeadsetPlugged | isConnected = " + bluetoothHeadsetConnected)
            //Set the output of the audio from the bluetooth
            //audioManager.
        }
    }

    fun receivedBluetoothCSOAudioEvent(){
        //TODO da capire quando viene chiamato
    }

    fun receivedBluetoothSCOAUdioUpdateEvent(state:Int){
        when(state){
            AudioManager.SCO_AUDIO_STATE_CONNECTED -> {
                Log.w(TAG, "receivedBluetoothSCOAUdioUpdateEvent connected")
            }
            AudioManager.SCO_AUDIO_STATE_DISCONNECTED -> {
                Log.w(TAG, "receivedBluetoothSCOAUdioUpdateEvent NOT connected")
            }
            AudioManager.SCO_AUDIO_STATE_CONNECTING -> {
                Log.w(TAG, "receivedBluetoothSCOAUdioUpdateEvent Connecting")
            }
        }
    }

    fun receivedActionPhoneStateChangedEvent(state: String?){
        if(state != null){

        }
    }



    override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
        when (profile) {
            /*
            * HSP (Handset Profile): fornisce le funzionalità
            * di base necessarie per la comunicazione tra il telefono e l'auricolare.
            * */
            BluetoothProfile.HEADSET -> {
                Log.w(TAG, "HEADSET")
                bluetoothHeadsetConnected = true;
                if(proxy?.connectedDevices != null)
                    for(device in proxy.connectedDevices){
                        Log.w(TAG, "HEADSET | isConnected : " + (proxy.getConnectionState(device) == BluetoothProfile.STATE_CONNECTED))
                    }
            }
            /*
             * A2DP (Advanced Audio Distribution Profile): consente una trasmissione di segnali audio stereo
             * con qualità superiore rispetto alla codifica mono utilizzata per i profili HSP e HFP.
             * */
            BluetoothProfile.A2DP -> {
                Log.w(TAG, "A2DP")
                bluetoothA2DPConnected = true;
                if(proxy?.connectedDevices != null)
                    for(device in proxy.connectedDevices){
                        Log.w(TAG, "A2DP | isConnected : " + (proxy.getConnectionState(device) == BluetoothProfile.STATE_CONNECTED))
                    }
            }
            /*
             * This profile allows devices such as car phones with built-in
             * GSM transceivers to connect to a SIM card in a Bluetooth enabled phone
             * */
            BluetoothProfile.SAP -> {
                Log.w(TAG, "SAP")
            }
            /*
            * Provides support for devices such as mice,joysticks,
            * keyboards, and simple buttons and indicators on other types of devices.
            * it is designed to provide a low latency link, with low power requirements.
            * PlayStation 3 controllers and Wii remotes also use Bluetooth HID.
            * */
            BluetoothProfile.HID_DEVICE -> {
                Log.w(TAG, "HID_DEVICE")
            }
        }
    }

    override fun onServiceDisconnected(profile: Int) {
        Log.w(TAG, "onServiceDisconnected profile")
        when (profile) {
            BluetoothProfile.HEADSET -> {
                Log.w(TAG, "HEADSET disconnected")
                bluetoothHeadsetConnected = false;
            }
            BluetoothProfile.A2DP -> {
                Log.w(TAG, "A2DP disconnected")
                bluetoothA2DPConnected = false;
            }
            BluetoothProfile.SAP -> {
                Log.w(TAG, "SAP")
                //Not supported
            }
            BluetoothProfile.HID_DEVICE -> {
                Log.w(TAG, "HID_DEVICE")
                //Not supported
            }
        }
    }

    /**
     * <p>On sensor changed</p>
     * if I bring the device close to my ear, I have to change the listening mode
     *
     * @param sensorEvent sensor event - it says to me the type of sensor and the distance
     *
     * */
    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        if(sensorEvent != null && sensorEvent.sensor.type == Sensor.TYPE_PROXIMITY){
            Log.w(TAG, "Value : " + sensorEvent.values[0])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {

    }

    interface AudioMediaPlayerManager{
        fun startAudio()
        fun stopAudio()
        fun bluetoothHeadsetEnabled()
        fun bluetoothA2DPEnabled()
    }
}