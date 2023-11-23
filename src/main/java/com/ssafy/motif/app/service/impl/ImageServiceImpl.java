package com.ssafy.motif.app.service.impl;

import com.ssafy.motif.app.domain.dto.image.ProfileImage;
import com.ssafy.motif.app.domain.mapper.ImageMapper;
import com.ssafy.motif.app.service.ImageService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageMapper imageMapper;

    // 프사 실제 저장 경로
    @Value("${upload.profile.path}")
    private String resources;

    @Override
    public String profilePicAdd(@RequestParam MultipartFile file, String email) {
        String storedName = UUID.randomUUID().toString()
            + "_" + file.getOriginalFilename();

        // String path="classpath:static/images/profile/";
        String imagePath = Paths.get(resources, email, storedName).toString();
//		log.debug("image path:"+imagePath);
        ProfileImage profilePic;

        profilePic = ProfileImage.builder()
            .email(email)
            .originalName(file.getOriginalFilename())
            .storedName(storedName)
            .imageUrl(imagePath)
            .build();

        //.imageData(file.getBytes())

        // 사전 폴더 체크
        // 플젝 경로/profile/유저 id
        String userFolder = Paths.get(resources, email).toString();
        File folder = new File(userFolder);

        if (!folder.exists()) { // 프로젝트 경로에 폴더가 없다면 생성
            folder.mkdir();
            log.info("폴더 생성함");
            log.info("folder path:" + resources);
        } else {
            // 안에 프사가 하나라도 있으면 add할 수 없다.
            if (folder.isDirectory()) { // 폴더 디렉터리라면 내부 파일 수 체크
                if (folder.listFiles().length > 0) {
                    log.info("현재 프로필 사진이 이미 추가된 상태입니다.");
                    return "";
                }
            }
        }

        log.debug("image path:" + imagePath);

        try {
            // 압축 시도

            // 업로드한 이미지 오픈
            BufferedImage uploadimage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

            log.info("파일의 타입:" + file.getContentType());
            // 이미지 출력 설정
            ImageWriter writer = ImageIO.getImageWritersByFormatName(
                file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf(".") + 1)).next();

            if (writer == null) {
                log.info("writer가 null이네요");
                System.out.println("writer가 null");
            }

            // 스트림 인코딩 방법
            ImageWriteParam param = writer.getDefaultWriteParam();

            if (param.canWriteCompressed()) { // writer가 압축을 지원하는지 확인
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 압축 설정 명시적으로 지정
                param.setCompressionQuality(0.05f); // 압축 품질.
                log.info("압축을 지원함.");
            } else {
                log.info("압축을 지원하지 않습니다");
            }

            // 이미지파일 출력
            FileImageOutputStream ios = new FileImageOutputStream(new File(imagePath));
            writer.setOutput(ios);

            // IIOImage: 이미지 세트, 이미지와 연결된 메타데이터를 나타낸 객체를 집계하는 컨테이너
            // IIOImage(렌더된 이미지, 썸네일, 연관 메타데이터)
            writer.write(null, new IIOImage(uploadimage, null, null), param);

            // 리소스 해제

            writer.dispose();
            ios.close();

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // DB에 저장
        imageMapper.profilePicAdd(profilePic);

        // System.out.println("저장될 이름:"+storedName);
        return storedName;

    }

    @Override
    public ProfileImage profilePicLoad(String email) {
        // TODO Auto-generated method stub
        // 이메일로 저장되어 있는 사진 불러오기
        return imageMapper.profilePicLoad(email);
    }

    @Override
    public void profilePicRemove(String email) {
        // TODO Auto-generated method stub

        // original name 추출을 위한 load
        ProfileImage img = imageMapper.profilePicLoad(email);

        String deletePath = Paths.get(resources, email, img.getStoredName()).toString();

        log.info(deletePath + "파일을 삭제합니다.");
        File file = new File(deletePath);

        // 폴더에서 제거
        if (file.delete()) {
            log.info("제거 완료");
        } else {
            log.error("제거 실패");
        }

        // 테이블에서 제거
        imageMapper.profilePicRemove(email);
    }

    @Override
    public void profilePicUpdate(MultipartFile file, String email) {
        // TODO Auto-generated method stub
        // 원래 있었던 프사 데이터 제거 후
        log.info("수정하는 분 이메일:" + email);
        profilePicRemove(email);

        log.info("update: 기존 파일 제거 완료");
        // 프사 업로드
        profilePicAdd(file, email);
    }
}
