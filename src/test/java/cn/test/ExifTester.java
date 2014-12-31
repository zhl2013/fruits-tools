/*  
 * @(#) ExifTester.java Create on 2014-9-4 下午2:41:58   
 *   
 * Copyright 2014 by yhx.   
 */


package cn.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;

/**
 * @ExifTester.java
 * @created at 2014-9-4 下午2:41:58 by zhanghl
 *
 * @desc
 *
 * @author zhanghl({@link 253587517@qq.com})
 * @version $Revision$
 * @update: $Date$
 */
public class ExifTester {
	public static void main(String[] args) {
		String fileName = "G:/微云/253587517/pad air/我的照片流/IMG_0002.JPG";
		File jpegFile = new File(fileName);
		try {
			Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
			
			Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
			
			
			for (Tag tag : directory.getTags()) {
				System.out.println(tag.getTagName());
				System.out.println(tag.getDescription());
//				System.out.println(tag);
				System.out.println("-------------------------------");
			}
			
			
			
		} catch (JpegProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
