package no.kristiania.backend.entity;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Review implements Serializable {

    @EmbeddedId
    private ReviewId reviewId;

    @Range(min = 1, max = 5)
    private int rating;

    //TODO: column definition?
    @Column(columnDefinition = "text")
    private String reviewText;

    @UpdateTimestamp
    private Date dateCreated;

    public ReviewId getReviewId() {
        return reviewId;
    }

    public void setReviewId(ReviewId reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    // TODO: get better date format?
    public Date getDateCreated() {
        return dateCreated;
    }

    //any way to just store unixtime and convert like in javascript?
    public String getSimpleDateFormat(){
        String pattern = "dd/mm/yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(getDateCreated());
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
