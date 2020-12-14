package com.software.nju.Enum;

public enum ApiEnum {

    api1("JHApi","JHApi","使用静海法院数据库"),
    api2("XFApi","XFApi","使用统一信访数据库");


    private String api;
    private String explain;
    private String value;

    ApiEnum(String value,String api,String explain){
        this.value = value;
        this.api = api;
        this.explain = explain;
    }
    private String getApi(){
        return this.api;
    }
    private String getExplain(){
        return this.explain;
    }
    private String getValue(){
        return this.value;
    }
    public static String getValueByApi(String api){
        for(ApiEnum apis:ApiEnum.values()){
            if(apis.getApi().equals(api)){
                return apis.getValue();
            }
        }
        return null;
    }

}
