package com.windula.sparkapi.service;

import com.windula.sparkapi.service.pipeline.LyricsPipeline;
import com.windula.sparkapi.service.pipeline.Word2VecPipeline;
import com.windula.sparkapi.service.transformer.util.GenrePrediction;
import com.windula.sparkapi.service.transformer.util.Similarity;
import org.apache.spark.ml.tuning.CrossValidatorModel;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class SparkService {

    @Resource(name = "${lyrics.pipeline}")
    private LyricsPipeline lyricsPipeline;


    public Map<String, Object> trainModel() {
//        RandomForestPipeline lyricsPipeline = new RandomForestPipeline();
        CrossValidatorModel model = lyricsPipeline.classify();
        return lyricsPipeline.getModelStatistics(model);

    }

    public GenrePrediction predictGenre(final String unknownLyrics) {
        return lyricsPipeline.predict(unknownLyrics);
    }

    public Map<String, Object> trainWord2Vec(){
        Word2VecPipeline pipeline = new Word2VecPipeline();
        return pipeline.train();
    }

    public List<Similarity> predictWord2Vec(final String unknownLyrics){
        Word2VecPipeline pipeline = new Word2VecPipeline();
        return pipeline.calculateSimilarity(unknownLyrics);
    }
}
