package pramati.wc.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WCFileHandler {
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
			System.out.println("Directory "+dirPath+" created successfully!!!");
		}else{
			throw new Exception("FAILED_TO_CREATE_DIRECTORY: please check if you have proper permissions");
			}
		}else{
			System.out.println("Directory "+dirPath+" Already Exists!!!");
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
			e.printStackTrace();
		}finally{
			try {
				fw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
