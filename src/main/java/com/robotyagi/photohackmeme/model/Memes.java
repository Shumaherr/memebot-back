package com.robotyagi.photohackmeme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "MEMELIB")
public class Memes {

    @Id
    @JsonIgnore
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meme_seq")
    @SequenceGenerator(name = "meme_seq", sequenceName = "meme_seq", allocationSize = 1)
    private Long id;

    @Column(name = "ANGER")
    private Double anger;

    @Column(name = "CONTEMPT")
    private Double contempt;

    @Column(name = "DISGUST")
    private Double disgust;

    @Column(name = "FEAR")
    private Double fear;

    @Column(name = "HAPPINESS")
    private Double happiness;

    @Column(name = "NEUTRAL")
    private Double neutral;

    @Column(name = "SADNESS")
    private Double sadness;

    @Column(name = "SURPRISE")
    private Double surprise;

    @Column(name = "FILENAME")
    private String filename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAnger() {
        return anger;
    }

    public void setAnger(Double anger) {
        this.anger = anger;
    }

    public Double getContempt() {
        return contempt;
    }

    public void setContempt(Double contempt) {
        this.contempt = contempt;
    }

    public Double getDisgust() {
        return disgust;
    }

    public void setDisgust(Double disgust) {
        this.disgust = disgust;
    }

    public Double getFear() {
        return fear;
    }

    public void setFear(Double fear) {
        this.fear = fear;
    }

    public Double getHappiness() {
        return happiness;
    }

    public void setHappiness(Double happiness) {
        this.happiness = happiness;
    }

    public Double getNeutral() {
        return neutral;
    }

    public void setNeutral(Double neutral) {
        this.neutral = neutral;
    }

    public Double getSadness() {
        return sadness;
    }

    public void setSadness(Double sadness) {
        this.sadness = sadness;
    }

    public Double getSurprise() {
        return surprise;
    }

    public void setSurprise(Double surprise) {
        this.surprise = surprise;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
