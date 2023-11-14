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
    public void giveAHummer(Long userId, Long quantity) {
        incrementStuff("hummerQty", userId, quantity);
        //incrementStuff();
        //incrementStuff(DataStuff.STUFF_HUMMER, userId, quantity);
        //                     let updateUserInfo = function () {
        //                         DataStuff.getByUserId(cntx.userId, function (data) {
        //                             CAPIStuff.gotStuff(cntx.userId, data);
        //                         });
        //                     };
        //
    }

    @Override
    public void giveALightning(Long userId, Long count) {

    }

    @Override
    public void giveAShuffle(Long userId, Long count) {

    }

    @Override
    public void giveAGold(Long userId, Long count) {

    }

    private void incrementStuff(String fieldName, Long userId, Long quantity) {

        userStuffRepository.incrementOne(fieldName, userId, quantity);
        //lock
        //userStuffRepository.incrementOne(fieldName, userId, count);
        //Statiscit. STUFF выдан, user stuff list
        //
//
//        LOCK.acquire(Keys.stuff(userId, fieldName), function (done) {
//            setTimeout(done, 5 * 60 * 1000);
//            DB.query("UPDATE " + tableName + "" +
//                    " SET `" + fieldName + "` = `" + fieldName + "` + " + parseInt(quantity) +
//                    " WHERE `userId` = " + parseInt(userId), function (data) {
//                DataStuff.getByUserId(userId, function (stuff) {
//                    Logs.log("vk_stuff tid:" + tid + " uid:"
//                                    + userId + " "
//                                    + fieldName + " +" + quantity +
//                                    " current:" + stuff[fieldName] + " OK",
//                            Logs.LEVEL_DEBUG, undefined, Logs.TYPE_VK_STUFF);
//                    if (callback) callback(true, stuff[fieldName]);
//                    done();
//                });
//            });
//        });
    }
}
