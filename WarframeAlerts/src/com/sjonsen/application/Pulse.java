package com.sjonsen.application;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Pulse {
    
    static int pulseCount = 0;
    
    static URL url;
    static BufferedReader br;
    static String[] entries, missions, temp;
    static List<String> missionsList = new LinkedList<String>();
    static ArrayList<String> seeds = new ArrayList<String>();
    
    public static void start(){
        while(true){
            pulse();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pulseCount++;
        }
    }

    public static void pulse(){
        try {
            url = new URL(Vars.API_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        try{
        br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (Exception e){
        }
        
        try{
            String inputLine;
            if (br != null){
                while((inputLine = br.readLine()) != null){
                    missionsList.clear();
                    Alert.getAlerts().clear();
                    
                    entries = inputLine.split("Alerts");
                    entries = entries[1].split("Sorties");
                    missions = entries[0].split("MissionInfo");
                    missionsList = new LinkedList<String>(Arrays.asList(missions));
                    missionsList.remove(0);
                    

                    
                    System.out.println("Pulse " + pulseCount);
                    for(String mission : missionsList){
                        Alert a = new Alert(mission);
                    }
                    
                    Alert.printAlerts();
                    
                    for(int i = 0; i < Alert.getAlerts().size(); i++){
                        for(int j = 0; j < Vars.WANTED.length; j++){
                            if (Alert.getAlerts().get(i).getItems().toLowerCase().contains(Vars.WANTED[j].toLowerCase())){
                                System.out.println("Mission " + i+1 + " contained " + Vars.WANTED[j]);
                                notifyTray(Vars.WANTED[j], Alert.getAlerts().get(i).getSeed());
                            }
                        }
                    }
                    System.out.println("-----------------------------------------------------");
                }
            }
        } catch(IOException e){
            
        }
    }
    
    public static void notifyTray(String name, String seed){
        
        if (!duplicateSeed(seed)){
            System.out.println("No dupe, notifying");
            seeds.add(seed);
        }else {
            System.out.println("ID is dupe, not notifying again");
            return;            
        }
        
        if (SystemTray.isSupported()){
            SystemTray tray = SystemTray.getSystemTray();
            
            Image image = Toolkit.getDefaultToolkit().createImage("/res/league.png");
            TrayIcon trayIcon = new TrayIcon(image, "Item found!");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Alert with item found!");
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            trayIcon.displayMessage("Warframe", name + " alert found!", MessageType.NONE);
        }
    }
    
    public static boolean duplicateSeed(String seed){
        return seeds.contains(seed);
    }
    
}
