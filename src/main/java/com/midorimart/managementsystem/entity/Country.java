package com.midorimart.managementsystem.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {
    @Id
    private String code;
    private String name;
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Merchant> merchants;
    @Column(name = "image")
    private String image;
}
