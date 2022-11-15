package com.example.belajarandroiddatabinding.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TodoWorker(val context: Context, val params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        NotificationHelper(context)
            .createNotification(
                inputData.getString("TITLE").toString(),
                inputData.getString("CONTENT").toString()
            )
        return Result.success()
    }
}