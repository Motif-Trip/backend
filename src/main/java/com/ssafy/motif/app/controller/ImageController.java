package com.ssafy.motif.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.motif.app.domain.dto.image.ProfileImage;
import com.ssafy.motif.app.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profileImages")
public class ImageController {	
	private final ImageService imageService;
	
	// 프사 등록
	@PostMapping
	public ResponseEntity<?> profilePicAdd(MultipartFile file, Authentication authentication){
		// log.info(file.getOriginalFilename());
		log.info(System.getProperty("user.dir"));
		
		// 저장될 프사 파일명 반환.
		// 프사 접근 시 localhost:8080/profile/이메일/저장될 파일명
		String result=imageService.profilePicAdd(file, authentication.getName());
		
		if(result.contentEquals("")) { // 프사가 이미 추가되어 있음
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}else {
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		}
	}
	
	// 프사 수정
		@PutMapping
		public ResponseEntity<?> profilePicUpdate(MultipartFile file, Authentication authentication){
			// log.info("수정하려는 사람 이메일:"+authentication.getName());
			imageService.profilePicUpdate(file, authentication.getName());
			
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	
	
	// 프사 불러오기
	@GetMapping
	public ResponseEntity<?> profilePicLoad(Authentication authentication){
		ProfileImage loadPic=imageService.profilePicLoad(authentication.getName());
		// 프사 실제 경로
		String imagePath=loadPic.getImageUrl();
		try {
			// 바이트 배열로 뽑아내기
			byte[] byteImage=Files.readAllBytes(Paths.get(imagePath));
			// BufferedImage image=ImageIO.read(imageFile);
			
			log.info("이미지를 가져왔습니다.");
			
			// 이미지 타입 처리
			
			// 이미지 확장자명
			String orgName=loadPic.getOriginalName().substring(loadPic.getOriginalName().lastIndexOf(".")+1);
			
			log.info("컨트롤러에서 추출한 이미지 확장자명:"+orgName);
			MediaType mediaType;
			
			switch(orgName) {
			case "jpg":
			case "jpeg":
				mediaType=MediaType.IMAGE_JPEG;
				break;
			case "png":
				mediaType=MediaType.IMAGE_PNG;
				break;
			case "gif":
				mediaType=MediaType.IMAGE_GIF;
				break;
			default:
				mediaType=MediaType.valueOf("image/"+orgName);
				break;
			}
				
			return ResponseEntity.ok()
					.contentType(mediaType)
					.body(byteImage);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("이미지 가져오기 실패.");
			e.printStackTrace();
		}
		
		// 이미지 가져오기 실패
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	// 프사 삭제하기
	@DeleteMapping
	public ResponseEntity<?> profilePicRemove(Authentication authentication){
		imageService.profilePicRemove(authentication.getName());
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
