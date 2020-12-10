public enum ItemType {
    AUDIO ("AU"), VISUAL ("VI"), AUDIOMOBILE ("AM"), VISUALMOBILE ("VM");

    public String code;
    ItemType(){}
    ItemType(String code){
        this.code = code;
    }

    private String getCode(){
        return code;
    }
    void setCode(String code){
        this.code = code;
    }
}