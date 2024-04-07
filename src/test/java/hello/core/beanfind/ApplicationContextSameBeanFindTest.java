package hello.core.beanfind;

import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationContextSameBeanFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
      SameBeanConfig.class);

  @Configuration
  static class SameBeanConfig {

    @Bean
    public MemberRepository memberRepository1() {
      return new MemoryMemberRepository();
    }

    @Bean
    public MemberRepository memberRepository2() {
      return new MemoryMemberRepository();
    }
  }

  @Test
  @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
  void findBeanByTypeDuplicate() {
    assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
  }


  @Test
  @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
  void findBeanByName() {
    MemberRepository memberRepository1 = ac.getBean("memberRepository1", MemberRepository.class);
    System.out.println("memberRepository1 = " + memberRepository1);
    MemberRepository memberRepository2 = ac.getBean("memberRepository2", MemberRepository.class);
    System.out.println("memberRepository2 = " + memberRepository2);
  }

  @Test
  @DisplayName("특정 타입을 모두 조회")
  void findAllBeanByType() {
    Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + " value = " + beansOfType.get(key));
    }
    System.out.println("beansOfType = " + beansOfType);
    Assertions.assertThat(beansOfType.size()).isEqualTo(2);
  }

}
