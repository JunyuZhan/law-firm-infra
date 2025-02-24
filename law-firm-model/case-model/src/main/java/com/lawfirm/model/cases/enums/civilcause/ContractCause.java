package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 合同纠纷案由
 */
public class ContractCause {

    /**
     * 十、合同纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        CONTRACT("合同纠纷", "10");

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
     * 合同纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 74.缔约过失责任纠纷
        CONTRACT_NEGOTIATION("缔约过失责任纠纷", "1074"),
        
        // 75.预约合同纠纷
        PRELIMINARY_CONTRACT("预约合同纠纷", "1075"),
        
        // 76.确认合同效力纠纷
        CONTRACT_VALIDITY("确认合同效力纠纷", "1076"),
        
        // 77.债权人代位权纠纷
        CREDITOR_SUBROGATION("债权人代位权纠纷", "1077"),
        
        // 78.债权人撤销权纠纷
        CREDITOR_REVOCATION("债权人撤销权纠纷", "1078"),
        
        // 79.债权转让合同纠纷
        CREDIT_ASSIGNMENT("债权转让合同纠纷", "1079"),
        
        // 80.债务转移合同纠纷
        DEBT_TRANSFER("债务转移合同纠纷", "1080"),
        
        // 81.债权债务概括转移合同纠纷
        DEBT_CREDIT_TRANSFER("债权债务概括转移合同纠纷", "1081"),
        
        // 82.债务加入纠纷
        DEBT_ADDITION("债务加入纠纷", "1082"),
        
        // 83.悬赏广告纠纷
        REWARD_ADVERTISEMENT("悬赏广告纠纷", "1083"),
        
        // 84.买卖合同纠纷
        SALE_CONTRACT("买卖合同纠纷", "1084"),
        
        // 85.拍卖合同纠纷
        AUCTION_CONTRACT("拍卖合同纠纷", "1085"),
        
        // 86.建设用地使用权合同纠纷
        LAND_USE_CONTRACT("建设用地使用权合同纠纷", "1086"),
        
        // 87.临时用地合同纠纷
        TEMPORARY_LAND("临时用地合同纠纷", "1087"),
        
        // 88.探矿权转让合同纠纷
        MINING_EXPLORATION_TRANSFER("探矿权转让合同纠纷", "1088"),
        
        // 89.采矿权转让合同纠纷
        MINING_RIGHT_TRANSFER("采矿权转让合同纠纷", "1089"),
        
        // 90.房地产开发经营合同纠纷
        REAL_ESTATE_DEVELOPMENT("房地产开发经营合同纠纷", "1090"),
        
        // 91.房屋买卖合同纠纷
        HOUSE_SALE_CONTRACT("房屋买卖合同纠纷", "1091"),
        
        // 92.民事主体间房屋拆迁补偿合同纠纷
        HOUSE_DEMOLITION("民事主体间房屋拆迁补偿合同纠纷", "1092"),
        
        // 93.供用电合同纠纷
        ELECTRICITY_SUPPLY("供用电合同纠纷", "1093"),
        
        // 94.供用水合同纠纷
        WATER_SUPPLY("供用水合同纠纷", "1094"),
        
        // 95.供用气合同纠纷
        GAS_SUPPLY("供用气合同纠纷", "1095"),
        
        // 96.供用热力合同纠纷
        HEAT_SUPPLY("供用热力合同纠纷", "1096"),
        
        // 97.排污权交易纠纷
        POLLUTION_RIGHT_TRADE("排污权交易纠纷", "1097"),
        
        // 98.用能权交易纠纷
        ENERGY_RIGHT_TRADE("用能权交易纠纷", "1098"),
        
        // 99.用水权交易纠纷
        WATER_RIGHT_TRADE("用水权交易纠纷", "1099"),
        
        // 100.碳排放权交易纠纷
        CARBON_EMISSION_TRADE("碳排放权交易纠纷", "1100"),
        
        // 101.碳汇交易纠纷
        CARBON_SINK_TRADE("碳汇交易纠纷", "1101"),
        
        // 102.赠与合同纠纷
        GIFT_CONTRACT("赠与合同纠纷", "1102"),
        
        // 103.借款合同纠纷
        LOAN_CONTRACT("借款合同纠纷", "1103"),
        
        // 104.保证合同纠纷
        GUARANTEE_CONTRACT("保证合同纠纷", "1104"),
        
        // 105.抵押合同纠纷
        MORTGAGE_CONTRACT("抵押合同纠纷", "1105"),
        
        // 106.质押合同纠纷
        PLEDGE_CONTRACT("质押合同纠纷", "1106"),
        
        // 107.定金合同纠纷
        DEPOSIT_CONTRACT("定金合同纠纷", "1107"),
        
        // 108.进出口押汇纠纷
        IMPORT_EXPORT_LOAN("进出口押汇纠纷", "1108"),
        
        // 109.储蓄存款合同纠纷
        SAVINGS_DEPOSIT("储蓄存款合同纠纷", "1109"),
        
        // 110.银行卡纠纷
        BANK_CARD("银行卡纠纷", "1110"),
        
        // 111.租赁合同纠纷
        LEASE_CONTRACT("租赁合同纠纷", "1111"),
        
        // 112.融资租赁合同纠纷
        FINANCIAL_LEASE("融资租赁合同纠纷", "1112"),
        
        // 113.保理合同纠纷
        FACTORING_CONTRACT("保理合同纠纷", "1113"),
        
        // 114.承揽合同纠纷
        CONTRACTOR_CONTRACT("承揽合同纠纷", "1114"),
        
        // 115.建设工程合同纠纷
        CONSTRUCTION_CONTRACT("建设工程合同纠纷", "1115"),
        
        // 116.运输合同纠纷
        TRANSPORT_CONTRACT("运输合同纠纷", "1116"),
        
        // 117.保管合同纠纷
        STORAGE_CONTRACT("保管合同纠纷", "1117"),
        
        // 118.仓储合同纠纷
        WAREHOUSE_CONTRACT("仓储合同纠纷", "1118"),
        
        // 119.委托合同纠纷
        COMMISSION_CONTRACT("委托合同纠纷", "1119"),
        
        // 120.委托理财合同纠纷
        FINANCIAL_MANAGEMENT("委托理财合同纠纷", "1120"),
        
        // 121.物业服务合同纠纷
        PROPERTY_SERVICE("物业服务合同纠纷", "1121"),
        
        // 122.行纪合同纠纷
        AGENCY_CONTRACT("行纪合同纠纷", "1122"),
        
        // 123.中介合同纠纷
        INTERMEDIARY_CONTRACT("中介合同纠纷", "1123"),
        
        // 124.补偿贸易纠纷
        COMPENSATION_TRADE("补偿贸易纠纷", "1124"),
        
        // 125.借用合同纠纷
        LENDING_CONTRACT("借用合同纠纷", "1125"),
        
        // 126.典当纠纷
        PAWN_CONTRACT("典当纠纷", "1126"),
        
        // 127.合伙合同纠纷
        PARTNERSHIP_CONTRACT("合伙合同纠纷", "1127"),
        
        // 128.种植、养殖回收合同纠纷
        PLANTING_BREEDING("种植、养殖回收合同纠纷", "1128"),
        
        // 129.彩票、奖券纠纷
        LOTTERY_TICKET("彩票、奖券纠纷", "1129"),
        
        // 130.中外合作勘探开发自然资源合同纠纷
        RESOURCE_EXPLORATION("中外合作勘探开发自然资源合同纠纷", "1130"),
        
        // 131.农业承包合同纠纷
        AGRICULTURE_CONTRACT("农业承包合同纠纷", "1131"),
        
        // 132.林业承包合同纠纷
        FORESTRY_CONTRACT("林业承包合同纠纷", "1132"),
        
        // 133.渔业承包合同纠纷
        FISHERY_CONTRACT("渔业承包合同纠纷", "1133"),
        
        // 134.牧业承包合同纠纷
        ANIMAL_HUSBANDRY("牧业承包合同纠纷", "1134"),
        
        // 135.土地承包经营权合同纠纷
        LAND_CONTRACT_RIGHT("土地承包经营权合同纠纷", "1135"),
        
        // 136.居住权合同纠纷
        RESIDENCE_RIGHT("居住权合同纠纷", "1136"),
        
        // 137.服务合同纠纷
        SERVICE_CONTRACT("服务合同纠纷", "1137"),
        
        // 138.演出合同纠纷
        PERFORMANCE_CONTRACT("演出合同纠纷", "1138"),
        
        // 139.劳务合同纠纷
        LABOR_SERVICE_CONTRACT("劳务合同纠纷", "1139"),
        
        // 140.离退休人员返聘合同纠纷
        RETIREE_REHIRE("离退休人员返聘合同纠纷", "1140"),
        
        // 141.广告合同纠纷
        ADVERTISING_CONTRACT("广告合同纠纷", "1141"),
        
        // 142.展览合同纠纷
        EXHIBITION_CONTRACT("展览合同纠纷", "1142"),
        
        // 143.追偿权纠纷
        RECOURSE_RIGHT("追偿权纠纷", "1143");

        private final String description;
        private final String code;
        private final First parentCause = First.CONTRACT;

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
     * 确认合同效力纠纷三级案由
     */
    @Getter
    public enum ContractValidity implements BaseEnum<String> {
        
