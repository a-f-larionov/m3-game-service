package m3.gameplay.services;

public interface StuffService {
    void giveAHummer(Long userId, Long count);

    void giveALightning(Long userId, Long count);

    void giveAShuffle(Long userId, Long count);

    void giveAGold(Long userId, Long count);
}
