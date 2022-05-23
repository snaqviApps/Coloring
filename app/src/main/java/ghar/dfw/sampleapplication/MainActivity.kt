package ghar.dfw.sampleapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ghar.dfw.sampleapplication.backEnd.repo.UIState
import ghar.dfw.sampleapplication.view.viewmodels.SchoolViewModel

class MainActivity : AppCompatActivity() {

  private lateinit var viewModel: SchoolViewModel
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

    // display schools feteched data
    viewModel = ViewModelProvider(this)[SchoolViewModel::class.java]
    getSchoolData(viewModel)
  }

  private fun getSchoolData(viewModel: SchoolViewModel) {
    viewModel.schoolsData.observe(this, Observer {state ->
     when(state) {
       is UIState.EmptyState -> {}

       is UIState.SuccessState -> {
         val schools = state.schools?.toList()?.get(0)?.censusTract
         val scores = state.scores?.toList()?.get(0)?.schoolName
         Toast.makeText(baseContext, "Schools: $schools\n, score-for-school: $scores", Toast.LENGTH_LONG)
           .show()
       }
       is UIState.ErrorState -> {
         Toast.makeText(baseContext, "Error: ${state.error}", Toast.LENGTH_LONG)
           .show()
       }
     }
    })
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