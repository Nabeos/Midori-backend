package com.midorimart.managementsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Location_District")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class District {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private int id;
    @Column(name = "DistrictId")
    private String districtId;
    @Column(name = "Name")
    private String name;
    @Column(name = "ProvinceId")
    private String provinceId;
}
