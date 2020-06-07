package com.example.adpushup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.adpushup.bean.Account;
import com.example.adpushup.bean.Advertise;
import com.example.adpushup.bean.Website;
import com.example.adpushup.helper.MysqlConnector;

public class AdPusherDAOImpl implements AdPusherDAO {

	
	
	@Override
	public int addWebsite(String url) {
		
		
		
		
		String sql = "INSERT INTO website (url) values (?)";
		String lastId = "SELECT LAST_INSERT_ID()";

        ResultSet rsObj = null;
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        try {
            connObj = MysqlConnector.getInstance().getConnection();
	        if(connObj != null) {
	        	pstmtObj = connObj.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
	            pstmtObj.setString(1, url);
	            pstmtObj.executeUpdate();
	            
	            ResultSet rs = pstmtObj.getGeneratedKeys();
	            int id = -1;
	            if (rs.next()){
	                id=rs.getInt(1);
	            }
	            
	            return id;
	        }
        } catch(Exception sqlException) {
                sqlException.printStackTrace();
        } finally {
            try {
                if(rsObj != null) {
                    rsObj.close();
                }
                if(pstmtObj != null) {
                    pstmtObj.close();
                }
                if(connObj != null) {
                    connObj.close();
                }
            } catch(Exception sqlException) {
                sqlException.printStackTrace();
            }
        }
		return -1;
      
	}

	@Override
	public String removeData() {
			String sql = "delete from  website";
			String sql2 = "delete from Advertise";
	        ResultSet rsObj = null;
	        Connection connObj = null;
	        PreparedStatement pstmtObj = null;
	        try {
	        	connObj = MysqlConnector.getInstance().getConnection();
		        if(connObj != null) {
		        	pstmtObj = connObj.prepareStatement(sql);
			        pstmtObj.execute();
			        
			        pstmtObj.close();
			        pstmtObj = connObj.prepareStatement(sql2);
			        pstmtObj.execute();
			        
			        
			    } 
	        } catch(Exception sqlException) {
	            sqlException.printStackTrace();
	        } finally {
	            try {
	                if(rsObj != null) {
	                    rsObj.close();
	                }
	                if(pstmtObj != null) {
	                    pstmtObj.close();
	                }
	                
	                if(connObj != null) {
	                    connObj.close();
	                }
	            } catch(Exception sqlException) {
	                sqlException.printStackTrace();
	            }
	        }
	    return null;
	}

