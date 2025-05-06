                                                                                package ch.zli.m223.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EntryDto {
    public Long id;
    public LocalDateTime checkIn;
    public LocalDateTime checkOut;
    public Long categoryId;
    public List<Long> tagIds;
    public Long userId;
} 