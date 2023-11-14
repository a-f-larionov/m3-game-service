package m3.map.kafka.sender;

import m3.lib.enums.StatisticEnum;

public interface CommonSender {

    default void statistic(Long userId, StatisticEnum statisticEnum) {
        this.statistic(userId, statisticEnum, null, null);
    }

    default void statistic(Long userId, StatisticEnum statisticEnum, String paramA) {
        this.statistic(userId, statisticEnum, paramA, null);
    }

    void statistic(Long userId, StatisticEnum statisticEnum, String paramA, String paramB);
}
