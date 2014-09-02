/*  
 * @(#) FileTools.java Create on 2014-6-4 上午10:28:58   
 *   
 * Copyright 2014 by yhx.   
 */

package fruits.framework.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * @FileTools.java
 * @created at 2014-6-4 上午10:28:58 by zhanghl
 * 
 * @desc
 * 
 * @author zhanghl({@link 253587517@qq.com})
 * @version $Revision$
 * @update: $Date$
 */
public class FileTools extends FileUtils {
	public static Logger logger = Logger.getLogger("FileTools");
	/**
	 * <pre>
	 * 拆分目录，根据handler
	 * 
	 * inputDir 中文件很多，根据文件创建时间 年月日把文件放到不同目录
	 * 可以自定义handler，制定不同拆分规则
	 * 
	 * </pre>
	 * 
	 * @Title: splitContents
	 * @data:2014-6-4上午10:42:46
	 * @author:zhanghl
	 * 
	 * @param inputDir
	 * @param outputDir
	 * @param handler
	 * @throws IOException
	 */
	public static void splitContents(File inputDir, File outputDir, SpliteHandler handler) throws IOException {
		if(inputDir==null || outputDir==null){
			throw new IOException("传入参数不能为null");
		}
		
		if (!inputDir.exists()) {
			throw new IOException("目录[" + inputDir.getPath() + "] 不存在，请检查");
		}
		if (!inputDir.isDirectory()) {
			throw new IOException("[" + inputDir.getPath() + "] 不是目录，请检查");
		}
		handler=handler==null?new SpliteByYearMouthHandler():handler;
		
		//取当前目录下的文件
		File[] files = inputDir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});
		
		for (File file : files) {
			handler.splite(file,outputDir);
		}

		//取当前目录下的文件夹
		File[] fileDirs = inputDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		
		for (File file : fileDirs) {
			splitContents(file, outputDir, handler);
		}
	}
	
	public static void splitContents(File inputDir, File outputDir) throws IOException{
		splitContents(inputDir, outputDir, null);
	}
	
	public static void splitContents(File inputDir) throws IOException{
		if(inputDir==null){
			throw new IOException("传入参数为null，请检查");
		}
		File outputDir = inputDir.getParentFile();
		splitContents(inputDir, outputDir);
	}

	public static void main(String[] args) {
//		File file = FileTools.getFile("F:/.tmp/照片/尼康/DCIM/101NIKON/DSCN3043.JPG");
		File inputDir = FileTools.getFile("F:/.tmp/照片/尼康/DCIM");
		File outputDir = inputDir.getParentFile();
		try {
			FileTools.splitContents(inputDir, outputDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		long modifiedTime = file.lastModified();
//		String dt = DateFormatUtils.ISO_DATE_FORMAT.format(new Date(modifiedTime));
//
//		System.out.println(dt);
		
	}
}

interface SpliteHandler {
	public void splite(File file,File outputFile)throws IOException;
}

class SpliteByYearMouthHandler implements SpliteHandler{
	@Override
	public void splite(File file, File outputFile) throws IOException {
		long modifiedTime = file.lastModified();
		FastDateFormat dataFormat =  FastDateFormat.getInstance("yyyy"+File.separator+"yyyy-MM");
		String dt = dataFormat.format(modifiedTime);
		String path = outputFile.getPath() + File.separator + dt;
		
		FileTools.copyFileToDirectory(file, new File(path));
	}
}
