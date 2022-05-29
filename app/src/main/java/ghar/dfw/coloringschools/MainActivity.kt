package ghar.dfw.coloringschools

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

  private lateinit var tvTarget: TextView
  private lateinit var colorController: SeekBar
  private lateinit var colorBlueController: SeekBar
  private lateinit var colorGreenController: SeekBar

  /** Red, Blue, Green STATE variables */
  private var colorRed = 0
  private var colorBlue = 0
  private var colorGreen = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    tvTarget = findViewById(R.id.tvTarget)
    
    colorController = findViewById(R.id.seekBar)
    colorGreenController = findViewById(R.id.seekBarGreen)
    colorBlueController = findViewById(R.id.seekBarBlue)
    changColor()

    val navController = findNavController(R.id.schoolHostFragment)
    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navBack)
    bottomNavigationView.setupWithNavController(navController)

    navController.navigateUp()
  }

  private fun changColor() {

    colorController.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        colorRed = p1
        tvTarget.setTextColor(Color.rgb(colorRed, colorGreen, colorBlue))
      }
      override fun onStartTrackingTouch(p0: SeekBar?) {}
      override fun onStopTrackingTouch(p0: SeekBar?) {}
    })

    colorBlueController.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        colorBlue = p1
        tvTarget.setTextColor(Color.rgb(colorRed, colorGreen, colorBlue))
      }
      override fun onStartTrackingTouch(p0: SeekBar?) {}
      override fun onStopTrackingTouch(p0: SeekBar?) {}
    })

    colorGreenController.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        colorGreen = p1
        tvTarget.setTextColor(Color.rgb(colorRed, colorGreen, colorBlue))
      }
      override fun onStartTrackingTouch(p0: SeekBar?) {}
      override fun onStopTrackingTouch(p0: SeekBar?) {}
    })
  }

}