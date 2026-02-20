package entity;

public enum Role {
    USER(1),
    ADMIN(15);
    //Right abcd (in binary) a - delete , b -edit , c - add , d - find
    private int right;
    Role(int right) {
        this.right = right;
    }
    public int getRight() {
        return right;
    }

    public static final int FIND = 1;
    public static final int ADD = 2;
    public static final int EDIT= 4;
    public static final int DELETE = 8;

    public boolean can(int right){
        return (this.right & right) != 0;
    }
}
