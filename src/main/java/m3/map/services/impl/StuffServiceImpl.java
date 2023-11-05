package m3.map.services.impl;

import m3.lib.repositories.UserStuffRepository;
import m3.map.services.StuffService;
import org.springframework.stereotype.Service;

@Service
public class StuffServiceImpl implements StuffService {

    private final UserStuffRepository userStuffRepository;

    public StuffServiceImpl(UserStuffRepository userStuffRepository) {
        this.userStuffRepository = userStuffRepository;
    }

    @Override
    public void giveAHummer(Long userId, int quantity) {
        //https://www.youtube.com/watch?v=RhTXyUERugQ&t=2027s
        //incrementStuff(DataStuff.STUFF_HUMMER, userId, quantity);
        //                     let updateUserInfo = function () {
        //                         DataStuff.getByUserId(cntx.userId, function (data) {
        //                             CAPIStuff.gotStuff(cntx.userId, data);
        //                         });
        //                     };
        //
    }

    @Override
    public void giveALightning(Long userId, int count) {

    }

    @Override
    public void giveAShuffle(Long userId, int count) {

    }

    @Override
    public void giveAGold(Long userId, int count) {

    }

    private void incrementStuff(String fieldName, Long userId, Long quanitity){
        //lock
        //userStuffRepository.incrementOne(fieldName, userId, count);
        //Statiscit. STUFF выдан, user stuff list
        //
    }
}
