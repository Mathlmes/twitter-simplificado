package com.ifs.twittersimplificado.controller;


import com.ifs.twittersimplificado.controller.dto.CreateTweetDTO;
import com.ifs.twittersimplificado.controller.dto.FeedDto;
import com.ifs.twittersimplificado.controller.dto.FeedItemDto;
import com.ifs.twittersimplificado.entities.Role;
import com.ifs.twittersimplificado.entities.Tweet;
import com.ifs.twittersimplificado.repository.TweetRepository;
import com.ifs.twittersimplificado.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class CreateTweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public CreateTweetController(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/tweets")//post mapping para criar tweet
    //testando ssh
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDTO dto,
                                            JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));
        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());
        tweetRepository.save(tweet);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDto> feed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        var tweets = tweetRepository.findAll(
                        PageRequest.of(page, pageSize, Sort.Direction.DESC, "timestamp"))
                .map(tweet ->
                        new FeedItemDto(
                                tweet.getTweetId(),
                                tweet.getContent(),
                                tweet.getUser().getUsername())
                );

        return ResponseEntity.ok(new FeedDto(
                tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements()));
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId,
                                            JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var isAdmin = user.get().getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.values.ADMIN.name()));


        var tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(isAdmin || tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))){
            tweetRepository.deleteById(tweetId);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok().build();
    }
}
