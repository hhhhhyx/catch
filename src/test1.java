
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class test1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
		//	Document  doc1 =Jsoup.connect("https://www.duodia.com/daxuexiaohua").get();
		//	Elements els1=doc1.select("#main > div.row > article> div > a.thumbnail-container > img");
	
			
			Document  doc = 	Jsoup.connect("https://www.duodia.com/daxuexiaohua").get();
			//#main > div.row > article:nth-child(1) > div > a.thumbnail-container > img
			//https://www.duodia.com/daxuexiaohua/list_2.html
			Elements  els=doc.select("#pagination-8510 > option");
			
			String getMax=els.get(els.size()-1).attr("value");
			String getRio=getMax.substring(getMax.indexOf("_")+1);
			String rioMax=getRio.substring(0,getRio.indexOf("."));
			
			int max=Integer.parseInt(rioMax);
	//		System.out.println(getRio);
	//		System.out.println(max);
	//		Elements  els = doc.select("#main > div.row > article> div > a.thumbnail-container > img");
	
			
			for(int i=1;i<max;i++){
				Document  doc2 =Jsoup.connect("https://www.duodia.com/daxuexiaohua/list_"+i+".html").get();
				Elements els2=doc2.select("#main > div.row > article> div > a.thumbnail-container > img");
				
				saveAll(els2);
				System.out.println("抓捕到第"+i+"页");
			}
			

			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		

	}
	

	//存储所有图片
	public static void saveAll(Elements els) throws IOException{
		getFolderNames(els);
		Map<String,String> ImageNames=getImageNames(els);
		Iterator iter=ImageNames.keySet().iterator();
		while(iter.hasNext()){
			String name=(String)iter.next();
			String url=(String)ImageNames.get(name);
			saveImage(url,name);
		}
		
	}
	
	//图片名称
	public static Map getImageNames(Elements els){
		Map<String,String> ImageNames=new HashMap<String,String>();

		for(int i=0;i<els.size();i++){
			String name=els.get(i).attr("alt");
			String url=els.get(i).attr("src");
			ImageNames.put(name, url);
		}
		
		return ImageNames;
	}
	
	//文件夹名称
	public static void getFolderNames(Elements els){
		List folderName=new ArrayList<String>();
		String rioName=null;
		for(int i=0;i<els.size();i++){
			
			String name=els.get(i).attr("alt");
			rioName=name.substring(0, 2);
			if(!folderName.contains(rioName)){
					folderName.add(rioName);
			}	
		}
		createFile(folderName);
	//	return folderName;
	}
	
	//创建文件夹
	public static void createFile(List list){
		
		for(int i=0;i<list.size();i++){
			String path="D:\\图片\\"+(String)list.get(i);
			File file=new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			else{
				
			}
				
		}
	}
	
	//存储图片
	public static void saveImage(String url,String imaName) throws IOException{
		URL urlFile = null;
		urlFile = new URL(url);
		
		URLConnection con=urlFile.openConnection();		
		InputStream is=con.getInputStream();
		byte[] bs=new byte[1024];
		String folName=imaName.substring(0,2);
		String filename="D:\\图片\\"+folName+"/"+imaName+".jpg";
		File file=new File(filename);
		FileOutputStream os=new FileOutputStream(file,true);
		int len;
		while((len=is.read(bs))!=-1){
			os.write(bs, 0, len);
		}
		os.close();
		is.close();
		
	}

}
