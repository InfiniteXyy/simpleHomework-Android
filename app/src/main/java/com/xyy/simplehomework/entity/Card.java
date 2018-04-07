package com.xyy.simplehomework.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * An entity that contains customized domain of information
 */

@Entity
public class Card {
    public String title;
    @Id
    long id;

}
