package com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.SelfCare.Fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.diabestes_care_app.R

// Hallow this is Update
class StepCounterK : Fragment(), SensorEventListener {
    // Added SensorEventListener the MainActivity class
    // Implement all the members in the class MainActivity
    // after adding SensorEventListener

    // we have assigned sensorManger to nullable
    private var sensorManager: SensorManager? = null

    // Creating a variable which will give the running status
    // and initially given the boolean value as false
    private var running = false

    // Creating a variable which will counts total steps
    // and it has been given the value of 0 float
    private var totalSteps = 0f

    // Creating a variable  which counts previous total
    // steps and it has also been given the value of 0 float
    private var previousTotalSteps = 0f

    private lateinit var tvStepsTaken: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvStepTaken2: TextView
    private lateinit var tvStepCount: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_step__counter__k, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initialize
        activity?.let {
            tvStepsTaken = it.findViewById(R.id.tv_stepsTaken2)
            tvDistance = it.findViewById(R.id.distance_res)
            tvStepTaken2 = it.findViewById(R.id.cal_result)
            tvStepCount = it.findViewById(R.id.step_res)

            loadData()
            restSteps()
            // Adding a context of SENSOR_SERVICE aas Sensor Manager
            sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        }
    }

    override fun onResume() {
        super.onResume()
        running = true

        // Returns the number of steps taken by the user since the last reboot while activated
        // This sensor requires permission android.permission.ACTIVITY_RECOGNITION.
        // So don't forget to add the following permission in AndroidManifest.xml present in manifest folder of the app.
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            // This will give a toast message to the user if there is no sensor in the device
            Toast.makeText(context, "جهازك لا يدعم مستشعر الحركة", Toast.LENGTH_SHORT).show()
        } else {
            // Rate suitable for the user interface
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        sensorManager = this.requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Calling the TextView that we made in activity_main.xml
        // by the id given to that TextView

        if (running) {
            totalSteps = event!!.values[0]

            // Current steps are calculated by taking the difference of total steps
            // and previous steps
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            val calories = getCalories(currentSteps)
            val distance = getDistanceCovered(currentSteps)

            // It will show the current steps to the user
            tvStepsTaken.text = ("$currentSteps")
            tvStepTaken2.text = (calories)
            tvDistance.text = (distance)
            tvStepCount.text = ("$currentSteps")
        }
    }

    private fun saveData() {

        // Shared Preferences will allow us to save
        // and retrieve data in the form of key,value pair.
        // In this function we will save data
        val sharedPreferences =
            requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps)
        editor.apply()
    }

    private fun restSteps() {
        tvStepsTaken.setOnClickListener {
            // This will give a toast message if the user want to reset the steps
            Toast.makeText(context, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        tvStepsTaken.setOnLongClickListener {

            previousTotalSteps = totalSteps

            // When the user will click long tap on the screen,
            // the steps will be reset to 0
            tvStepsTaken.text = 0.toString()
            tvDistance.text = 0.toString()

            // This will save the data
            saveData()

            true
        }
    }

    private fun loadData() {

        // In this function we will retrieve data
        val sharedPreferences =
            requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)

        // Log.d is used for debugging purposes
        Log.d("MainActivity", "$savedNumber")

        previousTotalSteps = savedNumber

        saveData()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // We do not have to write anything in this function for this app
    }

    private fun getCalories(steps: Int): String {
        val Cal = (steps * 0.045).toInt()
        return "$Cal calories"
    }

    private fun getDistanceCovered(steps: Int): String {
        val feet = (steps * 2.5).toInt()
        val distance = feet / 3.281
        val finalDistance: Double = String.format("%.2f", distance).toDouble()
        return "$finalDistance meter"
    }
}