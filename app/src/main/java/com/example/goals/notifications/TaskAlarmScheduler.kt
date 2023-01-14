package com.example.goals.notifications

import com.example.goals.domain.models.Task

interface TaskAlarmScheduler {

    fun schedule(task: Task)

    fun cancel(task: Task)

}