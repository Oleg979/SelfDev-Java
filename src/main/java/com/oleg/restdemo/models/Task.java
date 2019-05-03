package com.oleg.restdemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String tag;
    private String time;
    private LocalDate creationDate;
    private long timestamp;
    private boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private ApplicationUser user;

    public Task(String title, String tag, String time) {
        this.title = title;
        this.tag = tag;
        this.time = time;
        this.creationDate = LocalDate.now();
    }
}
