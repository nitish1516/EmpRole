package com.example.emprole.ui.main.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.emprole.R
import com.example.emprole.R.drawable
import com.example.emprole.data.model.Data
import com.example.emprole.data.model.EmpDetailsPojo
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_emp_details.*
import kotlinx.android.synthetic.main.activity_emp_details.profile_image

import kotlinx.android.synthetic.main.activity_emp_details.textViewEmail
import kotlinx.android.synthetic.main.activity_emp_details.textViewEmpCode
import kotlinx.android.synthetic.main.activity_emp_details.textViewEmpName
import kotlinx.android.synthetic.main.activity_emp_details.textViewJobTitle
import kotlinx.android.synthetic.main.activity_emp_details.textViewMobNum
import kotlinx.android.synthetic.main.emplist_layout.view.*
import kotlinx.android.synthetic.main.reporting_manager_detail.*
import org.json.JSONObject

class EmpDetails : AppCompatActivity() {

        var hmapTeamLead=LinkedHashMap<String,EmpDetailsPojo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_details)
        intent?.let {
            val empData= intent.extras?.getParcelable("EmpData") as EmpDetailsPojo?
            if (empData != null) {
                bindUI(empData)
            }
            val empDataList= intent.extras?.getParcelableArrayList<EmpDetailsPojo>("EmpDataAll") as ArrayList<EmpDetailsPojo>?
            if (empDataList != null) {
                getAllTemaLead(empDataList)
                btn_reportingManager.setOnClickListener(View.OnClickListener {
                    if (empData != null) {

                        if(empData.isTeamLead) {
                            hmapTeamLead.get("General Manager")?.let { it1 ->
                                showReportingManager(it1
                                )
                            }
                        }
                        else
                        {
                            showReportingManager(fetchTeamLeadOfDept(empData))
                        }
                    }
                })


            }
        }

    }


    @SuppressLint("ResourceAsColor")
    fun bindUI(empDetail: EmpDetailsPojo)
    {
        Glide.with(this)
            .load(empDetail.profileImage)
            .placeholder(drawable.default_user)
            .apply(RequestOptions.circleCropTransform())
            .into(profile_image)

        textViewEmpName.text=empDetail.preferredFullName?:"Name Missing"
        textViewJobTitle.text=empDetail.jobTitleName
        textViewIsTL.text=if(empDetail.isTeamLead) "Yes" else "No"
        textViewEmpCode.text=empDetail.employeeCode
        textViewEmail.text=empDetail.emailAddress
        textViewMobNum.text=empDetail.phoneNumber
        textViewUserId.text=empDetail.userId
        textViewDuties.text=empDetail.duties
        if(empDetail.jobTitleName.equals("General Manager",true))
        {
            btn_reportingManager.text="I am the Boss"
            btn_reportingManager.setBackgroundResource(drawable.buttonstyle_disable)

            btn_reportingManager.isEnabled=false
        }
        else
        {
            btn_reportingManager.text="Reporting Manager"
            btn_reportingManager.setBackgroundResource(drawable.buttonstyle)

            btn_reportingManager.isEnabled=true
        }

    }

    fun showReportingManager(reportingManagerDetail:EmpDetailsPojo)
    {

        val  mDialog =  Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.reporting_manager_detail);
        mDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);

       val parms: WindowManager.LayoutParams  = mDialog.window!!.attributes
        parms.gravity = Gravity.CENTER;
      //  parms.height=parms.WRAP_CONTENT;
        parms.dimAmount =  0.8F;
        mDialog.textViewEmpName.text=reportingManagerDetail.preferredFullName
        mDialog.textViewJobTitle.text=reportingManagerDetail.jobTitleName
        mDialog.textViewAge.text=reportingManagerDetail.employeeAge
        mDialog.textViewEmpCode.text=reportingManagerDetail.employeeCode
        mDialog.textViewEmail.text=reportingManagerDetail.emailAddress
        mDialog.textViewMobNum.text=reportingManagerDetail.phoneNumber
        mDialog.btn_ok.setOnClickListener(View.OnClickListener {
            mDialog.dismiss()
        })
        Glide.with(this)
            .load(reportingManagerDetail.profileImage)
            .placeholder(drawable.default_user)
            .apply(RequestOptions.circleCropTransform())
            .into(mDialog.profile_image)
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }
    fun getAllTemaLead(listEmp: ArrayList<EmpDetailsPojo>)
    {
        for(empDetails in listEmp)
        {
            if(empDetails.isTeamLead)
            {
                empDetails.jobTitleName?.let { hmapTeamLead.put(it,empDetails) }
            }
        }

    }

    fun fetchTeamLeadOfDept(empDetail: EmpDetailsPojo) : EmpDetailsPojo
    {

        if(empDetail.jobTitleName.equals("Developer",true))
        {
            return  hmapTeamLead.get("Developer")!!
        }
        else if(empDetail.jobTitleName.equals("Finance",true))
        {
            return hmapTeamLead.get("Finance")!!
        }
       return  empDetail
    }



}
