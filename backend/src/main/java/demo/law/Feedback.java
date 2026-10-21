package demo.law;
import jakarta.persistence.*;
@Entity
public class Feedback {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    public String question;
    public Integer rating;
    @Column(length=2000) public String comment;
    protected Feedback() {}
    Feedback(String question,Integer rating,String comment){this.question=question;this.rating=rating;this.comment=comment;}
}
