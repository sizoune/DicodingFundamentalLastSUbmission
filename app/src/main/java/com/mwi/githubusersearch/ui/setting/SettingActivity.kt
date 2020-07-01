package com.mwi.githubusersearch.ui.setting

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mwi.githubusersearch.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setupUI()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return true
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        alarmReceiver = AlarmReceiver()

        if (alarmReceiver.isAlarmSet(this@SettingActivity))
            alarmSwitch.isChecked = true

        alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!alarmReceiver.isAlarmSet(this@SettingActivity)) {
                    alarmReceiver.setRepeatingAlarm(
                        this@SettingActivity,
                        "10:20",
                        "This is Reminder to open Github User's Search APP !"
                    )
                }
            } else {
                if (alarmReceiver.isAlarmSet(this@SettingActivity))
                    alarmReceiver.cancelAlarm(this@SettingActivity)
            }
        }
    }


}