	@Override
	public String addAdvertiseMent(String advertiser, String adId, String accountType, int webId) {
		
		String sql = "INSERT INTO Advertise (adId , webId , advtisor , account) values (?,?,?,?)";
        ResultSet rsObj = null;
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        try {
            connObj = MysqlConnector.getInstance().getConnection();
	        if(connObj != null) {
	        	pstmtObj = connObj.prepareStatement(sql);
	            pstmtObj.setString(1, adId);
	            pstmtObj.setInt(2, webId);
	            pstmtObj.setString(3, advertiser);
	            pstmtObj.setString(4, accountType);
	            pstmtObj.executeUpdate();
	            
	           
	        
	        
	        }
	        
        } catch(Exception sqlException) {
                sqlException.printStackTrace();
                System.out.println(advertiser+" "+adId+" "+webId+" "+accountType);
        } finally {
            try {
                if(rsObj != null) {
                    rsObj.close();
                }
                if(pstmtObj != null) {
                    pstmtObj.close();
                }
                if(connObj != null) {
                    connObj.close();
                }
            } catch(Exception sqlException) {
                sqlException.printStackTrace();
            }
        }
		return null;
	}
	
	
	
	
	@Override
	public List<Advertise> advtisorOnWebsite(String  webUrl) {
		String sql = "select ad.* from Advertise ad join website wb on wb.id = ad.webId where url = ?";
        ResultSet rsObj = null;
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        List<Advertise> advtisor = new ArrayList<Advertise>();
        try {
            connObj = MysqlConnector.getInstance().getConnection();
	        if(connObj != null) {
	        	pstmtObj = connObj.prepareStatement(sql);
	            pstmtObj.setString(1, webUrl);
	            rsObj = pstmtObj.executeQuery();
	            
	            while(null!=rsObj && rsObj.next()) {
	            	Advertise ad = new Advertise();
	            	ad.setId(rsObj.getInt("id"));
	            	ad.setAdvtisor(rsObj.getString("advtisor"));
	            	ad.setWebId(rsObj.getString("webId"));
	            	ad.setAdId(rsObj.getString("adId"));
	            	ad.setAccount(Account.valueOf(rsObj.getString("account")));
	            	advtisor.add(ad);
	            }
	        }
	    } catch(Exception sqlException) {
                sqlException.printStackTrace();
        } finally {
            try {
                if(rsObj != null) {
                    rsObj.close();
                }
                if(pstmtObj != null) {
                    pstmtObj.close();
                }
                if(connObj != null) {
                    connObj.close();
                }
            } catch(Exception sqlException) {
                sqlException.printStackTrace();
            }
        }
		return advtisor;
	}	
	
	
	@Override
	public List<Website> websiteOnAdvtisor(String advtisor) {
		String sql = "select wb.* from website wb join Advertise ad on wb.id = ad.webId and ad.advtisor = ?";
        ResultSet rsObj = null;
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        List<Website> website = new ArrayList<Website>();
        try {
            connObj = MysqlConnector.getInstance().getConnection();
	        if(connObj != null) {
	        	pstmtObj = connObj.prepareStatement(sql);
	            pstmtObj.setString(1, advtisor);
	            rsObj = pstmtObj.executeQuery();
	            
	            while(null!=rsObj && rsObj.next()) {
	            	Website wb = new Website(rsObj.getInt("id"),rsObj.getString("url"));
	            	website.add(wb);
	            }
	        }
	    } catch(Exception sqlException) {
                sqlException.printStackTrace();
        } finally {
            try {
                if(rsObj != null) {
                    rsObj.close();
                }
                if(pstmtObj != null) {
                    pstmtObj.close();
                }
                if(connObj != null) {
                    connObj.close();
                }
            } catch(Exception sqlException) {
                sqlException.printStackTrace();
            }
        }
		return website;
	}

	@Override
	public List<Website> websiteOnAdvtisorId(String adId) {
		String sql = "select wb.* from website wb join Advertise ad on wb.id = ad.webId and ad.adId = ?";
        ResultSet rsObj = null;
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        List<Website> website = new ArrayList<Website>();
        try {
            connObj = MysqlConnector.getInstance().getConnection();
	        if(connObj != null) {
	        	pstmtObj = connObj.prepareStatement(sql);
	            pstmtObj.setString(1, adId);
	            rsObj = pstmtObj.executeQuery();
	            
	            while(null!=rsObj && rsObj.next()) {
	            	Website wb = new Website(rsObj.getInt("id"),rsObj.getString("url"));
	            	website.add(wb);
	            }
	        }
	    } catch(Exception sqlException) {
                sqlException.printStackTrace();
        } finally {
            try {
                if(rsObj != null) {
                    rsObj.close();
                }
                if(pstmtObj != null) {
                    pstmtObj.close();
                }
                if(connObj != null) {
                    connObj.close();
                }
            } catch(Exception sqlException) {
                sqlException.printStackTrace();
            }
        }
		return website;
	}
	
	
	
	@Override
	public List<String> allAdvtisor() {
		String sql = "select distinct(advtisor) from  Advertise";
        ResultSet rsObj = null;
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        List<String> website = new ArrayList<String>();
        try {
            connObj = MysqlConnector.getInstance().getConnection();
	        if(connObj != null) {
	        	pstmtObj = connObj.prepareStatement(sql);
	            rsObj = pstmtObj.executeQuery();
	            
	            while(null!=rsObj && rsObj.next()) {
	            	website.add(rsObj.getString("advtisor"));
	            }
	        }
	    } catch(Exception sqlException) {
                sqlException.printStackTrace();
        } finally {
            try {
                if(rsObj != null) {
                    rsObj.close();
                }
                if(pstmtObj != null) {
                    pstmtObj.close();
                }
                if(connObj != null) {
                    connObj.close();
                }
            } catch(Exception sqlException) {
                sqlException.printStackTrace();
            }
        }
		return website;
	}
	
	
	
}
