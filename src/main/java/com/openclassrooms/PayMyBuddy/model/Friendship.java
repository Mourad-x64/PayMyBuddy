package com.openclassrooms.PayMyBuddy.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "friendships")
public class Friendship {

    @Id
    @Column(name = "friendship_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "friendship_src_id")
    private User src;

    @ManyToOne
    @JoinColumn(name = "friendship_tgt_id")
    private User tgt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSrc() {
        return src;
    }

    public void setSrc(User src) {
        this.src = src;
    }

    public User getTgt() {
        return tgt;
    }

    public void setTgt(User tgt) {
        this.tgt = tgt;
    }
}
