package no.kristiania.backend.entity;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
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

    // TODO: column definition?
    @Column(columnDefinition = "text")
    private String text;

    @NotNull
    private Date dateCreated;

    // TODO: use this?
    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
    }

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

    public String getText() {
        return text;
    }

    public void setText(String reviewText) {
        this.text = reviewText;
    }

    // TODO: get better date format?
    public Date getDateCreated() {
        return dateCreated;
    }
    public String getSimpleDateFormat(){
        SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        return sd.format(getDateCreated());
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
