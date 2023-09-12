package com.yojana.bharat

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast

import com.yojana.bharat.databinding.ActivityDetailSchemeBinding

class Detail_scheme : AppCompatActivity() {
    private var mSchemeList: ArrayList<Heading>? =null
    private var binding: ActivityDetailSchemeBinding?=null
    private var mCurrentPosition:Int =0

    private var mSchemeId :Int ?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        mSchemeId = intent.getIntExtra("schemeId", 0)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSchemeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        mSchemeList = Constants.getScheme()

        Toast.makeText(this@Detail_scheme,"$mSchemeId",Toast.LENGTH_LONG).show()

        setScheme()

        binding?.btnMenu?.setOnClickListener {
            popup()
        }
    }


    private fun setScheme() {
        val heading: Heading =mSchemeList!![mSchemeId!!-1]

        binding?.tvCategory?.text = heading.category
        binding?.tvSchemeName?.text = heading.title
        binding?.ivSchemeImage?.setImageResource(heading.image)
        binding?.tvObjectiveDes?.text = heading.objective
        binding?.tvEligibilityAge?.text = heading.age
        binding?.tvEligibilityGender?.text = heading.gender
        binding?.tvEligibilityIncome?.text = heading.income
        binding?.tvBenefitsDes?.text = heading.benefits
        binding?.tvApplicationProcedureDes?.text = heading.application_procedure
        binding?.tvDocumentRequiredDes?.text = heading.documents_required


    }

    private fun popup() {
        val popupMenu = PopupMenu(this, binding?.btnMenu)
        popupMenu.inflate(R.menu.menu_scheme_details)


        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.help -> {
                    val intent = Intent(this@Detail_scheme,Help::class.java)
                    startActivity(intent)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

        }

        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popupMenu)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (e: Exception){
            Log.e("Main", "Error showing menu icons.", e)
        } finally {
            popupMenu.show()
        }

    }
}