package com.atguigu.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {

	public static List<String> save(MultipartFile[] files) {

		List<String> image_pathList = new ArrayList<>();
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				String image = UUID.randomUUID().toString() + file.getOriginalFilename();
				image_pathList.add(image);
				try {
					file.transferTo(new File(PropertiesUtil.getPath("imagePath.properties", "window_image") + image));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return image_pathList;

	}
}
