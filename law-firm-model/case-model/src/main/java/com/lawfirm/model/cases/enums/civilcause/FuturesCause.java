package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 期货交易纠纷案由
 */
public class FuturesCause {

    /**
     * 期货交易纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        FUTURES("期货交易纠纷", "25");

        private final String description;
        private final String code;

        First(String description, String code) {
            this.description = description;
            this.code = code;
        }

        @Override
        public String getValue() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return this.description;
        }
    }

    /**
     * 期货交易纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 319.期货经纪合同纠纷
        FUTURES_BROKERAGE("期货经纪合同纠纷", "1319"),
        
        // 320.期货透支交易纠纷
        FUTURES_OVERDRAFT("期货透支交易纠纷", "1320"),
        
        // 321.期货强行平仓纠纷
        FORCED_LIQUIDATION("期货强行平仓纠纷", "1321"),
        
        // 322.期货实物交割纠纷
        PHYSICAL_DELIVERY("期货实物交割纠纷", "1322"),
        
        // 323.期货保证合约纠纷
        FUTURES_GUARANTEE("期货保证合约纠纷", "1323"),
        
        // 324.期货交易代理合同纠纷
        FUTURES_AGENCY("期货交易代理合同纠纷", "1324"),
        
        // 325.侵占期货交易保证金纠纷
        MARGIN_MISAPPROPRIATION("侵占期货交易保证金纠纷", "1325"),
        
        // 326.期货欺诈责任纠纷
        FUTURES_FRAUD("期货欺诈责任纠纷", "1326"),
        
        // 327.操纵期货交易市场责任纠纷
        MARKET_MANIPULATION("操纵期货交易市场责任纠纷", "1327"),
        
        // 328.期货内幕交易责任纠纷
        INSIDER_TRADING("期货内幕交易责任纠纷", "1328"),
        
        // 329.期货虚假信息责任纠纷
        FALSE_INFORMATION("期货虚假信息责任纠纷", "1329");

        private final String description;
        private final String code;
        private final First parentCause = First.FUTURES;

        Second(String description, String code) {
            this.description = description;
            this.code = code;
        }

        @Override
        public String getValue() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return this.description;
        }
    }
} 