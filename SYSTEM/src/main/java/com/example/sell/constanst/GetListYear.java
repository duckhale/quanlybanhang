package com.example.sell.constanst;

import java.text.SimpleDateFormat;
import java.util.*;

public class GetListYear {
    public List<Integer> getYears(List<Date> objects) {
        List<Integer> years = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        objects.forEach(o -> {
            if (!years.contains(Integer.parseInt(format.format(o)))) {
                years.add(Integer.parseInt(format.format(o)));
            }
        });
        return years;
    }
}
