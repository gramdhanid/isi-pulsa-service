package id.my.dansapps.pulsa.service.recharge;

import id.my.dansapps.pulsa.dto.RechargeRequest;
import id.my.dansapps.pulsa.dto.RechargeResponse;

public interface RechargeService {

    RechargeResponse requestRecharge(RechargeRequest rechargeRequest);

}
