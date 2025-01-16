package com.memesphere.converter;

import com.memesphere.domain.MemeCoin;
import com.memesphere.dto.SearchResponseDTO;
import org.springframework.data.domain.Page;

public class SearchConverter {
    public static SearchResponseDTO.SearchListDTO toSearchListDTO(Page<MemeCoin> searchList, String viewType) {
        return null;
    }
}
