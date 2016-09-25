package tsmcomp.question.model;

/**
 * ユーザデータ
 */
public class User {

    private String name;
    private int age;

    //  TODO:アイコンを追加

    public User(){

    }




    public static User createDummy(){
        User user = new User();
        return user;
    }

}
