package com.sjonsen.application;

import java.util.Timer;
import java.util.TimerTask;

public class TimedEvent{
    
    private long interval = 30*60000; //30 min
    
    public TimedEvent(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                Pulse.seeds.clear();
            }
            
        }, interval);
    }
    
}