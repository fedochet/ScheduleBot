package org.anstreth.ruzapi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Teacher {
    @JsonProperty("full_name")
    private String fullName;
}
