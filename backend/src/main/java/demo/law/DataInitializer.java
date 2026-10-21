package demo.law;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DataInitializer {
    @Bean CommandLineRunner seed(ClauseRepository repo){return args->{
        if(repo.count()>0)return;
        repo.save(new Clause("第四百七十二条","要约定义与构成要件","要约是希望与他人订立合同的意思表示，该意思表示应当符合下列条件：（一）内容具体确定；（二）表明经受要约人承诺，要约人即受该意思表示约束。","要约、意思表示、内容具体确定、受约束意思"));
        repo.save(new Clause("第四百七十三条","要约邀请","要约邀请是希望他人向自己发出要约的表示。拍卖公告、招标公告、招股说明书、债券募集办法、基金招募说明书、商业广告和宣传、寄送的价目表等为要约邀请。商业广告和宣传的内容符合要约条件的，构成要约。","要约邀请、拍卖公告、招标公告、商业广告、宣传"));
        repo.save(new Clause("第四百七十四条","要约生效时间","要约生效的时间适用本法第一百三十七条的规定。","要约生效、到达主义、意思表示"));
        repo.save(new Clause("第四百七十五条","要约撤回","要约可以撤回。要约的撤回适用本法第一百四十一条的规定。","要约撤回、撤回通知、到达时间"));
        repo.save(new Clause("第四百七十六条","要约撤销","要约可以撤销，但是有下列情形之一的除外：（一）要约人以确定承诺期限或者其他形式明示要约不可撤销；（二）受要约人有理由认为要约是不可撤销的，并已经为履行合同做了合理准备工作。","要约撤销、不可撤销、承诺期限、合理准备"));
    };}
}
