//package config;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//import javax.persistence.EntityManager;
//
//
//// 얘는 안먹음 ServiceApplication에 설정해주면 먹음 ? 왜이럼?
//@EnableJpaAuditing
//@Configuration
//public class DatabaseConfig {
//    @Bean
//    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
//        return new JPAQueryFactory(em);
//    }
//
//}
