package com.example.sentimentanalysisservice.service.impl;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SentimentAnalysisService {
    public String analyze(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = pipeline.process(text);

        StringBuilder result = new StringBuilder();

        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int score = RNNCoreAnnotations.getPredictedClass(tree);

            String sentimentType = "";
            if(score <= 1) {
                sentimentType = "Çok Olumsuz";
            } else if(score == 2) {
                sentimentType = "Olumsuz";
            } else if(score == 3) {
                sentimentType = "Nötr";
            } else if(score == 4) {
                sentimentType = "Olumlu";
            } else if(score >= 5) {
                sentimentType = "Çok Olumlu";
            }

            result.append("Cümle: ").append(sentence.toString()).append(", Duygu: ").append(sentimentType).append("\n").append(" score: ").append(score);
        }

        return result.toString();
    }
}
