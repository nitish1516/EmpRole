package com.example.emprole.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emprole.R
import com.example.emprole.data.api.ApiHelper
import com.example.emprole.data.api.ApiServiceImpl
import com.example.emprole.data.model.Data
import com.example.emprole.data.model.EmpDetailsPojo
import com.example.emprole.ui.base.ViewModelFactory
import com.example.emprole.ui.main.adapter.EmpAdapter
import com.example.emprole.ui.main.viewmodel.MainViewModel
import com.example.emprole.utils.Status
import com.google.gson.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: EmpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }
    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EmpAdapter(arrayListOf(),this)
       /* recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )*/
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getEmps().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { empData -> if(empData.code==200)
                    {
                        renderList(empData.data)
                        recyclerView.visibility = View.VISIBLE}
                    else{
                        recyclerView.visibility = View.GONE}

                     }

                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    println("Error = "+it.message)
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(dataEmp: List<Data>) {
        var listEmp  =ArrayList<EmpDetailsPojo>()
        for(empDataList in dataEmp)
        {
            val empDetailsPojo = EmpDetailsPojo(
                getJsonFromStr(empDataList.duties),
                empDataList.emailAddress,
                empDataList.employeeAge,
                empDataList.employeeCode,
                empDataList.firstName,
                empDataList.isTeamLead,
                empDataList.jobTitleName,
                empDataList.lastName,
                empDataList.phoneNumber,

                empDataList.preferredFullName?:continateFirstLastName(empDataList) ,
                empDataList.profileImage,
                empDataList.region,
                empDataList.userId
            )
            listEmp.add(empDetailsPojo)
        }
        adapter.addData(listEmp)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }


    fun getJsonFromStr(duties:Any): String
    {
        var strBuilder= StringBuilder()
        val jsonParser : JsonParser = JsonParser()
        val jsonTree=jsonParser.parse(Gson().toJson(duties))
        if(jsonTree is JsonPrimitive)
        {
            strBuilder.append(jsonTree.asString)
            println("Type = 1 = "+jsonTree.asString)
        }
        else if(jsonTree is JsonArray)
        {
            val jsonArray=jsonTree.asJsonArray
            for (it in jsonArray)
            {
                if(it is JsonPrimitive)
                {
                    strBuilder.append(it.asString).append("\n")
                    println("Type = 3 = "+it.asString)
                }
                else if(it is JsonObject)
                {
                    val jObj = JSONObject(it.toString())
                    val keys: Iterator<String> = jObj.keys()

                    while (keys.hasNext()) {
                        // loop to get the dynamic key
                        val currentDynamicKey = keys.next() as String
                        // get the value of the dynamic key
                        val currentDynamicValue: String =
                            jObj.getString(currentDynamicKey)
                        // do something here with the value...
                        try {
                            if(jsonParser.parse(currentDynamicValue) is JsonArray)
                            {
                                val jsonArrayChile=jsonParser.parse(currentDynamicValue).asJsonArray
                                var index: Int=0;
                                for (it1 in jsonArrayChile)
                                {
                                    if(it1 is JsonPrimitive)
                                    {
                                        if(index==0)
                                        {
                                            strBuilder.append(currentDynamicKey.replace(",","").trim()+" = ").append(it1.asString)
                                        }
                                        else
                                        {
                                            strBuilder.append(it1.asString).append("\n")
                                        }

                                        index++
                                    }
                                }
                            }
                            else
                            {
                                strBuilder.append(currentDynamicKey.trim()+" = "+currentDynamicValue).append("\n")
                            }
                        }
                        catch (exception: Exception)
                        {
                            strBuilder.append(currentDynamicKey.trim()+" = "+currentDynamicValue).append("\n")
                        }
                    }

                }

            }

        }

        return (if (isNullOrEmpty(strBuilder.toString())) "N/A" else strBuilder.toString())
    }

    fun continateFirstLastName(empData:Data) : String
    {

            return empData.firstName+" "+empData.lastName
    }
    fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.isEmpty())
            return false
        return true
    }
}
