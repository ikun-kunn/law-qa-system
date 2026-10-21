package demo.law;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class LawService {
    public static final String DISCLAIMER="教学演示，不构成法律意见；所示条文及效力状态需以官方现行文本核验。";
    private final ClauseRepository clauses;
    LawService(ClauseRepository clauses){this.clauses=clauses;}
    public List<Clause> search(String q) {
        if(q==null || q.isBlank()) return List.of();
        String term=q.trim().toLowerCase(Locale.ROOT);
        return clauses.findAll().stream().filter(c -> (c.articleNumber+c.title+c.content+c.keywords).toLowerCase(Locale.ROOT).contains(term)).toList();
    }
    public Map<String,Object> answer(String question) {
        if(question==null || question.isBlank()) throw new IllegalArgumentException("question不能为空");
        List<Clause> evidence=search(question);
        if(question.contains("要约邀请")) evidence=search("第四百七十三条");
        else if(question.contains("要约撤销") || question.contains("不可撤销")) evidence=search("第四百七十六条");
        else if(evidence.isEmpty() && question.contains("要约")) evidence=search("第四百七十二条");
        if(evidence.isEmpty()) return Map.of("refused",true,"answer","现有演示资料没有足够证据回答该问题。","disclaimer",DISCLAIMER,"citations",List.of());
        Clause c=evidence.get(0);
        return Map.of("refused",false,"answer",c.content,"disclaimer",DISCLAIMER,"citations",List.of(c));
    }
}
