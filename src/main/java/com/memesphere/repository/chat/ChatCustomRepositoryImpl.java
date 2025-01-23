package com.memesphere.repository.chat;

import com.memesphere.domain.Chat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class ChatCustomRepositoryImpl implements ChatCustomRepository {

    private final EntityManager entityManager;

    public ChatCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Chat findLatestMessageByCoinId(Long coin_id) {
        // 최신 댓글 1개를 가져오는 JPQL 쿼리
        String jpql = "SELECT c FROM Chat c WHERE c.memeCoin.id = :coin_id ORDER BY c.createdAt DESC";

        // 쿼리 실행 및 첫 번째 결과만 반환
        try {
            return entityManager.createQuery(jpql, Chat.class)
                    .setParameter("coin_id", coin_id)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // 댓글이 없으면 null 반환
        }
    }

}
