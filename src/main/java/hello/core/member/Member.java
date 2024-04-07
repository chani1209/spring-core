package hello.core.member;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Member {

  private Long id;
  private String name;
  private Grade grade;

  public Member(Long id, String name, Grade grade) {
    this.id = id;
    this.name = name;
    this.grade = grade;
  }
}
