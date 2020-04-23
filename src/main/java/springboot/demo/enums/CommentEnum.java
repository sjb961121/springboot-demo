package springboot.demo.enums;

public enum CommentEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentEnum c:CommentEnum.values()){
            if (c.getType()==type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentEnum(Integer type) {
        this.type = type;
    }
}
