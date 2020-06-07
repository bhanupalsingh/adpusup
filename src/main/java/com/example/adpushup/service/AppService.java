package com.example.adpushup.service;

import java.util.List;

import com.example.adpushup.bean.Advertise;
import com.example.adpushup.bean.Website;

public interface AppService {

    public void loadData(String path);

	public List<Website> websiteByAdvtisor(String advtisor);

	public List<String> allAdvtisor();

	public List<Website> websiteByAdId(String adId);

	public List<Advertise> advtisorByWebsite(String website);
}
