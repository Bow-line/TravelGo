package app.TravelGo.Post;

import app.TravelGo.User.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "posts")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "posts")
    @SequenceGenerator(name = "my_entity_seq_gen", sequenceName = "MY_ENTITY_SEQ")
    @Column(name = "id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
    @Column(name = "user_id")
    private int user;

    private String status;
    private int likes;

    @Column(name = "created_at")
    private Long createdAt;


}
