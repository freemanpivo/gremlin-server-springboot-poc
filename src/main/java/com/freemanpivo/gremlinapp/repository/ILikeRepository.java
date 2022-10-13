package com.freemanpivo.gremlinapp.repository;

public interface ILikeRepository {
    void save(String entityIdFromPersonWhoLiked, String entityIdOfLikedPerson);
}
