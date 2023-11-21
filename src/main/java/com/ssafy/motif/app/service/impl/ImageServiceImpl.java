package com.ssafy.motif.app.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.motif.app.dto.image.ProfileImage;
import com.ssafy.motif.app.mapper.ImageMapper;
import com.ssafy.motif.app.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
	private final ImageMapper imageMapper;
	
	@Value("${resources.path}")
	private String resources;

	@Override
	public String profilePicAdd(@RequestParam MultipartFile file, String email) {
		System.out.println("file:"+file);
		
		String storedName=UUID.randomUUID().toString()
				+"_"+file.getOriginalFilename();
		
		// String path="classpath:static/images/profile/";
		String imagePath=Paths.get(resources, "profile", storedName).toString();
		log.debug("image path:"+imagePath);
		ProfileImage profilePic;
		
		profilePic = ProfileImage.builder()
		.email(email)
		.originalName(file.getOriginalFilename())
		.storedName(storedName)
		.imageUrl(imagePath)
		.build();
		
		//.imageData(file.getBytes())
			
		try {
			// 프사 서버에 저장
			log.info("프사 저장 시도중");
			log.info("확장자명 테스트:"+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1));
			file.transferTo(Paths.get(imagePath));
			
			// 압축 프로세스는 오류 나서 보류. (아예 실패. 결과 파일 용량 0 Byte
			/*
			BufferedImage image=ImageIO.read(file.getInputStream());
			// 저장할 곳
			File output=new File(imagePath);
			OutputStream out=new FileOutputStream(output);
			
			// 이미지 출력 설정
			ImageWriter writer=ImageIO.getImageWritersByFormatName(file.getContentType()).next();
			ImageOutputStream ios=ImageIO.createImageOutputStream(out);
			writer.setOutput(ios);
			
			// 스트림 인코딩 방법
			ImageWriteParam param=writer.getDefaultWriteParam();
			
			if(param.canWriteCompressed()) { // writer가 압축을 지원하는지 확인
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 압축 설정 명시적으로 지정
				param.setCompressionQuality(0.05f); // 압축 품질.
			}
			
			// IIOImage: 이미지 세트, 이미지와 연결된 메타데이터를 나타낸 객체를 집계하는 컨테이너
			// IIOImage(렌더된 이미지, 썸네일, 연관 메타데이터)
			writer.write(null, new IIOImage(image, null, null), param);
			
			// 리소스 해제
			out.close();
			ios.close();
			writer.dispose();
			// multipartfile to bufferedimage
				
			*/
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DB에 저장
//		imageMapper.profilePicAdd(profilePic);
	
		// System.out.println("저장될 이름:"+storedName);
		return storedName;
		
	}

	@Override
	public Image profilePicLoad(String email) {
		// TODO Auto-generated method stub
		ProfileImage profileImage=imageMapper.profilePicLoad(email);
		
		File imageFile=new File(profileImage.getImageUrl());
		
		try {
			BufferedImage image=ImageIO.read(imageFile);
			
			return image;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 이미지 로드 실패
		return null;
	}
	
	
}
