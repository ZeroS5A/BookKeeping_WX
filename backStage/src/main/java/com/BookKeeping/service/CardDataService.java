package com.BookKeeping.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface CardDataService {
    Integer insertCardData(String openid);

    Integer deleteCardData(String openid);
}
