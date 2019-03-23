package com.example.electicsintlproject.acitvities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Spinner
import com.example.electicsintlproject.R

class AccountActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val enguireBalance: Button = findViewById(R.id.bBalanceEnquiry)
        val miniStatement: Button = findViewById(R.id.bMiniStatement)
        val fundsTransfer: Button = findViewById(R.id.bFundsTransfer)

        val accountsSpinner: Spinner = findViewById(R.id.accountsSpinner)
        val miniStatementSpinner: Spinner = findViewById(R.id.miniStatementSpinner)
        val fundsTransferSpinner: Spinner = findViewById(R.id.fundsTransfereSpinner)

        enguireBalance.setOnClickListener(this)
        miniStatement.setOnClickListener(this)
        fundsTransfer.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.bBalanceEnquiry ->{
                //go to balance enquiry

            }
            R.id.bMiniStatement ->{
                //go to mini-statement

            }
            R.id.bFundsTransfer ->{
                //go to funds transfer
            }
        }

    }
}
