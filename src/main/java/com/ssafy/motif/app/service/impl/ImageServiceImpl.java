package com.ssafy.motif.app.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.motif.app.dto.image.ProfileImage;
import com.ssafy.motif.app.mapper.ImageMapper;
import com.ssafy.motif.app.service.ImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
	private final ImageMapper imageMapper;

	@Override
	public String profilePicAdd(MultipartFile file, Long memberId) {
		String storedName=UUID.randomUUID().toString()
				+"_"+file.getOriginalFilename()+file.getContentType();
		
		String path="classpath:/static/images/profile";
		String imagePath=path+storedName;
		
		ProfileImage profilePic;
		
		profilePic = ProfileImage.builder()
		.memberId(memberId)
		.originalName(file.getOriginalFilename())
		.storedName(storedName)
		.imageUrl(imagePath)
		.build();
		//.imageData(file.getBytes())
			
			
		// 프사 서버에 저장
		try {
			file.transferTo(Paths.get(imagePath));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DB에 저장
		imageMapper.profilePicAdd(profilePic);
	
		return storedName;
		
	}
	
	
}
