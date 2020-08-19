package com.stift.housecontrol.scheduling;

import com.stift.housecontrol.connection.KnxConnectionService;
import com.stift.housecontrol.task.CloseAllJalousiesTask;
import com.stift.housecontrol.task.CloseKitchen134lJalousiesTask;
import com.stift.housecontrol.task.CloseOfficelJalousiesTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@EnableScheduling
@Component
public class JalousieScheduler {

    @Autowired
    private KnxConnectionService knxConnectionService;
    @Autowired
    private TaskExecutor taskExecutor;

    private LocalTime timeCloseAll = LocalTime.of(21, 00);

    //@Scheduled(fixedDelay = 1000 * 60)
    public void closeAll(){
        if(timeCloseAll != null && compareMinute(timeCloseAll, LocalTime.now())) {
            taskExecutor.execute(new CloseAllJalousiesTask(knxConnectionService));
        }
        else{
            System.out.println("not yet close all");
        }
    }

    public void closeKitchen134(){
        taskExecutor.execute(new CloseKitchen134lJalousiesTask(knxConnectionService));
    }

    public void closeOffice(){
        taskExecutor.execute(new CloseOfficelJalousiesTask(knxConnectionService));
    }


    private boolean compareMinute(LocalTime t1, LocalTime t2) {
        return t1.withSecond(0).withNano(0).equals(t2.withSecond(0).withNano(0));
    }


    public LocalTime getTimeCloseAll() {
        return timeCloseAll;
    }

    public void setTimeCloseAll(LocalTime timeCloseAll) {
        this.timeCloseAll = timeCloseAll;
    }
}
