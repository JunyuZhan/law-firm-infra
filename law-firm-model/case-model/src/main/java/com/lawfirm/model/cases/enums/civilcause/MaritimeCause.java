package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 海事海商纠纷案由
 */
public class MaritimeCause {

    /**
     * 海事海商纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        MARITIME("海事海商纠纷", "19");

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
     * 海事海商纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 193.船舶碰撞损害责任纠纷
        SHIP_COLLISION("船舶碰撞损害责任纠纷", "1193"),
        
        // 194.船舶触碰损害责任纠纷
        SHIP_CONTACT("船舶触碰损害责任纠纷", "1194"),
        
        // 195.船舶损坏空中设施、水下设施损害责任纠纷
        FACILITY_DAMAGE("船舶损坏空中设施、水下设施损害责任纠纷", "1195"),
        
        // 196.船舶污染损害责任纠纷
        SHIP_POLLUTION("船舶污染损害责任纠纷", "1196"),
        
        // 197.海上、通海水域污染损害责任纠纷
        MARITIME_POLLUTION("海上、通海水域污染损害责任纠纷", "1197"),
        
        // 198.海上、通海水域养殖损害责任纠纷
        MARITIME_AQUACULTURE("海上、通海水域养殖损害责任纠纷", "1198"),
        
        // 199.海上、通海水域财产损害责任纠纷
        MARITIME_PROPERTY("海上、通海水域财产损害责任纠纷", "1199"),
        
        // 200.海上、通海水域人身损害责任纠纷
        MARITIME_PERSONAL_INJURY("海上、通海水域人身损害责任纠纷", "1200"),
        
        // 201.非法留置船舶、船载货物、船用燃油、船用物料损害责任纠纷
        ILLEGAL_DETENTION("非法留置船舶、船载货物、船用燃油、船用物料损害责任纠纷", "1201"),
        
        // 202.海上、通海水域货物运输合同纠纷
        MARITIME_CARGO("海上、通海水域货物运输合同纠纷", "1202"),
        
        // 203.海上、通海水域旅客运输合同纠纷
        MARITIME_PASSENGER("海上、通海水域旅客运输合同纠纷", "1203"),
        
        // 204.海上、通海水域行李运输合同纠纷
        MARITIME_LUGGAGE("海上、通海水域行李运输合同纠纷", "1204"),
        
        // 205.船舶经营管理合同纠纷
        SHIP_MANAGEMENT("船舶经营管理合同纠纷", "1205"),
        
        // 206.船舶买卖合同纠纷
        SHIP_SALE("船舶买卖合同纠纷", "1206"),
        
        // 207.船舶建造合同纠纷
        SHIP_CONSTRUCTION("船舶建造合同纠纷", "1207"),
        
        // 208.船舶修理合同纠纷
        SHIP_REPAIR("船舶修理合同纠纷", "1208"),
        
        // 209.船舶改建合同纠纷
        SHIP_RENOVATION("船舶改建合同纠纷", "1209"),
        
        // 210.船舶拆解合同纠纷
        SHIP_DISMANTLING("船舶拆解合同纠纷", "1210"),
        
        // 211.船舶抵押合同纠纷
        SHIP_MORTGAGE("船舶抵押合同纠纷", "1211"),
        
        // 212.航次租船合同纠纷
        VOYAGE_CHARTER("航次租船合同纠纷", "1212"),
        
        // 213.船舶租用合同纠纷
        SHIP_CHARTER("船舶租用合同纠纷", "1213"),
        
        // 214.船舶融资租赁合同纠纷
        SHIP_FINANCIAL_LEASE("船舶融资租赁合同纠纷", "1214"),
        
        // 215.海上、通海水域运输船舶承包合同纠纷
        MARITIME_SHIP_CONTRACT("海上、通海水域运输船舶承包合同纠纷", "1215"),
        
        // 216.渔船承包合同纠纷
        FISHING_BOAT_CONTRACT("渔船承包合同纠纷", "1216"),
        
        // 217.船舶属具租赁合同纠纷
        SHIP_EQUIPMENT_LEASE("船舶属具租赁合同纠纷", "1217"),
        
        // 218.船舶属具保管合同纠纷
        SHIP_EQUIPMENT_STORAGE("船舶属具保管合同纠纷", "1218"),
        
        // 219.海运集装箱租赁合同纠纷
        CONTAINER_LEASE("海运集装箱租赁合同纠纷", "1219"),
        
        // 220.海运集装箱保管合同纠纷
        CONTAINER_STORAGE("海运集装箱保管合同纠纷", "1220"),
        
        // 221.港口货物保管合同纠纷
        PORT_CARGO_STORAGE("港口货物保管合同纠纷", "1221"),
        
        // 222.船舶代理合同纠纷
        SHIP_AGENCY("船舶代理合同纠纷", "1222"),
        
        // 223.海上、通海水域货运代理合同纠纷
        MARITIME_FREIGHT_AGENCY("海上、通海水域货运代理合同纠纷", "1223"),
        
        // 224.理货合同纠纷
        TALLY("理货合同纠纷", "1224"),
        
        // 225.船舶物料和备品供应合同纠纷
        SHIP_SUPPLY("船舶物料和备品供应合同纠纷", "1225"),
        
        // 226.船员劳务合同纠纷
        CREW_LABOR("船员劳务合同纠纷", "1226"),
        
        // 227.海难救助合同纠纷
        MARITIME_SALVAGE("海难救助合同纠纷", "1227"),
        
        // 228.海上、通海水域打捞合同纠纷
        MARITIME_SALVAGE_CONTRACT("海上、通海水域打捞合同纠纷", "1228"),
        
        // 229.海上、通海水域拖航合同纠纷
        MARITIME_TOWAGE("海上、通海水域拖航合同纠纷", "1229"),
        
        // 230.海上、通海水域保险合同纠纷
        MARITIME_INSURANCE("海上、通海水域保险合同纠纷", "1230"),
        
        // 231.海上、通海水域保赔合同纠纷
        MARITIME_PI("海上、通海水域保赔合同纠纷", "1231"),
        
        // 232.海上、通海水域运输联营合同纠纷
        MARITIME_JOINT_VENTURE("海上、通海水域运输联营合同纠纷", "1232"),
        
        // 233.船舶营运借款合同纠纷
        SHIP_OPERATION_LOAN("船舶营运借款合同纠纷", "1233"),
        
        // 234.海事担保合同纠纷
        MARITIME_GUARANTEE("海事担保合同纠纷", "1234"),
        
        // 235.航道、港口疏浚合同纠纷
        CHANNEL_DREDGING("航道、港口疏浚合同纠纷", "1235"),
        
        // 236.船坞、码头建造合同纠纷
        DOCK_CONSTRUCTION("船坞、码头建造合同纠纷", "1236"),
        
        // 237.船舶检验合同纠纷
        SHIP_INSPECTION("船舶检验合同纠纷", "1237"),
        
        // 238.海事请求担保纠纷
        MARITIME_CLAIM_SECURITY("海事请求担保纠纷", "1238"),
        
        // 239.海上、通海水域运输重大责任事故责任纠纷
        MARITIME_ACCIDENT("海上、通海水域运输重大责任事故责任纠纷", "1239"),
        
        // 240.港口作业重大责任事故责任纠纷
        PORT_ACCIDENT("港口作业重大责任事故责任纠纷", "1240"),
        
        // 241.港口作业纠纷
        PORT_OPERATION("港口作业纠纷", "1241"),
        
        // 242.共同海损纠纷
        GENERAL_AVERAGE("共同海损纠纷", "1242"),
        
        // 243.海洋开发利用纠纷
        OCEAN_DEVELOPMENT("海洋开发利用纠纷", "1243"),
        
        // 244.船舶共有纠纷
        SHIP_CO_OWNERSHIP("船舶共有纠纷", "1244"),
        
        // 245.船舶权属纠纷
        SHIP_OWNERSHIP("船舶权属纠纷", "1245"),
        
        // 246.海运欺诈纠纷
        MARITIME_FRAUD("海运欺诈纠纷", "1246"),
        
        // 247.海事债权确权纠纷
        MARITIME_CLAIM_CONFIRMATION("海事债权确权纠纷", "1247");

        private final String description;
        private final String code;
        private final First parentCause = First.MARITIME;

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

    /**
     * 船舶租用合同纠纷三级案由
     */
    @Getter
    public enum ShipCharter implements BaseEnum<String> {
        
        // (1)定期租船合同纠纷
        TIME_CHARTER("定期租船合同纠纷", "121301"),
        
        // (2)光船租赁合同纠纷
        BAREBOAT_CHARTER("光船租赁合同纠纷", "121302");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SHIP_CHARTER;

        ShipCharter(String description, String code) {
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