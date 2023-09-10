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
                sentimentType = "Very Negative";
            } else if(score == 2) {
                sentimentType = "Negative";
            } else if(score == 3) {
                sentimentType = "Neutral";
            } else if(score == 4) {
                sentimentType = "Positive";
            } else if(score >= 5) {
                sentimentType = "Very Positive";
            }

            result.append("Sentence: ").append(sentence.toString()).append(", Sentiment: ").append(sentimentType).append("\n").append(" Score: ").append(score);
        }

        return result.toString();
    }
}
