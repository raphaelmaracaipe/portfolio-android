package br.com.raphaelmaracaipe.uimessage.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.raphaelmaracaipe.uimessage.flow.StatusNotificationFlow

class StatusWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        StatusNotificationFlow.notifyStatus()

        return Result.retry()
    }
}