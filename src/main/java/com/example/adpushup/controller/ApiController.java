package com.example.adpushup.controller;


import com.example.adpushup.bean.Advertise;
import com.example.adpushup.bean.Website;
import com.example.adpushup.helper.Constant;
import com.example.adpushup.helper.ResponseData;
import com.example.adpushup.service.AppService;
import com.example.adpushup.service.AppServiceImpl;
import net.minidev.json.JSONObject;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {


    private AppService appService = new AppServiceImpl();


    @RequestMapping(value="/app/generateData" , method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> loadData(@RequestBody JSONObject data){
        appService.loadData(data.getAsString("path"));
        return new ResponseEntity<ResponseData<String>>(new ResponseData(Constant.SUCCESS.toString(),"data generated successfully "), HttpStatus.OK);

    }
    
    @RequestMapping(value="/app/websiteByAdvtisor/{advtisor}" , method = RequestMethod.GET)
    public @ResponseBody List<Website> websiteByAdvtisor(@PathVariable String  advtisor){
        return appService.websiteByAdvtisor(advtisor);
    }
    
    @RequestMapping(value="/app/websiteByAdId/{adId}" , method = RequestMethod.GET)
    public @ResponseBody List<Website> websiteByAdId(@PathVariable String adId){
        return appService.websiteByAdId(adId);
    }
    
    @RequestMapping(value="/app/allAdvtisor" , method = RequestMethod.GET)
    public @ResponseBody List<String> allAdvtisor(){
        return appService.allAdvtisor();
    }
    
    @RequestMapping(value="/app/advtisorByWebsite/{website}" , method = RequestMethod.GET)
    public @ResponseBody List<Advertise> advtisorByWebsite(@PathVariable String website){
        return appService.advtisorByWebsite(website);
    }

}
