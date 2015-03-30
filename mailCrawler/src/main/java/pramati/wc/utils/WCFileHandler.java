package pramati.wc.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class WCFileHandler {
	private static final Logger log=Logger.getLogger(WCFileHandler.class);
	private static WCFileHandler instance=null;
	private WCFileHandler(){}
	
	public static WCFileHandler getInstance() {
		if(instance!=null){
			return instance;
		}else
			return instance=new WCFileHandler();
	}

	public void createDir(String dirPath) throws Exception {
		File file=new File(dirPath);
		if(!file.exists()){
		if(file.mkdirs()){
			log.info("Directory "+dirPath+" created successfully!!!");
		}else{
			log.error("FAILED_TO_CREATE_DIRECTORY: please check if you have proper permissions");
			throw new Exception();
			}
		}
	}

	public void createFileAndWriteTxt(String fileName, String dirPath,
			String textTosave) {
		fileName=fileName.replaceAll("/", "or");
		FileWriter fw=null;
		File file=new File(dirPath,fileName);
		try{
		file.createNewFile();
		fw=new FileWriter(file);
		fw.write(textTosave);
		}catch(IOException e){
			log.error("error occured while writing: file {"+fileName+"} in dir {"+dirPath+"}",e);
		}finally{
			try {
				fw.flush();
				fw.close();
			} catch (IOException e) {
				log.error("error occured while flush: file {"+fileName+"} in dir {"+dirPath+"}",e);
			}
		}
	}
	
}
