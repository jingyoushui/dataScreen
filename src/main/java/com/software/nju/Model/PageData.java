package com.software.nju.Model;

import com.software.nju.Bean.Visual;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class PageData {
    private int total;
    private int size;
    private int current;
    private List<Object> orders;
    private boolean hitCount;
    private boolean searchCount;
    private int pages;
    private Object records;
}
