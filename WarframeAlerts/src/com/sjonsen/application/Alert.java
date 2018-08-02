package com.sjonsen.application;
import java.util.ArrayList;


public class Alert {
    
    private static ArrayList<Alert> alerts = new ArrayList<Alert>();
    
    private String missionType;
    private String faction;
    private String minEnemyLevel;
    private String maxEnemyLevel;
    private String difficulty;
    private String seed;
    private String credits;
    private String items = "";
    private String itemCount;
    
    public Alert(String mission){
        missionType = filter("missionType\":\"", mission);
        faction = filter("faction\":\"", mission);
        minEnemyLevel = filter("minEnemyLevel\":", mission);
        maxEnemyLevel = filter("maxEnemyLevel\":", mission);
        difficulty = filter("difficulty\":", mission);
        seed = filter("seed\":", mission);
        credits = filter("credits\":", mission);
        credits = Util.replaceWithWhitespace(credits, "}");
        if(mission.toLowerCase().contains("\"items\"")){
            items = filter("items\":", mission);
            items = Util.replaceWithWhitespace(items, "[", "]", "}");
        }else if (mission.toLowerCase().contains("\"counteditems\"")){
            items = filter("ItemType\":", mission);
            itemCount = filter("ItemCount\":", mission);
            itemCount = Util.replaceWithWhitespace(itemCount, "}", "]");
        }

        alerts.add(this);
    }
 
    private String filter(String filter, String mission){
        String[] temp;
        temp = mission.split(filter);
        temp = temp[1].split(",");
        return temp[0].replace("\"", "");
    }
    
    public static ArrayList<Alert> getAlerts(){
        return alerts;
    }
    
    public static void printAlerts(){
        int counter = 1;
        for(Alert s : alerts){
            System.out.println("ALERT " + counter + ": " + s.getMissionType()
                     + ", " +  s.getFaction() + ", " + s.getMinEnemyLevel() + " - " + s.getMaxEnemyLevel() + ", "
                     + s.getDifficulty() + ", " + s.getSeed() + ", " 
                     + s.getCredits() + (s.getItems().equals("") ? "" : ", " 
                     + s.getItems()) 
                     + (s.getItemCount() != null ? (", " + s.getItemCount()) : ""));
            counter++;
        }
    }
    
    public String getMissionType(){
        return missionType;
    }
    
    public String getFaction(){
        return faction;
    }
    
    public String getMinEnemyLevel(){
        return minEnemyLevel;
    }
    
    public String getMaxEnemyLevel() {
        return maxEnemyLevel;
    }
    
    public String getDifficulty(){
        return difficulty;
    }
    
    public String getSeed(){
        return seed;
    }
    
    public String getCredits(){
        return credits;
    }
    
    public String getItems(){
        return items;
    }
    
    public String getItemCount(){
        return itemCount;
    }
    
    public void log(){
        System.out.println("missionType: " + missionType + ", faction: " + faction + ", minEnemyLevel: " + minEnemyLevel +
                ", maxEnemyLevel:" + maxEnemyLevel + ", difficulty: " + difficulty + ", seed: " + seed + ", credits: " + credits +
                ", items: " + items);  
    }

}
