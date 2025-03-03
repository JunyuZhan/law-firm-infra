package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政裁决案由
 */
public class AdminAdjudication {

    /**
     * 行政裁决案由枚举
     */
    @Getter
    public enum Cause implements BaseEnum<String> {
        
        LAND_DISPUTE("土地权属争议行政裁决", "1801"),
        FOREST_DISPUTE("林木权属争议行政裁决", "1802"),
        GRASSLAND_DISPUTE("草原权属争议行政裁决", "1803"),
        MINERAL_DISPUTE("矿产资源权属争议行政裁决", "1804"),
        WATER_DISPUTE("水事权属争议行政裁决", "1805"),
        TRADEMARK_DISPUTE("商标争议行政裁决", "1806"),
        PATENT_DISPUTE("专利争议行政裁决", "1807"),
        COPYRIGHT_DISPUTE("著作权争议行政裁决", "1808"),
        LABOR_DISPUTE("劳动争议行政裁决", "1809"),
        PRICE_DISPUTE("价格争议行政裁决", "1810"),
        MEDICAL_DISPUTE("医疗纠纷行政裁决", "1811"),
        TRAFFIC_ACCIDENT("交通事故行政裁决", "1812"),
        RURAL_LAND_CONTRACT("农村土地承包经营权争议行政裁决", "1813"),
        OTHER_ADJUDICATION("其他行政裁决", "1899");

        private final String description;
        private final String code;

        Cause(String description, String code) {
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