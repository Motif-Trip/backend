package com.ssafy.motif.app.domain.mapper;

import com.ssafy.motif.app.domain.dto.image.ProfileImage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

    void profilePicAdd(ProfileImage profileImage);

    ProfileImage profilePicLoad(String email);

    void profilePicRemove(String email);
}