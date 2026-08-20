package com.huong.workingsystem.model.dto.BoardDTO;

public interface PhaseBottleneckProjection {
    Long getBoardListTypeId();
    String getBoardListTypeTitle();
    Long getTotalCardEntries();
    Long getTotalUniqueCards();
    Long getTotalDurationSeconds();
    Double getAverageDurationSeconds();
    Double getTimeShareRate();
}
