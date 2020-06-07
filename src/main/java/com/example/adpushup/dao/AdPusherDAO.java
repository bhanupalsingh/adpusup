package com.example.adpushup.dao;

import java.util.List;

import com.example.adpushup.bean.Advertise;
import com.example.adpushup.bean.Website;

public interface AdPusherDAO {
	public int addWebsite(String url);
	public String removeData();
	public String addAdvertiseMent(String advertiser , String adId , String accountType , int webId);
	public List<Website> websiteOnAdvtisor(String advtisor);
	public List<Advertise> advtisorOnWebsite(String webUrl);
	public List<Website> websiteOnAdvtisorId(String adId);
	public List<String> allAdvtisor();
}
