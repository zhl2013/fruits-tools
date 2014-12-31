/*  
 * @(#) PhotoTools.java Create on 2014-9-4 下午2:49:37   
 *   
 * Copyright 2014 by yhx.   
 */

package fruits.framework.tools.image;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;

/**
 * @PhotoTools.java
 * @created at 2014-9-4 下午2:49:37 by zhanghl
 * 
 * @desc
 * 
 * @author zhanghl({@link 253587517@qq.com})
 * @version $Revision$
 * @update: $Date$
 */
public class PhotoTools {
	/**
	 * 获取照片exif 信息，拍摄时间，坐标等
	 * 
	 * @Title: getPhotoExifInfo
	 * @data:2014-9-4下午2:50:15
	 * @author:zhanghl
	 * 
	 * @param file
	 * @return
	 */
	public static Map<String, String> getPhotoExifInfo(File file) {
		Map<String, String> result = new HashMap<String, String>();
		
		if(file==null || !file.exists()){
			return result;
		}

		try {
			Metadata metadata;
			metadata = JpegMetadataReader.readMetadata(file);
			Directory directory = metadata.getDirectory(ExifIFD0Directory.class);

			for (Tag tag : directory.getTags()) {
//				System.out.println(tag.getTagName());
//				System.out.println(tag);
//				System.out.println("-------------------------------");
				result.put(tag.getTagName(), tag.getDescription());
			}
		} catch (JpegProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 取拍摄时间
	 * @Title: getShootingTime
	 * @data:2014-9-4下午2:57:29
	 * @author:zhanghl
	 *
	 * @param file
	 * @return
	 */
	public static String getShootingTime(File file){
		return getPhotoExifInfo(file).get("Date/Time");
	}
}
