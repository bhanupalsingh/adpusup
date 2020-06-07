package com.example.adpushup.service;

import java.io.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.adpushup.bean.Advertise;
import com.example.adpushup.bean.Website;
import com.example.adpushup.dao.AdPusherDAO;
import com.example.adpushup.dao.AdPusherDAOImpl;



public class AppServiceImpl implements  AppService {
	private AdPusherDAO dao = new AdPusherDAOImpl();
	private static final int maxThread = 10;
	private final static Logger log = LogManager.getLogger(AppServiceImpl.class);
	
	
    public void loadData(String path){

        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> list = new ArrayList<>();
            HashSet<String> hashSet = new HashSet<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                hashSet.add(data);
                //System.out.println(data);
            }
            list.addAll(hashSet);

            myReader.close();
            dao.removeData();

            if(list.size() > 0 ){
            	
            	int n = list.size();
            	int m = maxThread;
            	
            	HashMap<Integer , List<String>> map = new HashMap<>();
            	for(int i=0;i<list.size();i++){
            		if(map.containsKey(i%m)) {
            			map.get(i%m).add(list.get(i));
            		}else {
            			map.put(i%m, new ArrayList<String>());
            		}
            		
            		
            	}
            	
            	
            	for(int i=0;i<m;i++) {
            		if(map.containsKey(i)) {
            			try {
            				Thread t = new crawlOperation(map.get(i));
                			t.start();
            			}catch(Exception e) {
            				e.printStackTrace();
            			}
            		}
            	}
            	
            }




        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



    }




    class crawlOperation extends Thread{
        private List<String> site;

        public crawlOperation(List<String> list){
            this.site = list;
        }

        public void run(){
        	
        	for(int j=0;j<this.site.size();j++) {
        		 String eachSite = "http://"+this.site.get(j) + "/ads.txt";
        		 
        		 int id = dao.addWebsite(this.site.get(j));

                 BufferedReader reader = null;
                 
                 try {
                     URL url = new URL(eachSite);
                     InputStream is = url.openStream();
                     InputStreamReader isReader = new InputStreamReader(is);
                     reader = new BufferedReader(isReader);
                     StringBuffer sb = new StringBuffer();
                     String str;
                     
                     int i = 0;
                     
                     while((str = reader.readLine())!= null){
                     	if(!str.trim().isEmpty()){
                             
                     		if(i==0) {
                         		if(!Character.isAlphabetic(str.charAt(0))){
                         			log.error(eachSite+":-"+str);
                         			break;
                         		}
                         	}
                     		
                     		i += 1;
                     		
                     		
                     		if(str.indexOf("#") > -1){
                                 str = str.substring(0,str.indexOf("#"));
                             }
                             if(str.length() > 0){
                                 //sb.append(str);
                                 String[] splitData = str.split(",");
                             	if(splitData.length <= 4 && splitData.length >= 3){
                                 	dao.addAdvertiseMent(splitData[0].trim(),splitData[1].trim(), splitData[2].trim() , id);
                                 }
                             }
                         }
                     }
                     //System.out.println(sb.toString());
                 } catch (IOException e) {

                 } finally {
                 	if(reader != null) {
                 		try {
     						reader.close();
     					} catch (IOException e) {
     						e.printStackTrace();
     					}
                 	}
                 	
                 }
        	}
           

        }
    }




	@Override
	public List<Website> websiteByAdvtisor(String advtisor) {
		return dao.websiteOnAdvtisor(advtisor);
	}




	@Override
	public List<String> allAdvtisor() {
		return dao.allAdvtisor();
	}




	@Override
	public List<Website> websiteByAdId(String adId) {
		return dao.websiteOnAdvtisorId(adId);
		
	}




	@Override
	public List<Advertise> advtisorByWebsite(String website) {
		return dao.advtisorOnWebsite(website);
		
	}



}
