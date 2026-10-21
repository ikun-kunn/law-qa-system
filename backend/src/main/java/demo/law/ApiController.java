package demo.law;

import java.util.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:5173")
public class ApiController {
    private final LawService service; private final FeedbackRepository feedback;
    ApiController(LawService service,FeedbackRepository feedback){this.service=service;this.feedback=feedback;}
    @GetMapping("/clauses") List<Clause> search(@RequestParam(defaultValue="") String q){return service.search(q);}
    public record Question(@NotBlank String question) {}
    @PostMapping("/ask") Map<String,Object> ask(@Valid @RequestBody Question body){return service.answer(body.question());}
    public record FeedbackInput(@NotBlank String question,@Min(1) @Max(5) Integer rating,String comment) {}
    @PostMapping("/feedback") ResponseEntity<?> submit(@Valid @RequestBody FeedbackInput body){
        feedback.save(new Feedback(body.question(),body.rating(),body.comment()));
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("received",true));
    }
}
