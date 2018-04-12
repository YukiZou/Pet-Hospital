package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PictureAllDTO  implements Serializable {
    private int caseId;
    private int stage;
}
