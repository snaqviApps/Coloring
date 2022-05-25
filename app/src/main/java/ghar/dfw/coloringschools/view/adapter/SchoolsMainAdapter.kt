package ghar.dfw.coloringschools.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ghar.dfw.coloringschools.backEnd.model.SchoolScores
import ghar.dfw.coloringschools.backEnd.model.SchoolsBasicInfo
import ghar.dfw.coloringschools.databinding.ItemSchoolsBasicInfoBinding
import org.w3c.dom.Text

//class SchoolsMainAdapter(val schools: List<SchoolsBasicInfo>?, val scores: List<SchoolScores>,
class SchoolsMainAdapter(val schools: List<SchoolsBasicInfo>?,
                         private val clickListener: SchoolsBasicInfoListener) : RecyclerView.Adapter<SchoolsMainAdapter.SchoolsMainViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolsMainViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = ItemSchoolsBasicInfoBinding.inflate(layoutInflater)
    return SchoolsMainViewHolder(binding)
  }

  override fun onBindViewHolder(holder: SchoolsMainViewHolder, position: Int) {
    schools?.get(position)?.let { holder.bindData(it, clickListener) }
  }

  override fun getItemCount(): Int {
    return schools!!.size
  }

  class SchoolsMainViewHolder(private val binding : ItemSchoolsBasicInfoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(schoolsBasicInfo: SchoolsBasicInfo, clickListener: SchoolsBasicInfoListener) {
      binding.clickListener = clickListener
      binding.mTextViewSchoolName.text = schoolsBasicInfo.schoolName
    }
  }

  class SchoolsBasicInfoListener(val clickListener : (school : String) ->  Unit) {

    fun onClick(schoolClicked: TextView) = clickListener(schoolClicked.text.toString())        // dummy text
  }

}