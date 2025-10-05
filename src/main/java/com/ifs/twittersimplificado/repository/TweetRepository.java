package com.ifs.twittersimplificado.repository;


import com.ifs.twittersimplificado.entities.Tweet;
import com.ifs.twittersimplificado.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