        // (1)确认合同有效纠纷
        VALID("确认合同有效纠纷", "107601"),
        
        // (2)确认合同无效纠纷
        INVALID("确认合同无效纠纷", "107602");

        private final String description;
        private final String code;
        private final Second parentCause = Second.CONTRACT_VALIDITY;

        ContractValidity(String description, String code) {
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
     * 买卖合同纠纷三级案由
     */
    @Getter
    public enum SaleContract implements BaseEnum<String> {
        
        // (1)分期付款买卖合同纠纷
        INSTALLMENT("分期付款买卖合同纠纷", "108401"),
        
        // (2)凭样品买卖合同纠纷
        SAMPLE("凭样品买卖合同纠纷", "108402"),
        
        // (3)试用买卖合同纠纷
        TRIAL("试用买卖合同纠纷", "108403"),
        
        // (4)所有权保留买卖合同纠纷
        OWNERSHIP_RETENTION("所有权保留买卖合同纠纷", "108404"),
        
        // (5)招标投标买卖合同纠纷
        BIDDING("招标投标买卖合同纠纷", "108405"),
        
        // (6)互易纠纷
        EXCHANGE("互易纠纷", "108406"),
        
        // (7)国际货物买卖合同纠纷
        INTERNATIONAL_GOODS("国际货物买卖合同纠纷", "108407"),
        
        // (8)信息网络买卖合同纠纷
        ONLINE_SALE("信息网络买卖合同纠纷", "108408");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SALE_CONTRACT;

        SaleContract(String description, String code) {
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
     * 房地产开发经营合同纠纷三级案由
     */
    @Getter
    public enum RealEstateDevelopment implements BaseEnum<String> {
        
        // (1)委托代建合同纠纷
        ENTRUSTED_CONSTRUCTION("委托代建合同纠纷", "109001"),
        
        // (2)合资、合作开发房地产合同纠纷
        JOINT_DEVELOPMENT("合资、合作开发房地产合同纠纷", "109002"),
        
        // (3)项目转让合同纠纷
        PROJECT_TRANSFER("项目转让合同纠纷", "109003");

        private final String description;
        private final String code;
        private final Second parentCause = Second.REAL_ESTATE_DEVELOPMENT;

        RealEstateDevelopment(String description, String code) {
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
     * 房屋买卖合同纠纷三级案由
     */
    @Getter
    public enum HouseSaleContract implements BaseEnum<String> {
        
        // (1)商品房预约合同纠纷
        COMMERCIAL_HOUSE_BOOKING("商品房预约合同纠纷", "109101"),
        
        // (2)商品房预售合同纠纷
        COMMERCIAL_HOUSE_PRESALE("商品房预售合同纠纷", "109102"),
        
        // (3)商品房销售合同纠纷
        COMMERCIAL_HOUSE_SALE("商品房销售合同纠纷", "109103"),
        
        // (4)商品房委托代理销售合同纠纷
        COMMERCIAL_HOUSE_AGENCY("商品房委托代理销售合同纠纷", "109104"),
        
        // (5)经济适用房转让合同纠纷
        AFFORDABLE_HOUSE_TRANSFER("经济适用房转让合同纠纷", "109105"),
        
        // (6)农村房屋买卖合同纠纷
        RURAL_HOUSE_SALE("农村房屋买卖合同纠纷", "109106");

        private final String description;
        private final String code;
        private final Second parentCause = Second.HOUSE_SALE_CONTRACT;

        HouseSaleContract(String description, String code) {
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
     * 赠与合同纠纷三级案由
     */
    @Getter
    public enum GiftContract implements BaseEnum<String> {
        
        // (1)公益事业捐赠合同纠纷
        PUBLIC_WELFARE_DONATION("公益事业捐赠合同纠纷", "110201"),
        
        // (2)附义务赠与合同纠纷
        CONDITIONAL_GIFT("附义务赠与合同纠纷", "110202");

        private final String description;
        private final String code;
        private final Second parentCause = Second.GIFT_CONTRACT;

        GiftContract(String description, String code) {
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
     * 借款合同纠纷三级案由
     */
    @Getter
    public enum LoanContract implements BaseEnum<String> {
        
        // (1)金融借款合同纠纷
        FINANCIAL_LOAN("金融借款合同纠纷", "110301"),
        
        // (2)同业拆借纠纷
        INTERBANK_LOAN("同业拆借纠纷", "110302"),
        
        // (3)民间借贷纠纷
        PRIVATE_LENDING("民间借贷纠纷", "110303"),
        
        // (4)小额借款合同纠纷
        SMALL_LOAN("小额借款合同纠纷", "110304"),
        
        // (5)金融不良债权转让合同纠纷
        BAD_DEBT_TRANSFER("金融不良债权转让合同纠纷", "110305"),
        
        // (6)金融不良债权追偿纠纷
        BAD_DEBT_RECOVERY("金融不良债权追偿纠纷", "110306");

        private final String description;
        private final String code;
        private final Second parentCause = Second.LOAN_CONTRACT;

        LoanContract(String description, String code) {
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
     * 银行卡纠纷三级案由
     */
    @Getter
    public enum BankCard implements BaseEnum<String> {
        
        // (1)借记卡纠纷
        DEBIT_CARD("借记卡纠纷", "111001"),
        
        // (2)信用卡纠纷
        CREDIT_CARD("信用卡纠纷", "111002");

        private final String description;
        private final String code;
        private final Second parentCause = Second.BANK_CARD;

        BankCard(String description, String code) {
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
     * 租赁合同纠纷三级案由
     */
    @Getter
    public enum LeaseContract implements BaseEnum<String> {
        
        // (1)土地租赁合同纠纷
        LAND_LEASE("土地租赁合同纠纷", "111101"),
        
        // (2)房屋租赁合同纠纷
        HOUSE_LEASE("房屋租赁合同纠纷", "111102"),
        
        // (3)车辆租赁合同纠纷
        VEHICLE_LEASE("车辆租赁合同纠纷", "111103"),
        
        // (4)建筑设备租赁合同纠纷
        CONSTRUCTION_EQUIPMENT_LEASE("建筑设备租赁合同纠纷", "111104");

        private final String description;
        private final String code;
        private final Second parentCause = Second.LEASE_CONTRACT;

        LeaseContract(String description, String code) {
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
     * 承揽合同纠纷三级案由
     */
    @Getter
    public enum ContractorContract implements BaseEnum<String> {
        
        // (1)加工合同纠纷
        PROCESSING("加工合同纠纷", "111401"),
        
        // (2)定作合同纠纷
        CUSTOM_MADE("定作合同纠纷", "111402"),
        
        // (3)修理合同纠纷
        REPAIR("修理合同纠纷", "111403"),
        
        // (4)复制合同纠纷
        REPRODUCTION("复制合同纠纷", "111404"),
        
        // (5)测试合同纠纷
        TESTING("测试合同纠纷", "111405"),
        
        // (6)检验合同纠纷
        INSPECTION("检验合同纠纷", "111406"),
        
        // (7)铁路机车、车辆建造合同纠纷
        RAILWAY_VEHICLE("铁路机车、车辆建造合同纠纷", "111407");

        private final String description;
        private final String code;
        private final Second parentCause = Second.CONTRACTOR_CONTRACT;

        ContractorContract(String description, String code) {
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
     * 建设工程合同纠纷三级案由
     */
    @Getter
    public enum ConstructionContract implements BaseEnum<String> {
        
        // (1)建设工程勘察合同纠纷
        SURVEY("建设工程勘察合同纠纷", "111501"),
        
        // (2)建设工程设计合同纠纷
        DESIGN("建设工程设计合同纠纷", "111502"),
        
        // (3)建设工程施工合同纠纷
        CONSTRUCTION("建设工程施工合同纠纷", "111503"),
        
        // (4)建设工程价款优先受偿权纠纷
        PRIORITY_COMPENSATION("建设工程价款优先受偿权纠纷", "111504"),
        
        // (5)建设工程分包合同纠纷
        SUBCONTRACT("建设工程分包合同纠纷", "111505"),
        
        // (6)建设工程监理合同纠纷
        SUPERVISION("建设工程监理合同纠纷", "111506"),
        
        // (7)装饰装修合同纠纷
        DECORATION("装饰装修合同纠纷", "111507"),
        
        // (8)铁路修建合同纠纷
        RAILWAY_CONSTRUCTION("铁路修建合同纠纷", "111508"),
        
        // (9)农村建房施工合同纠纷
        RURAL_CONSTRUCTION("农村建房施工合同纠纷", "111509");

        private final String description;
        private final String code;
        private final Second parentCause = Second.CONSTRUCTION_CONTRACT;

        ConstructionContract(String description, String code) {
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
     * 运输合同纠纷三级案由
     */
    @Getter
    public enum TransportContract implements BaseEnum<String> {
        
        // (1)公路旅客运输合同纠纷
        ROAD_PASSENGER("公路旅客运输合同纠纷", "111601"),
        
        // (2)公路货物运输合同纠纷
        ROAD_CARGO("公路货物运输合同纠纷", "111602"),
        
        // (3)水路旅客运输合同纠纷
        WATER_PASSENGER("水路旅客运输合同纠纷", "111603"),
        
        // (4)水路货物运输合同纠纷
        WATER_CARGO("水路货物运输合同纠纷", "111604"),
        
        // (5)航空旅客运输合同纠纷
        AIR_PASSENGER("航空旅客运输合同纠纷", "111605"),
        
        // (6)航空货物运输合同纠纷
        AIR_CARGO("航空货物运输合同纠纷", "111606"),
        
        // (7)出租汽车运输合同纠纷
        TAXI("出租汽车运输合同纠纷", "111607"),
        
        // (8)管道运输合同纠纷
        PIPELINE("管道运输合同纠纷", "111608"),
        
        // (9)城市公交运输合同纠纷
        CITY_BUS("城市公交运输合同纠纷", "111609"),
        
        // (10)联合运输合同纠纷
        JOINT_TRANSPORT("联合运输合同纠纷", "111610"),
        
        // (11)多式联运合同纠纷
        MULTIMODAL_TRANSPORT("多式联运合同纠纷", "111611"),
        
        // (12)铁路货物运输合同纠纷
        RAILWAY_CARGO("铁路货物运输合同纠纷", "111612"),
        
        // (13)铁路旅客运输合同纠纷
        RAILWAY_PASSENGER("铁路旅客运输合同纠纷", "111613"),
        
        // (14)铁路行李运输合同纠纷
        RAILWAY_LUGGAGE("铁路行李运输合同纠纷", "111614"),
        
        // (15)铁路包裹运输合同纠纷
        RAILWAY_PACKAGE("铁路包裹运输合同纠纷", "111615"),
        
        // (16)国际铁路联运合同纠纷
        INTERNATIONAL_RAILWAY("国际铁路联运合同纠纷", "111616");

        private final String description;
        private final String code;
        private final Second parentCause = Second.TRANSPORT_CONTRACT;

        TransportContract(String description, String code) {
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
     * 委托合同纠纷三级案由
     */
    @Getter
    public enum CommissionContract implements BaseEnum<String> {
        
        // (1)进出口代理合同纠纷
        IMPORT_EXPORT_AGENCY("进出口代理合同纠纷", "111901"),
        
        // (2)货运代理合同纠纷
        FREIGHT_AGENCY("货运代理合同纠纷", "111902"),
        
        // (3)民用航空运输销售代理合同纠纷
        CIVIL_AVIATION_AGENCY("民用航空运输销售代理合同纠纷", "111903"),
        
        // (4)诉讼、仲裁、人民调解代理合同纠纷
        LEGAL_AGENCY("诉讼、仲裁、人民调解代理合同纠纷", "111904"),
        
        // (5)销售代理合同纠纷
        SALES_AGENCY("销售代理合同纠纷", "111905");

        private final String description;
        private final String code;
        private final Second parentCause = Second.COMMISSION_CONTRACT;

        CommissionContract(String description, String code) {
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
     * 委托理财合同纠纷三级案由
     */
    @Getter
    public enum FinancialManagement implements BaseEnum<String> {
        
        // (1)金融委托理财合同纠纷
        FINANCIAL_TRUST("金融委托理财合同纠纷", "112001"),
        
        // (2)民间委托理财合同纠纷
        PRIVATE_TRUST("民间委托理财合同纠纷", "112002");

        private final String description;
        private final String code;
        private final Second parentCause = Second.FINANCIAL_MANAGEMENT;

        FinancialManagement(String description, String code) {
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
     * 服务合同纠纷三级案由
     */
    @Getter
    public enum ServiceContract implements BaseEnum<String> {
        
        // (1)电信服务合同纠纷
        TELECOM("电信服务合同纠纷", "113701"),
        
        // (2)邮政服务合同纠纷
        POSTAL("邮政服务合同纠纷", "113702"),
        
        // (3)快递服务合同纠纷
        EXPRESS("快递服务合同纠纷", "113703"),
        
        // (4)医疗服务合同纠纷
        MEDICAL("医疗服务合同纠纷", "113704"),
        
        // (5)法律服务合同纠纷
        LEGAL("法律服务合同纠纷", "113705"),
        
        // (6)旅游合同纠纷
        TOURISM("旅游合同纠纷", "113706"),
        
        // (7)房地产咨询合同纠纷
        REAL_ESTATE_CONSULTING("房地产咨询合同纠纷", "113707"),
        
        // (8)房地产价格评估合同纠纷
        REAL_ESTATE_APPRAISAL("房地产价格评估合同纠纷", "113708"),
        
        // (9)旅店服务合同纠纷
        HOTEL("旅店服务合同纠纷", "113709"),
        
        // (10)财会服务合同纠纷
        ACCOUNTING("财会服务合同纠纷", "113710"),
        
        // (11)餐饮服务合同纠纷
        CATERING("餐饮服务合同纠纷", "113711"),
        
        // (12)娱乐服务合同纠纷
        ENTERTAINMENT("娱乐服务合同纠纷", "113712"),
        
        // (13)有线电视服务合同纠纷
        CABLE_TV("有线电视服务合同纠纷", "113713"),
        
        // (14)网络服务合同纠纷
        INTERNET("网络服务合同纠纷", "113714"),
        
        // (15)教育培训合同纠纷
        EDUCATION("教育培训合同纠纷", "113715"),
        
        // (16)家政服务合同纠纷
        HOUSEKEEPING("家政服务合同纠纷", "113716"),
        
        // (17)庆典服务合同纠纷
        CELEBRATION("庆典服务合同纠纷", "113717"),
        
        // (18)殡葬服务合同纠纷
        FUNERAL("殡葬服务合同纠纷", "113718"),
        
        // (19)农业技术服务合同纠纷
        AGRICULTURAL_TECH("农业技术服务合同纠纷", "113719"),
        
        // (20)农机作业服务合同纠纷
        AGRICULTURAL_MACHINERY("农机作业服务合同纠纷", "113720"),
        
        // (21)保安服务合同纠纷
        SECURITY("保安服务合同纠纷", "113721"),
        
        // (22)银行结算合同纠纷
        BANK_SETTLEMENT("银行结算合同纠纷", "113722");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SERVICE_CONTRACT;

        ServiceContract(String description, String code) {
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
     * 土地承包经营权合同纠纷三级案由
     */
    @Getter
    public enum LandContractRight implements BaseEnum<String> {
        
        // (1)土地承包经营权转让合同纠纷
        TRANSFER("土地承包经营权转让合同纠纷", "113501"),
        
        // (2)土地承包经营权互换合同纠纷
        EXCHANGE("土地承包经营权互换合同纠纷", "113502"),
        
        // (3)土地经营权入股合同纠纷
        STOCK_CONTRIBUTION("土地经营权入股合同纠纷", "113503"),
        
        // (4)土地经营权抵押合同纠纷
        MORTGAGE("土地经营权抵押合同纠纷", "113504"),
        
        // (5)土地经营权出租合同纠纷
        LEASE("土地经营权出租合同纠纷", "113505");

        private final String description;
        private final String code;
        private final Second parentCause = Second.LAND_CONTRACT_RIGHT;

        LandContractRight(String description, String code) {
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
     * 建设用地使用权合同纠纷三级案由
     */
    @Getter
    public enum LandUseContract implements BaseEnum<String> {
        
        // (1)建设用地使用权出让合同纠纷
        TRANSFER("建设用地使用权出让合同纠纷", "108601"),
        
        // (2)建设用地使用权转让合同纠纷
        ASSIGNMENT("建设用地使用权转让合同纠纷", "108602");

        private final String description;
        private final String code;
        private final Second parentCause = Second.LAND_USE_CONTRACT;

        LandUseContract(String description, String code) {
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
     * 保证合同纠纷三级案由
     */
    @Getter
    public enum GuaranteeContract implements BaseEnum<String> {
        
        // (1)一般保证合同纠纷
        GENERAL_GUARANTEE("一般保证合同纠纷", "110401"),
        
        // (2)连带责任保证合同纠纷
        JOINT_LIABILITY("连带责任保证合同纠纷", "110402"),
        
        // (3)反担保合同纠纷
        COUNTER_GUARANTEE("反担保合同纠纷", "110403");

        private final String description;
        private final String code;
        private final Second parentCause = Second.GUARANTEE_CONTRACT;

        GuaranteeContract(String description, String code) {
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
     * 抵押合同纠纷三级案由
     */
    @Getter
    public enum MortgageContract implements BaseEnum<String> {
        
        // (1)动产抵押合同纠纷
        MOVABLE_PROPERTY("动产抵押合同纠纷", "110501"),
        
        // (2)不动产抵押合同纠纷
        REAL_ESTATE("不动产抵押合同纠纷", "110502"),
        
        // (3)最高额抵押合同纠纷
        MAXIMUM_AMOUNT("最高额抵押合同纠纷", "110503");

        private final String description;
        private final String code;
        private final Second parentCause = Second.MORTGAGE_CONTRACT;

        MortgageContract(String description, String code) {
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
     * 质押合同纠纷三级案由
     */
    @Getter
    public enum PledgeContract implements BaseEnum<String> {
        
        // (1)动产质押合同纠纷
        MOVABLE_PROPERTY("动产质押合同纠纷", "110601"),
        
        // (2)权利质押合同纠纷
        RIGHTS_PLEDGE("权利质押合同纠纷", "110602"),
        
        // (3)最高额质押合同纠纷
        MAXIMUM_AMOUNT("最高额质押合同纠纷", "110603");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PLEDGE_CONTRACT;

        PledgeContract(String description, String code) {
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
     * 保管合同纠纷三级案由
     */
    @Getter
    public enum StorageContract implements BaseEnum<String> {
        
        // (1)人身保管合同纠纷
        PERSONAL_STORAGE("人身保管合同纠纷", "111701"),
        
        // (2)财产保管合同纠纷
        PROPERTY_STORAGE("财产保管合同纠纷", "111702"),
        
        // (3)酒店保管合同纠纷
        HOTEL_STORAGE("酒店保管合同纠纷", "111703"),
        
        // (4)储藏保管合同纠纷
        DEPOSIT_STORAGE("储藏保管合同纠纷", "111704");

        private final String description;
        private final String code;
        private final Second parentCause = Second.STORAGE_CONTRACT;

        StorageContract(String description, String code) {
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
     * 仓储合同纠纷三级案由
     */
    @Getter
    public enum WarehouseContract implements BaseEnum<String> {
        
        // (1)普通仓储合同纠纷
        GENERAL_STORAGE("普通仓储合同纠纷", "111801"),
        
        // (2)仓单质押合同纠纷
        WAREHOUSE_RECEIPT_PLEDGE("仓单质押合同纠纷", "111802"),
        
        // (3)保税仓储合同纠纷
        BONDED_STORAGE("保税仓储合同纠纷", "111803");

        private final String description;
        private final String code;
        private final Second parentCause = Second.WAREHOUSE_CONTRACT;

        WarehouseContract(String description, String code) {
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
     * 物业服务合同纠纷三级案由
     */
    @Getter
    public enum PropertyServiceContract implements BaseEnum<String> {
        
        // (1)住宅物业服务合同纠纷
        RESIDENTIAL("住宅物业服务合同纠纷", "112101"),
        
        // (2)非住宅物业服务合同纠纷
        NON_RESIDENTIAL("非住宅物业服务合同纠纷", "112102"),
        
        // (3)物业服务区域内车位车库租赁合同纠纷
        PARKING_LEASE("物业服务区域内车位车库租赁合同纠纷", "112103");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PROPERTY_SERVICE;

        PropertyServiceContract(String description, String code) {
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
     * 行纪合同纠纷三级案由
     */
    @Getter
    public enum AgencyContract implements BaseEnum<String> {
        
        // (1)进出口行纪合同纠纷
        IMPORT_EXPORT("进出口行纪合同纠纷", "112201"),
        
        // (2)国内行纪合同纠纷
        DOMESTIC("国内行纪合同纠纷", "112202");

        private final String description;
        private final String code;
        private final Second parentCause = Second.AGENCY_CONTRACT;

        AgencyContract(String description, String code) {
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
     * 中介合同纠纷三级案由
     */
    @Getter
    public enum IntermediaryContract implements BaseEnum<String> {
        
        // (1)居间合同纠纷
        BROKERAGE("居间合同纠纷", "112301"),
        
        // (2)经纪合同纠纷
        AGENCY("经纪合同纠纷", "112302"),
        
        // (3)行纪合同纠纷
        COMMISSION("行纪合同纠纷", "112303");

        private final String description;
        private final String code;
        private final Second parentCause = Second.INTERMEDIARY_CONTRACT;

        IntermediaryContract(String description, String code) {
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
     * 融资租赁合同纠纷三级案由
     */
    @Getter
    public enum FinancialLeaseContract implements BaseEnum<String> {
        
        // (1)融资租赁合同纠纷
        FINANCIAL_LEASE("融资租赁合同纠纷", "111201"),
        
        // (2)融资性售后回租合同纠纷
        SALE_LEASEBACK("融资性售后回租合同纠纷", "111202"),
        
        // (3)融资租赁保证合同纠纷
        GUARANTEE("融资租赁保证合同纠纷", "111203");

        private final String description;
        private final String code;
        private final Second parentCause = Second.FINANCIAL_LEASE;

        FinancialLeaseContract(String description, String code) {
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
     * 保理合同纠纷三级案由
     */
    @Getter
    public enum FactoringContract implements BaseEnum<String> {
        
        // (1)有追索权保理合同纠纷
        WITH_RECOURSE("有追索权保理合同纠纷", "111301"),
        
        // (2)无追索权保理合同纠纷
        WITHOUT_RECOURSE("无追索权保理合同纠纷", "111302"),
        
        // (3)保理融资合同纠纷
        FINANCING("保理融资合同纠纷", "111303");

        private final String description;
        private final String code;
        private final Second parentCause = Second.FACTORING_CONTRACT;

        FactoringContract(String description, String code) {
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
     * 合伙合同纠纷三级案由
     */
    @Getter
    public enum PartnershipContract implements BaseEnum<String> {
        
        // (1)普通合伙协议纠纷
        GENERAL_PARTNERSHIP("普通合伙协议纠纷", "112701"),
        
        // (2)特殊普通合伙协议纠纷
        SPECIAL_GENERAL_PARTNERSHIP("特殊普通合伙协议纠纷", "112702"),
        
        // (3)有限合伙协议纠纷
        LIMITED_PARTNERSHIP("有限合伙协议纠纷", "112703"),
        
        // (4)隐名合伙协议纠纷
        ANONYMOUS_PARTNERSHIP("隐名合伙协议纠纷", "112704");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PARTNERSHIP_CONTRACT;

        PartnershipContract(String description, String code) {
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