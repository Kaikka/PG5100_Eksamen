package no.kristiania.backend.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    public String getSimpleDateFormat(){
        SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        return sd.format(getDateCreated());
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
