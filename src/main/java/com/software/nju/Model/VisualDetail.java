package com.software.nju.Model;

import com.software.nju.Bean.Config;
import com.software.nju.Bean.Visual;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class VisualDetail {
    private Visual visual;
    private Config config;
}
