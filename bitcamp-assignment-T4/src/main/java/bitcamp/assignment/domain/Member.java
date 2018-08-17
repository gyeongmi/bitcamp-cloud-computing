package bitcamp.assignment.domain;

import java.io.Serializable;

public class Member implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //내보낼때 클래스정보와 버전 번호를 함께 내보냄
    //읽어들이는 쪽에서 읽을 수 있을지 없을지 결정한다.
    
    protected String name;
    protected String email;
    protected String password;
    
    //Serializable
    //메소드를 구현하지 않아도 된다.
    //상징적인 용도로 쓴다.
    
    //톰캣을 하나 쓰지 않고 두개쓴다.
    //이전 톰캣서버에 있던 것을 다른 톰캣서버로 옮기는데
    //객체를 다른 객체로 옮기는 것을 '스트리밍한다.' 라고 한다.
    //옮길수있도록(스트리밍ㅎ할수 있도록) byte 배열(시리얼라이즈=마쉐어링)로 바꾼 것을 내보내고
    //다시 객체로 보관한다.(de시리얼라이즈=언마쉐어링)
    //호출자와 호출된자와의 규칙
    
    //이퀄스 오버라이딩
    //객체와 객체를 바로 비교할수 있도록(매번 조건문 쓰지 않아도 됨..)
    //기본디폴트가 각 객체마다 다르기 때문에 같은 값을 갖고 있더라도 false
    
    //사람은 지문, 주민번호로 구분
    //엑스코드...........
    
    //디지털 지문!!
    //해쉬코드
    //X코드
    
    //오브젝트로부터 상속받은 해쉬코드를 하면 된다.
    
    
    @Override
    public String toString() {
        return "Member [name=" + name + ", email=" + email + ", password=" + password + "]";
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    

}
