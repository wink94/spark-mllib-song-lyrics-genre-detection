package com.windula.sparkapi.controller;


import com.windula.sparkapi.service.SparkService;
import com.windula.sparkapi.service.transformer.util.GenrePrediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/lyrics")
public class LyricsController {


    @Autowired
    private SparkService service;

    @RequestMapping(value = "/train", method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> trainLyricsModel() {
        Map<String, Object> trainStatistics = service.trainModel();

        return new ResponseEntity<>(trainStatistics, HttpStatus.OK);
    }

    @PostMapping("/predict")
    ResponseEntity<Map<String, Object>> predictGenre(@RequestBody Map<String, String> payload) {
        try {
            GenrePrediction trainStatistics = service.predictGenre(payload.get("unknownLyrics").toString());
            trainStatistics.setProbabilityMap();

            Map<String, Object> map = new HashMap<>();
            for (Field field : trainStatistics.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(trainStatistics));
            }
//        return new ResponseEntity<>(trainStatistics, HttpStatus.OK);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping(value = "/train/word2vec", method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> trainWord2VecLyricsModel() {
        Map<String, Object> trainStatistics = service.trainWord2Vec();

        return new ResponseEntity<>(trainStatistics, HttpStatus.OK);
    }

    @PostMapping("/predict/word2vec")
    ResponseEntity<List< Object>> predictWord2VecLyricsModel(@RequestBody Map<String, String> payload) {
        List< Object> trainStatistics = Collections.singletonList(service.predictWord2Vec(payload.get("unknownLyrics")));

        return new ResponseEntity<>(trainStatistics, HttpStatus.OK);
    }

}
