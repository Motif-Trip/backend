package com.ssafy.motif.app.mapper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RefreshTokenMapper {

    /**
     * Refresh-Token - 이메일(UNQ)을 통한 조회
     *
     * @param email (토큰 소유자)
     * @return (토큰 값)
     */
    Optional<String> getRefreshTokenByEmail(
        @Param("email") String email
    );

    /**
     * Refresh-Token - 회원당 최초 1회 생성
     *
     * @param value (토큰 값)
     * @param email (토큰 소유자)
     * @param expirationAt (토큰 만료 시점)
     */
    void createToken(
        @Param("value") String value,
        @Param("email") String email,
        @Param("expirationAt") LocalDateTime expirationAt
    );

    /**
     * Refresh-Token - 만료 시,
     * 기존 토큰을 삭제 하는 것이 아닌 계속 새로 갱신 해서 재사용.
     *
     * @param value (토큰 값)
     * @param email (토큰 소유자)
     * @param expirationAt (토큰 만료 시점)
     */
    void updateToken(
        @Param("value") String value,
        @Param("email") String email,
        @Param("expirationAt") Date expirationAt
    );

}
