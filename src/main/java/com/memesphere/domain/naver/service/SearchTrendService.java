package com.memesphere.domain.naver.service;

import com.memesphere.domain.naver.dto.request.SearchRequest;
import com.memesphere.domain.naver.dto.response.SearchResponse;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SearchTrendService {

    private final RestTemplate restTemplate;

    @Value("${naver.url}")
    private String apiUrl;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.secret}")
    private String clientSecret;

    public SearchResponse getSearchTrends(SearchRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Naver-Client-Id", clientId);
            headers.set("X-Naver-Client-Secret", clientSecret);

            HttpEntity<SearchRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<SearchResponse> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    SearchResponse.class
            );

            return responseEntity.getBody();

        } catch (HttpClientErrorException.BadRequest e) {
            throw new GeneralException(ErrorStatus.BAD_REQUEST);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new GeneralException(ErrorStatus.KEY_UNAUTHORIZED);
        } catch (HttpClientErrorException.Forbidden e) {
            throw new GeneralException(ErrorStatus.API_FORBIDDEN);
        } catch (HttpServerErrorException e) {
            throw new GeneralException(ErrorStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
