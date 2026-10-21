package demo.law;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class LawServiceAcceptanceTest {
    @Autowired LawService service; @Autowired ClauseRepository repo;
    @Test void case01EmptySearch(){assertTrue(service.search("").isEmpty());}
    @Test void case02OfferSearch(){assertTrue(service.search("要约").stream().anyMatch(c->c.articleNumber.equals("第四百七十二条")));}
    @Test void case03RevocationSearch(){assertTrue(service.search("撤销").stream().anyMatch(c->c.articleNumber.equals("第四百七十六条")));}
    @Test void case04InvitationSearch(){assertTrue(service.search("邀请").stream().anyMatch(c->c.articleNumber.equals("第四百七十三条")));}
    @Test void case05NoHit(){assertTrue(service.search("火星法条").isEmpty());}
    @Test void case06AnswerOffer(){assertFalse((Boolean)service.answer("什么是要约").get("refused"));}
    @Test void case07AnswerInvitation(){assertEquals("第四百七十三条",citation(service.answer("要约邀请是什么")));}
    @Test void case08AnswerRevocation(){assertEquals("第四百七十六条",citation(service.answer("要约撤销")));}
    @Test void case09RefusalWithoutEvidence(){assertEquals(true,service.answer("租房纠纷如何赔偿").get("refused"));}
    @Test void case10RejectBlankQuestion(){assertThrows(IllegalArgumentException.class,()->service.answer(" "));}
    @Test void case11DisclaimerPresent(){assertTrue(service.answer("要约").containsKey("disclaimer"));}
    @Test void case12OfficialSourcePresent(){assertTrue(service.search("要约").get(0).sourceUrl.startsWith("https://"));}
    @Test void case13SeedHasFiveTeachingClauses(){assertEquals(5,repo.count());}
    @Test void case14CitationHasSupportingEvidence(){assertFalse(service.answer("要约邀请").get("citations").toString().isBlank());}
    @Test void case15UnknownTermRefuses(){assertTrue((Boolean)service.answer("专利侵权赔偿").get("refused"));}
    private String citation(Map<String,Object> answer){return ((Clause)((List<?>)answer.get("citations")).get(0)).articleNumber;}
}
