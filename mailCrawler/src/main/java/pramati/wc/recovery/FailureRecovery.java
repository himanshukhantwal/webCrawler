package pramati.wc.recovery;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import pramati.wc.datatypes.MessagesDatatype;
import pramati.wc.utils.WCFileHandler;

public class FailureRecovery {
	private static final Logger log=Logger.getLogger(FailureRecovery.class);
	private static FailureRecovery instance;
	private FailureRecovery(){}
	private Map<String,ArrayList<MessagesDatatype>> recoveryMap=new HashMap<String, ArrayList<MessagesDatatype>>();
	private int yrNeedsToBeInspctd;
		
	public static FailureRecovery getInstance(){
		if(instance!=null)
			return instance;
		else
			return instance=new FailureRecovery();
	}
	
	public void createRecoveryMap(int yrNeedsToBeInspctd){
		this.yrNeedsToBeInspctd=yrNeedsToBeInspctd;
		String dirForRecMnth="web_crawler/Recovery/"+"Year_"+yrNeedsToBeInspctd;
		File[] dirList = WCFileHandler.getInstance().getFileListFrmDir(dirForRecMnth);
		for(File dirEntry:dirList){
				if(dirEntry.isDirectory()){
					populateMapForMnth(dirEntry);
				}
			}
		log.info("**Creation of Recovery List Finised **");
		for(String snglMnth:recoveryMap.keySet()){
		log.info("Failure recovery :"+recoveryMap.get(snglMnth).size()+" -files {"+snglMnth+"}"+" -already downloaded");
		}
	}

	private void populateMapForMnth(File dirEntry) {
		String mnth=((dirEntry.getName()).split("_"))[1];
		String mnthAndyr=mnth+" "+yrNeedsToBeInspctd;
		ArrayList<MessagesDatatype> fileListUnderMnth=getFileListUnderMnth(mnth);
		this.recoveryMap.put(mnthAndyr, fileListUnderMnth);		
	}

	private ArrayList<MessagesDatatype> getFileListUnderMnth(String mnth) {
		String dirForFileInMnthDir ="web_crawler/Recovery/"+"Year_"+yrNeedsToBeInspctd+"/Month_"+mnth;
		File[] fileList = WCFileHandler.getInstance().getFileListFrmDir(dirForFileInMnthDir);
		ArrayList<MessagesDatatype> al=new ArrayList<MessagesDatatype>();
		for(File fileEntry:fileList){
			if(fileEntry.isFile()){
				MessagesDatatype messagesDatatype=createMessageDtTypFrmName(fileEntry.getName());
				al.add(messagesDatatype);
			}
		}
		return al;
	}

	private MessagesDatatype createMessageDtTypFrmName(String name) {
		String[] token=(name.replaceAll("-or-", "/")).split("-And-");
		return new MessagesDatatype(token[1], token[0], token[2], null);
	}
	
	public ArrayList<MessagesDatatype> getRecoveryMap(String mnthYr){
		return recoveryMap.get(mnthYr);
	}
}
