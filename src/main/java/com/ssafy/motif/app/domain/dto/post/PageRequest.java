package com.ssafy.motif.app.domain.dto.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PageRequest { // 페이징 요청을 위한 Dto
	private int pageId;
	private int size;
	
	public int getSkip() {
		return (pageId-1)*size;
	}

}
