/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package channelswitcher;

import com.sun.management.HotSpotDiagnosticMXBean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thorstenstueker
 */
public class Channelswitcher {
public static String channel="",ssidstring="",passwordstring="";
public static boolean fiveghzmode=false;
public static String userhome = System.getProperty("user.home")+"/hdware/hddata/";
public static boolean twoghzonly = false;
public static String hostna;
public static String passwort;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length>0){
            if (args[0].equals("--help")){
                System.out.println("reads hdware/hddata/hostdat.dat which includes 2 entries: first is hostname and sevond is password of the wlan. You can set them automatically to hostapd.conf with this. Set hostapd.conf to 777 before");
                System.out.println("");
            }
        }
        
    try {
        String hostdat=  Files.readString(Path.of(userhome+"hostdata.dat"));
        
        String[] hostsplit = hostdat.split(",");
        hostna=hostsplit[0];
        passwort=hostsplit[1];
        
        File f = new File(userhome+"twoghzonly.set");
        if(f.exists() && !f.isDirectory()) {
            
        }else{
            twoghzonly=true;
            System.out.println("Datei ist nicht da"); // do something
        }
        System.out.println(args[0]);
        
            
            
            
            channel=args[0];
           
            if (Integer.parseInt(channel)>12){
                fiveghzmode=true;
            }
        
         
        
        System.out.println("parser active");
        
        Stringer();
        
        // TODO code application logic here
    } catch (IOException ex) {
        Logger.getLogger(Channelswitcher.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    public static String fileheadstring(){
        String stringstring= "interface=wlan0\n" +
"ssid="+hostna+"\n" +
"ieee80211n=1\n" +
"country_code=DE\n" +
"wmm_enabled=1\n" +
"auth_algs=1\n" +
"wpa=2\n" +
"wpa_key_mgmt=WPA-PSK\n" +
"rsn_pairwise=CCMP\n" +
"wpa_passphrase="+passwort+"\n";

        
        return stringstring;
}

public static String fiveghzfooter(){


String stringstring="hw_mode=a\n" +
"channel="+channel+"\n" +
"ieee80211ac=1";



return stringstring;
}

public static String twoghzFooter(){
    
    String stringstring;
    stringstring = "hw_mode=g\n" +
            "channel="+channel+"\n";

    
    
    
    
    
    return stringstring;
}

public static void Stringer(){
  
    System.out.println(userhome);
   String Contentstring = fileheadstring();
   if (fiveghzmode){
       
    Contentstring=Contentstring+fiveghzfooter();
   
       System.out.println(Contentstring);
    
   }else{
       Contentstring=fileheadstring();
        Contentstring=Contentstring+twoghzFooter();
   
       System.out.println(Contentstring);
       
      
   }
    
      try {
            Files.writeString(Path.of("/etc/hostapd/hostapd.conf"), Contentstring);
        } catch (IOException ex) {
            Logger.getLogger(Channelswitcher.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    
}
}